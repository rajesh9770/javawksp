package com.web.crawler;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.function.Predicate;

public class WebCrawler2 {

    public static final int THREAD_COUNT = 5;


    BlockingQueue<Future<Set<URL>>> bq = new LinkedBlockingQueue<>();
    private ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
    Set<URL> masterList = new HashSet<>();
    private String urlBase;


    public static void main(String[] args) throws IOException, InterruptedException {
        WebCrawler2 wc = new WebCrawler2();
        wc.go(new URL("http://news.yahoo.com"));
        //wc.go(new URL("http://scrumbucket.org/"));
        //wc.write("urllist.txt");
    }

    public void go(URL start) throws IOException, InterruptedException {

        // stay within same site
        urlBase = start.toString().replaceAll("(.*//.*/).*", "$1");

        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        long ct = 0;
        submitNewURL(start);
        ++ct;

        while(ct>0){
            Iterator<Future<Set<URL>>> iterator = bq.iterator();
            Set<URL> newLevel = new HashSet<>();
            while(iterator.hasNext()){
                Future<Set<URL>> next = iterator.next();
                if(next.isDone()){
                    try {
                        Set<URL> urls = next.get();
                        urls.stream().filter(this::shouldVisit).forEach(u->{
                            System.out.println(u);
                            masterList.add(u);
                            newLevel.add(u);
                        });
                    } catch (ExecutionException e) {
                        //e.printStackTrace();
                    }
                    iterator.remove();
                    --ct;
                }else if(next.isCancelled()){
                    iterator.remove();
                    --ct;
                }
            }
            if (!newLevel.isEmpty()){
                ct += newLevel.size();
                for(URL u: newLevel){
                    submitNewURL(u);
                }
            }
            System.out.println(ct);
            Thread.sleep(1*1000);
        }

        stopWatch.stop();
        executorService.shutdown();

        System.out.println("Found " + masterList.size() + " urls");
        System.out.println("in " + stopWatch.getTime() / 1000 + " seconds");
    }

    private void submitNewURL(URL url) {

        URLTraversal grabPage = new URLTraversal(url);
        Future<Set<URL>> future = executorService.submit(grabPage);
        bq.add(future);

    }

    private boolean shouldVisit(URL url) {
        if (masterList.contains(url)) {
            return false;
        }
        if (!url.toString().startsWith(urlBase)) {
            return false;
        }
        if (url.toString().endsWith(".pdf")) {
            return false;
        }
        return true;
    }


    public static class URLTraversal implements Callable<Set<URL>> {
        static final int TIMEOUT = 60000;   // one minute

        private URL url;

        public URLTraversal(URL url){
            this.url = url;
        }

        @Override
        public Set<URL> call() throws Exception {
            //System.out.println("processing " + url);
            Document document = Jsoup.parse(url, TIMEOUT);
            Set<URL> urlList = new HashSet<>();
            for (Element link : document.select("a[href]")) {
                String href = link.attr("href");
                if (StringUtils.isBlank(href) || href.startsWith("#")) {
                    continue;
                }

                try {
                    URL nextUrl = new URL(url, href);
                    urlList.add(nextUrl);
                } catch (MalformedURLException e) { // ignore bad urls
                }
            }
            return urlList;
        }
    }

}
