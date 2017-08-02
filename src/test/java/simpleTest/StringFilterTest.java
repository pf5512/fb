package simpleTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringFilterTest {
	
	public static void main(String[] args){
		StringFilterTest sft = new StringFilterTest();
		String str = "Aྀ??叱咤_风雲☁!@#$%^&*()[];1',24./{}:\"<>?";
		System.out.print(sft.StringFilter(str));
	}
	
	
	public String StringFilter(String   str)   throws   PatternSyntaxException   {      
		  // 只允许字母，数字，汉字，_        
		  String   regEx  =  "[^a-zA-Z0-9_\u4e00-\u9fa5]";                      
		  // 清除掉所有特殊字符   
		  //String regEx="[`~!@#$%^&*()+=|{}':;',//[//]\".<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";   
		  Pattern   p   =   Pattern.compile(regEx);      
		  Matcher   m   =   p.matcher(str);      
		  return   m.replaceAll("").trim();      
	}
}
