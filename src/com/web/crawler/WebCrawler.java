package com.web.crawler;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

public class WebCrawler {

    public static final int THREAD_COUNT = 5;
    private static final long PAUSE_TIME = 1000;

    private Set<URL> masterList = new HashSet<>();
    private List<Future<GrabPage>> futures = new ArrayList<>();
    private ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

    private String urlBase;

    private final int maxDepth;
    private final int maxUrls;

    public WebCrawler(int maxDepth, int maxUrls) {
        this.maxDepth = maxDepth;
        this.maxUrls = maxUrls;
    }

    public void go(URL start) throws IOException, InterruptedException {

        // stay within same site
        urlBase = start.toString().replaceAll("(.*//.*/).*", "$1");

        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        submitNewURL(start, 0);

        while (checkPageGrabs()) ;
        stopWatch.stop();

        System.out.println("Found " + masterList.size() + " urls");
        System.out.println("in " + stopWatch.getTime() / 1000 + " seconds");
    }

    private boolean checkPageGrabs() throws InterruptedException {
        Thread.sleep(PAUSE_TIME);
        Set<GrabPage> pageSet = new HashSet<>();
        Iterator<Future<GrabPage>> iterator = futures.iterator();

        while (iterator.hasNext()) {
            Future<GrabPage> future = iterator.next();
            if (future.isDone()) {
                iterator.remove();
                try {
                    pageSet.add(future.get());
                } catch (InterruptedException e) {  // skip pages that load too slow
                } catch (ExecutionException e) {
                }
            }
        }

        for (GrabPage grabPage : pageSet) {
            addNewURLs(grabPage);
        }

        return (futures.size() > 0);
    }

    private void addNewURLs(GrabPage grabPage) {
        for (URL url : grabPage.getUrlList()) {
            if (url.toString().contains("#")) {
                try {
                    url = new URL(StringUtils.substringBefore(url.toString(), "#"));
                } catch (MalformedURLException e) {
                }
            }

            submitNewURL(url, grabPage.getDepth() + 1);
        }
    }

    private void submitNewURL(URL url, int depth) {
        if (shouldVisit(url, depth)) {
            masterList.add(url);

            GrabPage grabPage = new GrabPage(url, depth);
            Future<GrabPage> future = executorService.submit(grabPage);
            futures.add(future);
        }
    }

    /**
     * Redementary visitation filter.
     */
    private boolean shouldVisit(URL url, int depth) {
        if (masterList.contains(url)) {
            return false;
        }
        if (!url.toString().startsWith(urlBase)) {
            return false;
        }
        if (url.toString().endsWith(".pdf")) {
            return false;
        }
        if (depth > maxDepth) {
            return false;
        }
        if (masterList.size() >= maxUrls) {
            return false;
        }
        return true;
    }

    public void write(String path) throws IOException {
        FileUtils.writeLines(new File(path), masterList);
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        WebCrawler wc = new WebCrawler(2000, 50000);
        wc.go(new URL("http://news.yahoo.com"));
        //wc.go(new URL("http://scrumbucket.org/"));
        wc.write("urllist.txt");
    }

    public static class GrabPage implements    Callable<GrabPage> {
        static final int TIMEOUT = 60000;   // one minute

        private URL url;
        private int depth;
        private Set<URL> urlList = new HashSet<>();

        public GrabPage(URL url, int depth) {
            this.url = url;
            this.depth = depth;
        }

        @Override
        public GrabPage call() throws Exception {
            Document document = null;
            System.out.println("Visiting (" + depth + "): " + url.toString());
            document = Jsoup.parse(url, TIMEOUT);

            processLinks(document.select("a[href]"));

            return this;
        }

        private void processLinks(Elements links) {
            for (Element link : links) {
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
        }

        public Set<URL> getUrlList() {
            return urlList;
        }

        public int getDepth() {
            return depth;
        }
    }

}