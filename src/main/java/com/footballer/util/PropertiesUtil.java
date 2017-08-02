package com.footballer.util;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	static private String domain = null;
	static private String identity = null;
	static private String suffix =null;

	static {
		loads();
	}

	synchronized static public void loads() {
		if (domain == null || identity == null || suffix ==null) {
			InputStream is = PropertiesUtil.class.getResourceAsStream("/env.properties");
			Properties dbProps = new Properties();
			try {
				dbProps.load(is);
				domain = dbProps.getProperty("domain");
				identity = dbProps.getProperty("identity");
				suffix = dbProps.getProperty("suffix");
			} catch (Exception e) {
				System.err.println("不能读取属性文件. " + "请确保*.properties在CLASSPATH指定的路径中");
			}
		}
	}

	public static String getDomain() {
		if (domain == null)
			loads();
		return domain;
	}
	
	public static String getIdentity() {
		if (identity == null)
			loads();
		return identity;
	}
	
	public static String getSuffix() {
		if (suffix == null)
			loads();
		return suffix;
	}

	public static void main(String args[]) {
		//PropertiesUtil pu = new PropertiesUtil();
		System.out.println(getDomain());

		// System.out.println(System.getProperty("/WebContent/i18n/env.properties"));
		// System.out.println(System.getProperty("user.dir"));
		// System.out.println( System.getProperty("java.class.path"));
	}
}
