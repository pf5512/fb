package com.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Signature {
    
	//private static final String APP_SECRET = WechatConfig.PARTNER_APP_SECRET; 

    public static String getSign(Map<String,String> map, String partnerAppSecret){
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,String> entry:map.entrySet()){
            if(entry.getValue()!=""){
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        
        result += "key=" + partnerAppSecret;
        result = MD5.MD5Encode(result).toUpperCase();
        return result;
    }

}
