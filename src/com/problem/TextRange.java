package com.problem;

import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class TextRange {
	
	private static class Range{
		String start, end;
		boolean startInc, endInc;
		
		public Range(String start, String end, boolean startInc, boolean endInc) throws Exception{			
			this.start = start;
			this.end = end;
			this.startInc = startInc;
			this.endInc = endInc;
			isValid();
		}
		
		private void isValid() throws Exception{
			//assert start and end not null
			// do not allow empty range
			if ((start.equals(end) && startInc != true && endInc != true) ||
					(start.compareTo(end) > 0)) throw new Exception("Range [" + start + ", " + end + "] is not valid");
		}
		
		boolean contains(String str){
			boolean isGreateThanStart = startInc ? start.compareTo(str) <=0 : start.compareTo(str) <0;
			boolean isLessThanEnd = endInc ? end.compareTo(str) >=0 : end.compareTo(str) >0;
			return isGreateThanStart && isLessThanEnd; 
		}
		
		boolean isGreater(String str){
			return end.compareTo(str) < 0;
		}
		
		boolean isLess(String str){
			return start.compareTo(str) > 0;
		}
		
		/**
		 * Checks if this range includes the specified range.
		 * @param r
		 * @return
		 */
		boolean encloses(Range r){
			//TODO handle inclusion/exclusion
			return contains(r.start) && contains(r.end);
		}
		
		public boolean equals(Range r){
			return start.equals(r.start) && end.equals(r.end) && startInc == r.startInc && endInc == r.endInc;
		}
		
		public int hashCode(){
			//revise
			return (start + end + Boolean.toString(startInc) + Boolean.toString(endInc)).hashCode(); 			
		}
		
		public String toString(){
			String ret = startInc ? "[" : "(";
			ret += start;
			ret += " ";
			ret += end;
			ret += endInc ? "]" : ")";
			return ret;
		}
	}
	private SortedSet<Range> textRange;
	
	
	public TextRange(){
		textRange = new TreeSet<Range>( new Comparator<Range>() {
			public int compare(Range o1, Range o2) {
				return  o1.end.compareTo(o2.end) ;
			}
		});
	}
	
	public void addRange(Range r) throws Exception{		
		//get the start and end point of the new range 
		SortedSet<Range> tailSet = textRange.tailSet(new Range("", r.start, false, true));
		String start = r.start;
		boolean sInc = r.startInc;
		if(tailSet != null && !tailSet.isEmpty()){
			Range first = tailSet.first();
			if (first.contains(r.start)) {
				start = first.start;
				sInc = first.startInc;
			}			
		}
		String end = r.end;
		boolean eInc = r.endInc;
		SortedSet<Range> endTailSet = textRange.tailSet(new Range("", r.end, false, true));
		if(endTailSet != null && !endTailSet.isEmpty()){
			Range first = endTailSet.first();
			if (first.contains(r.end)){
				end = first.end;
				eInc = first.endInc;
			}			
		}
		
		Range newRangeToAdd = new Range(start, end, sInc, eInc);
		System.out.println("New Range to add " + newRangeToAdd);
		// now remove all the ranges that are part of this new Range
		Iterator<Range> it = textRange.iterator();
		while(it.hasNext()){
			Range next = it.next();
			if (newRangeToAdd.encloses(next)) it.remove();
		}		
		textRange.add(newRangeToAdd);
	}
	
	public void deleteRange(Range r) throws Exception{
		// find the range that contains r.start and shrink it's end point
		SortedSet<Range> tailSet = textRange.tailSet(new Range("", r.start, false, true));
		
		if(tailSet != null && !tailSet.isEmpty()){
			Range first = tailSet.first();
			if (first.contains(r.start)) {
				first.end = r.start; //shrink the range
				first.endInc = ! r.startInc;
			}			
		}
		// find the range that contains r.end and shrink it's start point
		SortedSet<Range> endTailSet = textRange.tailSet(new Range("", r.end, false, true));
		if(endTailSet != null && !endTailSet.isEmpty()){
			Range first = endTailSet.first();
			if (first.contains(r.end)){
				first.start = r.end; // shrink the range
				first.startInc = ! r.endInc;
			}			
		}
		// now delete all ranges that are enclosed by r
		Iterator<Range> it = textRange.iterator();
		while(it.hasNext()){
			Range next = it.next();
			if (r.encloses(next)) it.remove();
		}
	}
	
	public boolean isInsideRange(String input) throws Exception{
			SortedSet<Range> tailSet = textRange.tailSet(new Range("", input, false, true));
			if(tailSet != null && !tailSet.isEmpty()){
				Range first = tailSet.first();
				System.out.println("Checking the range " + first);
				return first.contains(input);
			}
			return false;			
	}
	
	
	public String toString(){
		StringBuilder buff = new StringBuilder();
		for (Range r : textRange){
			buff.append(r);
			buff.append(" ");
		}
		return buff.toString();
	}
	
	public static void test() throws Exception{		
		
		System.out.println( "Test "+ "AaA".compareTo("BaB"));
		Range r = new Range("AaA", "BaB", true, true);
		Range r2 = new Range("AA", "AB", true, true);
		TextRange rr = new TextRange();
		rr.addRange(r);
		rr.addRange(r2);
		System.out.println(rr);
		System.out.println(" is Inside " + rr.isInsideRange("Aaab"));
		String testStr = "AA";
		System.out.println( testStr + " "  + "contains "   + r.contains(testStr) + " " + r.isGreater(testStr) + " " + r.isLess(testStr));
		testStr = "AaaB";
		System.out.println( testStr + " "  + "contains "  + r.contains(testStr) + " " + r.isGreater(testStr) + " " + r.isLess(testStr));
		testStr = "Cblahblah";
		System.out.println( testStr + " "  + "contains "  + r.contains(testStr) + " " + r.isGreater(testStr) + " " + r.isLess(testStr));
	}
}
