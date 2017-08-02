package com.utils;

import java.util.List;

import com.footballer.util.ListUtil;

import jersey.repackaged.com.google.common.collect.Lists;

public class TempTest {

	public static void main(String [] args){
		
		testList();
	}
	
	public static void testList(){
		List<String> l = Lists.newArrayList();
		l.add("1");
		l.add("1");
		l.add("2");
		l.add("1");
		l.add("1");
		l.add("2");
		l.add("1");
		l.add("2");
		l.add("1");
		l.add("1");
		
		l = ListUtil.removeDuplicate(l);  
		
		for(String s: l){
			System.out.println(s);
		}
	}
}
