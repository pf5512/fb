package com.footballer.util;

import java.util.HashSet;
import java.util.List;

public class ListUtil {

	@SuppressWarnings("rawtypes")
	public static List removeDuplicate(List list) 
	{   
		HashSet<?> h = new HashSet(list);   
		list.clear();   
		list.addAll(h);   
		return list;   
	}  
	
	
	public static boolean checkListisNotNull(List list) 
	{   
		if(ObjectUtil.isNull(list)){
			return false;
		}
		if(list.size()>0){
			return true;
		}else{
			return false;	
		}   
	} 
}
