package com.footballer.util;

import java.util.List;
import org.springframework.util.CollectionUtils;

/**
 * User: justin  Date Time: 6/29/14 
 */
public final class ObjectUtil {

    private ObjectUtil() {
    }

    public static boolean isNotNull(Object obj) {
    	if(obj instanceof String){
    		return !((String) obj).trim().equals("null");
    	}else{
    		return !isNull(obj);
    	}
    	
    }

    public static boolean isNull(Object obj) {
    	
    	if (obj == null)  
        {  
            return true;  
        }  
        if ((obj instanceof List))  
        {  
            return ((List) obj).size() == 0;  
        } 
        if ((obj instanceof String))  
        {  
            return ((String) obj).trim().equals("") || ((String) obj).trim().equals("null");  
        }
          
        return false; 
    }

    public static String convertListToString(List<String> list, String leftBracket,
            String rightBracket) {
        StringBuffer result = new StringBuffer();
        if (!CollectionUtils.isEmpty(list)) {
            for (String msg : list) {
                result.append(leftBracket + msg + rightBracket);
            }
        }
        return result.toString();
    }

    public static String convertListToString(List<String> list) {
        return convertListToString(list, "[", "]");
    }
    
    public static void main(String args[]){
    	String s = null;
    	
//    	System.out.println(isNotNull(null));
//    	System.out.println(isNull(null));
    	
    	System.out.println(isNotNull(s));
    	System.out.println(isNull(s));
    	//System.out.println(isNotNull("1"));
    }
}
