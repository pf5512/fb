package com.footballer.util;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * @author
 * @version
 */
public class StringUtil {
	/**
	 * The empty String {@code ""}.
	 */
	public static final String EMPTY = "";

	public static enum REGEX_ENUM {
		EMAIL("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"), CHINESE_CHARACTER(
				"[\\u4E00-\\u9FA5]+");
		private String value;

		private REGEX_ENUM(String value) {
			this.value = value;
		}

		public String toString() {
			return this.value;
		}
	}; 

	/**
	 * 
	 * @param regex
	 * @param str
	 * @return
	 */
	public static boolean matcherRegex(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 
	 * @param ch
	 * @return
	 */
	public static boolean isChineseCharacter(char ch) {
		return matcherRegex(REGEX_ENUM.CHINESE_CHARACTER.toString(),
				String.valueOf(ch));
	}

	/**
	 * 
	 * @param str
	 * @param byteLength
	 */
	public static String subString(String str, int byteLength) {
		if (isBlank(str))
			return EMPTY;
		if (str.getBytes().length <= byteLength)
			return str;
		if (str.length() >= byteLength)
			str = str.substring(0, byteLength);
		int readLen = 0;
		String c = null;
		StringBuffer sb = new StringBuffer(EMPTY);
		for (int i = 0; i < str.length(); i++) {
			c = String.valueOf(str.charAt(i));
			readLen += c.getBytes().length;
			if (readLen > byteLength)
				return sb.toString();
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * check length (minLength<=str.length<=maxLength)
	 * 
	 * @param str
	 *            input String
	 * @param minLength
	 *            minute length
	 * @param maxLength
	 *            maximum length
	 * @return boolean true: minLength<=str.length<=maxLength false: other
	 */
	public static boolean checkLength(String str, int minLength, int maxLength) {
		if (isBlank(str))
			return false;
		int len = str.length();
		if (minLength == 0)
			return len <= maxLength;
		else if (maxLength == 0)
			return len >= minLength;
		else
			return (len >= minLength && len <= maxLength);
	}

	/**
	 * decode string by UTF-8
	 * 
	 * @param str
	 *            input string
	 * @return String decode string
	 */
	public static String decodeString(String str) {
		return decodeString(str, "UTF-8");
	}

	/**
	 * decode string by the input encoding
	 * 
	 * @param str
	 * @param encoding
	 * @return
	 */
	public static String decodeString(String str, String encoding) {
		if (isBlank(str))
			return EMPTY;
		try {
			return URLDecoder.decode(str.trim(), encoding);
		} catch (UnsupportedEncodingException e) {
		}
		return EMPTY;
	}

	/**
	 * decode the string
	 * 
	 * @param str
	 * @param encoding
	 * @return
	 */
	public static String decodeURI(String str) {
		if (isBlank(str))
			return EMPTY;
		try {
			return new String(str.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return EMPTY;
	}

	/**
	 * encode String by UTF-8
	 * 
	 * @param str
	 *            input string
	 * @return String
	 */
	public static String encodeString(String str) {
		return encodeString(str, "UTF-8");
	}

	/**
	 * encode String by the input encoding
	 * 
	 * @param str
	 *            input string
	 * @return String encode string
	 */
	public static String encodeString(String str, String encoding) {
		if (isBlank(str))
			return EMPTY;
		try {
			return URLEncoder.encode(str.trim(), encoding);
		} catch (UnsupportedEncodingException e) {
		}
		return EMPTY;
	}

	/**
	 * get the only string by time
	 * 
	 * @return
	 */
	public static String getOnlyString() {
		return String.valueOf(System.currentTimeMillis());
	}

	/**
	 * <p>
	 * Checks if a CharSequence is whitespace, empty ("") or null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isBlank(null)      = true
	 * StringUtils.isBlank("")        = true
	 * StringUtils.isBlank(" ")       = true
	 * StringUtils.isBlank("bob")     = false
	 * StringUtils.isBlank("  bob  ") = false
	 * </pre>
	 * 
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if the CharSequence is null, empty or whitespace
	 */
	public static boolean isBlank(CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(cs.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if a CharSequence is not empty (""), not null and not whitespace
	 * only.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isNotBlank(null)      = false
	 * StringUtils.isNotBlank("")        = false
	 * StringUtils.isNotBlank(" ")       = false
	 * StringUtils.isNotBlank("bob")     = true
	 * StringUtils.isNotBlank("  bob  ") = true
	 * </pre>
	 * 
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if the CharSequence is not empty and not null and
	 *         not whitespace
	 */
	public static boolean isNotBlank(CharSequence cs) {
		return !StringUtil.isBlank(cs);
	}

	/**
	 * parses the specified string as a signed decimal integer value
	 * 
	 * @param str
	 *            input string representation of an integer value.
	 * @return boolean true: each character is integer false: other
	 */
	public static boolean isInteger(String str) {
		if (isBlank(str))
			return false;
		try {
			Integer.parseInt(str.trim());
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * parses the specified string as a signed decimal long value
	 * 
	 * @param str
	 *            input string representation of an integer value.
	 * @return boolean true: each character is long integer false: other
	 */
	public static boolean isLong(String str) {
		if (isBlank(str))
			return false;
		try {
			Long.parseLong(str.trim());
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * parses the specified string as a signed boolean value
	 * 
	 * @param str
	 *            input string
	 * @return boolean true: str = true / TRUE (Ignore upper case or low case)
	 *         false: other
	 */
	public static boolean isBoolean(String str) {
		if (isBlank(str))
			return false;
		try {
			Boolean.parseBoolean(str.trim());
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * checks the specified string as a double value
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str) {
		if (isBlank(str))
			return false;
		try {
			Double.parseDouble(str.trim());
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * checks the specified string as a Date value
	 * 
	 * @param str
	 *            the input string
	 * @return boolean 
	 */
	public static boolean isDate(String str) {
		if (isBlank(str))
			return false;
		try {
			java.sql.Date.valueOf(str.trim());
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * checks the string arrays is all long values
	 * 
	 * @param str
	 *            the input string array
	 * @return boolean 
	 */
	public static boolean isLongs(String str[]) {
		for (int i = 0; i < str.length; i++) {
			if (!isLong(str[i]))
				return false;
		}
		return true;
	}

	/**
	 * 
	 * @param str
	 * @return boolean 
	 */
	public static boolean isIntegers(String str[]) {
		for (int i = 0; i < str.length; i++)
			if (!isInteger(str[i]))
				return false;
		return true;
	}

	/**
	 * 
	 * @param str
	 * @return boolean 
	 */
	public static boolean isBooleans(String str[]) {
		for (int i = 0; i < str.length; i++)
			if (!isBoolean(str[i]))
				return false;
		return true;
	}

	/**
	 * 
	 * @param str
	 * @return str
	 */
	public static boolean isTimestamp(String str) {
		if (isBlank(str))
			return false;
		try {
			java.sql.Date.valueOf(str.trim());
			return true;
		} catch (Exception ex) {
		}
		return false;
	}

	/**
	 * 
	 * @param str
	 */
	public static boolean isFullTimestamp(String str) {
		if (isBlank(str))
			return false;
		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date = format.parse(str.trim());
			return date != null;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 
	 * @param str
	 * @return Long[] 
	 */
	public static Long[] stringsToLongs(String str[]) {
		Long lon[] = new Long[str.length];
		for (int i = 0; i < lon.length; i++)
			lon[i] = new Long(str[i]);
		return lon;
	}

	/**
	 * 
	 * @param str
	 * @return Integer[]
	 */
	public static Integer[] stringsToIntegers(String str[]) {
		Integer array[] = new Integer[str.length];
		for (int i = 0; i < array.length; i++)
			array[i] = new Integer(str[i]);
		return array;
	}

	/**
	 * 
	 * @param str
	 * @return Boolean[]
	 */
	public static Boolean[] stringsToBooleans(String str[]) {
		Boolean array[] = new Boolean[str.length];
		for (int i = 0; i < array.length; i++)
			array[i] = new Boolean(str[i]);
		return array;
	}

	/**
	 * 
	 * @param str
	 * @return double[] 
	 */
	public static double[] stringsToDoubles(String str[]) {
		double array[] = new double[str.length];
		for (int i = 0; i < array.length; i++)
			array[i] = Double.parseDouble(str[i]);
		return array;
	}

	public static String formatNumber(Number number, int minFractionDigits,
			int maxFractionDigits) {
		NumberFormat format = NumberFormat.getInstance();
		format.setMinimumFractionDigits(minFractionDigits);
		format.setMaximumFractionDigits(maxFractionDigits);
		return format.format(number);
	}

	public static String heightLight(String text, String[] replaceStrs,
			String beginStr, String endStr) {
		if (text.length() == 0)
			return text;
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < replaceStrs.length; i++) {
			String replaceStr = replaceStrs[i];
			int index = text.indexOf(replaceStr);
			if (index >= 0) {
				String afterStr = null;
				if (index > 0) {
					String beforeStr = text.substring(0, index);
					afterStr = text.substring(index + replaceStr.length());
					str.append(heightLight(beforeStr, replaceStrs, beginStr,
							endStr));
				} else
					afterStr = text.substring(replaceStr.length());
				str.append(beginStr).append(replaceStr).append(endStr);
				str.append(heightLight(afterStr, replaceStrs, beginStr, endStr));
				break;
			}
		}
		if (str.length() == 0)
			return text;
		return str.toString();
	}

	/**
	 * 
	 * @param text
	 * @param replaceStrs
	 * @param newStr
	 * @return
	 */
	public static String replaceAll(String text, String[] replaceStrs,
			String newStr) {
		if (text.length() == 0)
			return text;
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < replaceStrs.length; i++) {
			String replaceStr = replaceStrs[i];
			int index = text.indexOf(replaceStr);
			if (index >= 0) {
				String afterStr = null;
				if (index > 0) {
					String beforeStr = text.substring(0, index);
					afterStr = text.substring(index + replaceStr.length());
					str.append(replaceAll(beforeStr, replaceStrs, newStr));
				} else
					afterStr = text.substring(replaceStr.length());
				str.append(newStr);
				str.append(replaceAll(afterStr, replaceStrs, newStr));
				break;
			}
		}
		if (str.length() == 0)
			return text;
		return str.toString();
	}

	/**
	 * 
	 * @param text
	 * @param replaceStr
	 * @param newStr
	 * @return
	 */
	public static String replaceAll(String text, String replaceStr,
			String newStr) {
		if (text.length() == 0)
			return text;
		StringBuilder str = new StringBuilder();
		int index = text.indexOf(replaceStr);
		if (index >= 0) {
			String afterStr = null;
			if (index > 0) {
				String beforeStr = text.substring(0, index);
				afterStr = text.substring(index + replaceStr.length());
				str.append(replaceAll(beforeStr, replaceStr, newStr));
			} else
				afterStr = text.substring(replaceStr.length());
			str.append(newStr);
			str.append(replaceAll(afterStr, replaceStr, newStr));
		}
		if (str.length() == 0)
			return text;
		return str.toString();
	}

	/**
	 * 
	 * @param text
	 * @param replaceStrs
	 * @param newStrs
	 * @return
	 */
	public static String replaceAllArray(String text, String[] replaceStrs,
			String[] newStrs) {
		if (text.length() == 0)
			return text;
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < replaceStrs.length; i++) {
			String replaceStr = replaceStrs[i];
			int index = text.indexOf(replaceStr);
			if (index >= 0) {
				String afterStr = null;
				if (index > 0) {
					String beforeStr = text.substring(0, index);
					afterStr = text.substring(index + replaceStr.length());
					str.append(replaceAllArray(beforeStr, replaceStrs, newStrs));
				} else
					afterStr = text.substring(replaceStr.length());
				str.append(newStrs[i]);
				str.append(replaceAllArray(afterStr, replaceStrs, newStrs));
				break;
			}
		}
		if (str.length() == 0)
			return text;
		return str.toString();
	}

	/**
	 * 
	 * @param html
	 * @return
	 */
	public static String decodeHTML(String html) {
		if (isBlank(html))
			return EMPTY;
		String[] replaceStr = { "&amp;", "&lt;", "&gt;", "&quot;" };
		String[] newStr = { "&", "<", ">", "\"" };
		return replaceAllArray(html, replaceStr, newStr);
	}

	/**
	 * 
	 * @param html
	 * @return
	 */
	public static String encodeHTML(String html) {
		if (isBlank(html))
			return EMPTY;
		int ln = html.length();
		char c;
		StringBuffer b;
		for (int i = 0; i < ln; i++) {
			c = html.charAt(i);
			if (c == '<' || c == '>' || c == '&' || c == '"') {
				b = new StringBuffer(html.substring(0, i));
				switch (c) {
				case '<':
					b.append("&lt;");
					break;
				case '>':
					b.append("&gt;");
					break;
				case '&':
					b.append("&amp;");
					break;
				case '"':
					b.append("&quot;");
					break;
				}
				i++;
				int next = i;
				while (i < ln) {
					c = html.charAt(i);
					if (c == '<' || c == '>' || c == '&' || c == '"') {
						b.append(html.substring(next, i));
						switch (c) {
						case '<':
							b.append("&lt;");
							break;
						case '>':
							b.append("&gt;");
							break;
						case '&':
							b.append("&amp;");
							break;
						case '"':
							b.append("&quot;");
							break;
						}
						next = i + 1;
					}
					i++;
				}
				if (next < ln)
					b.append(html.substring(next));
				html = b.toString();
				break;
			}
		}
		return html;
	}

	/**
	 * 
	 * @param plainText
	 */
	public static String Md5(String plainText) {
		StringBuffer buf = new StringBuffer(EMPTY);
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i = 0;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buf.toString();
	}

	/**
	 * MD5(32)
	 * 
	 * @param plainText
	 * @return
	 */
	public final static String MD5(String plainText) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = plainText.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
		}
		return "";
	}

	// Empty checks
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Checks if a CharSequence is empty ("") or null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isEmpty(null)      = true
	 * StringUtils.isEmpty("")        = true
	 * StringUtils.isEmpty(" ")       = false
	 * StringUtils.isEmpty("bob")     = false
	 * StringUtils.isEmpty("  bob  ") = false
	 * </pre>
	 * 
	 * <p>
	 * NOTE: This method changed in Lang version 2.0. It no longer trims the
	 * CharSequence. That functionality is available in isBlank().
	 * </p>
	 * 
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if the CharSequence is empty or null
	 */
	public static boolean isEmpty(CharSequence cs) {
		return cs == null || cs.length() == 0;
	}

	/**
	 * <p>
	 * Checks if a CharSequence is not empty ("") and not null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isNotEmpty(null)      = false
	 * StringUtils.isNotEmpty("")        = false
	 * StringUtils.isNotEmpty(" ")       = true
	 * StringUtils.isNotEmpty("bob")     = true
	 * StringUtils.isNotEmpty("  bob  ") = true
	 * </pre>
	 * 
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if the CharSequence is not empty and not null
	 * @since 3.0 Changed signature from isNotEmpty(String) to
	 *        isNotEmpty(CharSequence)
	 */
	public static boolean isNotEmpty(CharSequence cs) {
		return !isEmpty(cs);
	}

	// Trim
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Removes control characters (char &lt;= 32) from both ends of this String,
	 * handling {@code null} by returning {@code null}.
	 * </p>
	 * 
	 * <p>
	 * The String is trimmed using {@link String#trim()}. Trim removes start and
	 * end characters &lt;= 32. To strip whitespace use {@link #strip(String)}.
	 * </p>
	 * 
	 * <p>
	 * To trim your choice of characters, use the {@link #strip(String, String)}
	 * methods.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.trim(null)          = null
	 * StringUtils.trim("")            = ""
	 * StringUtils.trim("     ")       = ""
	 * StringUtils.trim("abc")         = "abc"
	 * StringUtils.trim("    abc    ") = "abc"
	 * </pre>
	 * 
	 * @param str
	 *            the String to be trimmed, may be null
	 * @return the trimmed string, {@code null} if null String input
	 */
	public static String trim(String str) {
		return str == null ? null : str.trim();
	}

	/**
	 * <p>
	 * Removes control characters (char &lt;= 32) from both ends of this String
	 * returning {@code null} if the String is empty ("") after the trim or if
	 * it is {@code null}.
	 * 
	 * <p>
	 * The String is trimmed using {@link String#trim()}. Trim removes start and
	 * end characters &lt;= 32. To strip whitespace use
	 * {@link #stripToNull(String)}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.trimToNull(null)          = null
	 * StringUtils.trimToNull("")            = null
	 * StringUtils.trimToNull("     ")       = null
	 * StringUtils.trimToNull("abc")         = "abc"
	 * StringUtils.trimToNull("    abc    ") = "abc"
	 * </pre>
	 * 
	 * @param str
	 *            the String to be trimmed, may be null
	 * @return the trimmed String, {@code null} if only chars &lt;= 32, empty or
	 *         null String input
	 */
	public static String trimToNull(String str) {
		String ts = trim(str);
		return isEmpty(ts) ? null : ts;
	}

	/**
	 * <p>
	 * Removes control characters (char &lt;= 32) from both ends of this String
	 * returning an empty String ("") if the String is empty ("") after the trim
	 * or if it is {@code null}.
	 * 
	 * <p>
	 * The String is trimmed using {@link String#trim()}. Trim removes start and
	 * end characters &lt;= 32. To strip whitespace use
	 * {@link #stripToEmpty(String)}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.trimToEmpty(null)          = ""
	 * StringUtils.trimToEmpty("")            = ""
	 * StringUtils.trimToEmpty("     ")       = ""
	 * StringUtils.trimToEmpty("abc")         = "abc"
	 * StringUtils.trimToEmpty("    abc    ") = "abc"
	 * </pre>
	 * 
	 * @param str
	 *            the String to be trimmed, may be null
	 * @return the trimmed String, or an empty String if {@code null} input
	 */
	public static String trimToEmpty(String str) {
		return str == null ? EMPTY : str.trim();
	}

	// Equals
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Compares two CharSequences, returning {@code true} if they are equal.
	 * </p>
	 * 
	 * <p>
	 * {@code null}s are handled without exceptions. Two {@code null} references
	 * are considered to be equal. The comparison is case sensitive.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.equals(null, null)   = true
	 * StringUtils.equals(null, "abc")  = false
	 * StringUtils.equals("abc", null)  = false
	 * StringUtils.equals("abc", "abc") = true
	 * StringUtils.equals("abc", "ABC") = false
	 * </pre>
	 * 
	 * @see java.lang.String#equals(Object)
	 * @param cs1
	 *            the first CharSequence, may be null
	 * @param cs2
	 *            the second CharSequence, may be null
	 * @return {@code true} if the CharSequences are equal, case sensitive, or
	 *         both {@code null}
	 */
	public static boolean equals(CharSequence cs1, CharSequence cs2) {
		return cs1 == null ? cs2 == null : cs1.equals(cs2);
	}

	/**
	 * <p>
	 * Compares two CharSequences, returning {@code true} if they are equal
	 * ignoring the case.
	 * </p>
	 * 
	 * <p>
	 * {@code null}s are handled without exceptions. Two {@code null} references
	 * are considered equal. Comparison is case insensitive.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.equalsIgnoreCase(null, null)   = true
	 * StringUtils.equalsIgnoreCase(null, "abc")  = false
	 * StringUtils.equalsIgnoreCase("abc", null)  = false
	 * StringUtils.equalsIgnoreCase("abc", "abc") = true
	 * StringUtils.equalsIgnoreCase("abc", "ABC") = true
	 * </pre>
	 * 
	 * @param str1
	 *            the first CharSequence, may be null
	 * @param str2
	 *            the second CharSequence, may be null
	 * @return {@code true} if the CharSequence are equal, case insensitive, or
	 *         both {@code null}
	 */
//	public static boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
//		if (str1 == null || str2 == null) {
//			return str1 == str2;
//		} else {
//			return CharSequenceUtils.regionMatches(str1, true, 0, str2, 0,
//					Math.max(str1.length(), str2.length()));
//		}
//	}

	// Case conversion
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Converts a String to upper case as per {@link String#toUpperCase()}.
	 * </p>
	 * 
	 * <p>
	 * A {@code null} input String returns {@code null}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.upperCase(null)  = null
	 * StringUtils.upperCase("")    = ""
	 * StringUtils.upperCase("aBc") = "ABC"
	 * </pre>
	 * 
	 * <p>
	 * <strong>Note:</strong> As described in the documentation for
	 * {@link String#toUpperCase()}, the result of this method is affected by
	 * the current locale. For platform-independent case transformations, the
	 * method {@link #lowerCase(String, Locale)} should be used with a specific
	 * locale (e.g. {@link Locale#ENGLISH}).
	 * </p>
	 * 
	 * @param str
	 *            the String to upper case, may be null
	 * @return the upper cased String, {@code null} if null String input
	 */
	public static String upperCase(String str) {
		if (str == null) {
			return null;
		}
		return str.toUpperCase();
	}

	/**
	 * <p>
	 * Converts a String to upper case as per {@link String#toUpperCase(Locale)}
	 * .
	 * </p>
	 * 
	 * <p>
	 * A {@code null} input String returns {@code null}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.upperCase(null, Locale.ENGLISH)  = null
	 * StringUtils.upperCase("", Locale.ENGLISH)    = ""
	 * StringUtils.upperCase("aBc", Locale.ENGLISH) = "ABC"
	 * </pre>
	 * 
	 * @param str
	 *            the String to upper case, may be null
	 * @param locale
	 *            the locale that defines the case transformation rules, must
	 *            not be null
	 * @return the upper cased String, {@code null} if null String input
	 */
	public static String upperCase(String str, Locale locale) {
		if (str == null) {
			return null;
		}
		return str.toUpperCase(locale);
	}

	/**
	 * <p>
	 * Converts a String to lower case as per {@link String#toLowerCase()}.
	 * </p>
	 * 
	 * <p>
	 * A {@code null} input String returns {@code null}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.lowerCase(null)  = null
	 * StringUtils.lowerCase("")    = ""
	 * StringUtils.lowerCase("aBc") = "abc"
	 * </pre>
	 * 
	 * <p>
	 * <strong>Note:</strong> As described in the documentation for
	 * {@link String#toLowerCase()}, the result of this method is affected by
	 * the current locale. For platform-independent case transformations, the
	 * method {@link #lowerCase(String, Locale)} should be used with a specific
	 * locale (e.g. {@link Locale#ENGLISH}).
	 * </p>
	 * 
	 * @param str
	 *            the String to lower case, may be null
	 * @return the lower cased String, {@code null} if null String input
	 */
	public static String lowerCase(String str) {
		if (str == null) {
			return null;
		}
		return str.toLowerCase();
	}

	/**
	 * <p>
	 * Converts a String to lower case as per {@link String#toLowerCase(Locale)}
	 * .
	 * </p>
	 * 
	 * <p>
	 * A {@code null} input String returns {@code null}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.lowerCase(null, Locale.ENGLISH)  = null
	 * StringUtils.lowerCase("", Locale.ENGLISH)    = ""
	 * StringUtils.lowerCase("aBc", Locale.ENGLISH) = "abc"
	 * </pre>
	 * 
	 * @param str
	 *            the String to lower case, may be null
	 * @param locale
	 *            the locale that defines the case transformation rules, must
	 *            not be null
	 * @return the lower cased String, {@code null} if null String input
	 */
	public static String lowerCase(String str, Locale locale) {
		if (str == null) {
			return null;
		}
		return str.toLowerCase(locale);
	}

	/**
	 * <p>
	 * Checks if the CharSequence contains only lowercase characters.
	 * </p>
	 * 
	 * <p>
	 * {@code null} will return {@code false}. An empty CharSequence
	 * (length()=0) will return {@code false}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isAllLowerCase(null)   = false
	 * StringUtils.isAllLowerCase("")     = false
	 * StringUtils.isAllLowerCase("  ")   = false
	 * StringUtils.isAllLowerCase("abc")  = true
	 * StringUtils.isAllLowerCase("abC") = false
	 * </pre>
	 * 
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if only contains lowercase characters, and is
	 *         non-null
	 */
	public static boolean isAllLowerCase(CharSequence cs) {
		if (cs == null || isEmpty(cs)) {
			return false;
		}
		int sz = cs.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isLowerCase(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the CharSequence contains only uppercase characters.
	 * </p>
	 * 
	 * <p>
	 * {@code null} will return {@code false}. An empty String (length()=0) will
	 * return {@code false}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isAllUpperCase(null)   = false
	 * StringUtils.isAllUpperCase("")     = false
	 * StringUtils.isAllUpperCase("  ")   = false
	 * StringUtils.isAllUpperCase("ABC")  = true
	 * StringUtils.isAllUpperCase("aBC") = false
	 * </pre>
	 * 
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if only contains uppercase characters, and is
	 *         non-null
	 */
	public static boolean isAllUpperCase(CharSequence cs) {
		if (cs == null || isEmpty(cs)) {
			return false;
		}
		int sz = cs.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isUpperCase(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	// Defaults
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Returns either the passed in String, or if the String is {@code null}, an
	 * empty String ("").
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.defaultString(null)  = ""
	 * StringUtils.defaultString("")    = ""
	 * StringUtils.defaultString("bat") = "bat"
	 * </pre>
	 * 
	 * @see ObjectUtils#toString(Object)
	 * @see String#valueOf(Object)
	 * @param str
	 *            the String to check, may be null
	 * @return the passed in String, or the empty String if it was {@code null}
	 */
	public static String defaultString(String str) {
		return str == null ? EMPTY : str;
	}

	/**
	 * <p>
	 * Returns either the passed in String, or if the String is {@code null},
	 * the value of {@code defaultStr}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.defaultString(null, "NULL")  = "NULL"
	 * StringUtils.defaultString("", "NULL")    = ""
	 * StringUtils.defaultString("bat", "NULL") = "bat"
	 * </pre>
	 * 
	 * @see ObjectUtils#toString(Object,String)
	 * @see String#valueOf(Object)
	 * @param str
	 *            the String to check, may be null
	 * @param defaultStr
	 *            the default String to return if the input is {@code null}, may
	 *            be null
	 * @return the passed in String, or the default if it was {@code null}
	 */
	public static String defaultString(String str, String defaultStr) {
		return str == null ? defaultStr : str;
	}

	/**
	 * <p>
	 * Returns either the passed in CharSequence, or if the CharSequence is
	 * whitespace, empty ("") or {@code null}, the value of {@code defaultStr}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.defaultIfBlank(null, "NULL")  = "NULL"
	 * StringUtils.defaultIfBlank("", "NULL")    = "NULL"
	 * StringUtils.defaultIfBlank(" ", "NULL")   = "NULL"
	 * StringUtils.defaultIfBlank("bat", "NULL") = "bat"
	 * StringUtils.defaultIfBlank("", null)      = null
	 * </pre>
	 * 
	 * @param <T>
	 *            the specific kind of CharSequence
	 * @param str
	 *            the CharSequence to check, may be null
	 * @param defaultStr
	 *            the default CharSequence to return if the input is whitespace,
	 *            empty ("") or {@code null}, may be null
	 * @return the passed in CharSequence, or the default
	 * @see StringUtils#defaultString(String, String)
	 */
	public static <T extends CharSequence> T defaultIfBlank(T str, T defaultStr) {
		return isBlank(str) ? defaultStr : str;
	}

	/**
	 * <p>
	 * Returns either the passed in CharSequence, or if the CharSequence is
	 * empty or {@code null}, the value of {@code defaultStr}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.defaultIfEmpty(null, "NULL")  = "NULL"
	 * StringUtils.defaultIfEmpty("", "NULL")    = "NULL"
	 * StringUtils.defaultIfEmpty("bat", "NULL") = "bat"
	 * StringUtils.defaultIfEmpty("", null)      = null
	 * </pre>
	 * 
	 * @param <T>
	 *            the specific kind of CharSequence
	 * @param str
	 *            the CharSequence to check, may be null
	 * @param defaultStr
	 *            the default CharSequence to return if the input is empty ("")
	 *            or {@code null}, may be null
	 * @return the passed in CharSequence, or the default
	 * @see StringUtils#defaultString(String, String)
	 */
	public static <T extends CharSequence> T defaultIfEmpty(T str, T defaultStr) {
		return isEmpty(str) ? defaultStr : str;
	}

	/**
	 * <p>
	 * Checks if the CharSequence contains only Unicode letters.
	 * </p>
	 * 
	 * <p>
	 * {@code null} will return {@code false}. An empty CharSequence
	 * (length()=0) will return {@code false}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isAlpha(null)   = false
	 * StringUtils.isAlpha("")     = false
	 * StringUtils.isAlpha("  ")   = false
	 * StringUtils.isAlpha("abc")  = true
	 * StringUtils.isAlpha("ab2c") = false
	 * StringUtils.isAlpha("ab-c") = false
	 * </pre>
	 * 
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if only contains letters, and is non-null
	 */
	public static boolean isAlpha(CharSequence cs) {
		if (cs == null || cs.length() == 0) {
			return false;
		}
		int sz = cs.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isLetter(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the CharSequence contains only Unicode letters and space (' ').
	 * </p>
	 * 
	 * <p>
	 * {@code null} will return {@code false} An empty CharSequence (length()=0)
	 * will return {@code true}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isAlphaSpace(null)   = false
	 * StringUtils.isAlphaSpace("")     = true
	 * StringUtils.isAlphaSpace("  ")   = true
	 * StringUtils.isAlphaSpace("abc")  = true
	 * StringUtils.isAlphaSpace("ab c") = true
	 * StringUtils.isAlphaSpace("ab2c") = false
	 * StringUtils.isAlphaSpace("ab-c") = false
	 * </pre>
	 * 
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if only contains letters and space, and is non-null
	 */
	public static boolean isAlphaSpace(CharSequence cs) {
		if (cs == null) {
			return false;
		}
		int sz = cs.length();
		for (int i = 0; i < sz; i++) {
			if ((Character.isLetter(cs.charAt(i)) == false)
					&& (cs.charAt(i) != ' ')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the CharSequence contains only Unicode letters or digits.
	 * </p>
	 * 
	 * <p>
	 * {@code null} will return {@code false}. An empty CharSequence
	 * (length()=0) will return {@code false}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isAlphanumeric(null)   = false
	 * StringUtils.isAlphanumeric("")     = false
	 * StringUtils.isAlphanumeric("  ")   = false
	 * StringUtils.isAlphanumeric("abc")  = true
	 * StringUtils.isAlphanumeric("ab c") = false
	 * StringUtils.isAlphanumeric("ab2c") = true
	 * StringUtils.isAlphanumeric("ab-c") = false
	 * </pre>
	 * 
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if only contains letters or digits, and is non-null
	 */
	public static boolean isAlphanumeric(CharSequence cs) {
		if (cs == null || cs.length() == 0) {
			return false;
		}
		int sz = cs.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isLetterOrDigit(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the CharSequence contains only Unicode letters, digits or space
	 * ({@code ' '}).
	 * </p>
	 * 
	 * <p>
	 * {@code null} will return {@code false}. An empty CharSequence
	 * (length()=0) will return {@code true}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isAlphanumericSpace(null)   = false
	 * StringUtils.isAlphanumericSpace("")     = true
	 * StringUtils.isAlphanumericSpace("  ")   = true
	 * StringUtils.isAlphanumericSpace("abc")  = true
	 * StringUtils.isAlphanumericSpace("ab c") = true
	 * StringUtils.isAlphanumericSpace("ab2c") = true
	 * StringUtils.isAlphanumericSpace("ab-c") = false
	 * </pre>
	 * 
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if only contains letters, digits or space, and is
	 *         non-null
	 */
	public static boolean isAlphanumericSpace(CharSequence cs) {
		if (cs == null) {
			return false;
		}
		int sz = cs.length();
		for (int i = 0; i < sz; i++) {
			if ((Character.isLetterOrDigit(cs.charAt(i)) == false)
					&& (cs.charAt(i) != ' ')) {
				return false;
			}
		}
		return true;
	}

	public static boolean isDigit(char ch) {
		if (ch >= '0' && ch <= '9')
			return true;
		return false;
	}

	public static boolean isDigit(int ch) {
		if (ch >= 0 && ch <= 9)
			return true;
		return false;
	}
	
	
	
	public static boolean isHave(String[] strs,String s)
	{
		  for(int i=0;i<strs.length;i++){
		   if(strs[i].indexOf(s)!=-1 && strs[i].length()== s.length()){
		    return true;
		   }
		  }
		  return false;
	}
	
	
	public static boolean isHave(String[] strs,char c)
	{
		  for(int i=0;i<strs.length;i++){
		   if(strs[i].indexOf(c)!=-1){
		    return true;//
		   }
		  }
		  return false;//
	}
	

	/**
	 * 将输入流转换为字符串
	 * @param in 输入流
	 * @return
	 * @throws IOException
	 */
	public static String streamToString(InputStream in) throws IOException {
		if (in==null) {
			return null;
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String str = null;
		StringBuilder sb = new StringBuilder();
		try {
			while(( str = reader.readLine() )!=null) {
				sb.append(str);
			}
		} finally {
			reader.close();
		}
		return sb.toString();
	}
	
	//清除所有列出的特殊字符
	public static String StringFilter(String str)   throws   PatternSyntaxException   {      
		  //只允许字母，数字，汉字，_    
		  String   regEx  =  "[^a-zA-Z0-9_\u4e00-\u9fa5]";	
		
		  // 清除掉指定特殊字符   
		  //String regEx="[`~!@#$%^&*()+=|{}':;',//[//]\".<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";   
		  Pattern   p   =   Pattern.compile(regEx);      
		  Matcher   m   =   p.matcher(str);      
		  return   m.replaceAll("").trim();      
	}
	
	//获取URL 中得 参数值
	public static String getURLParamterValue(String url, String name) {
		Integer index = url.indexOf("?"); 
		if (index>-1) {
			String newStr = url.substring(index+1, url.length());
			Map<String, String> map = null;
	        if (url != null && url.indexOf("&") > -1 && url.indexOf("=") > -1) { //多个参数
	            map = new HashMap<String, String>();
	             
	            String[] arrTemp = newStr.split("&");          
	            for (String str : arrTemp) {
	                String[] qs = str.split("=");
	                map.put(qs[0], qs[1]);
	            }
	        }else{ //一个参数
	        	if (newStr.indexOf(name)>-1){
	        		return newStr.substring(newStr.indexOf("=")+1, newStr.length());
	        	}else{
	        		return null;
	        	}
	        }
	        return map.get(name);
		}else{
			return null;
		}	
	}
	
	

	public static void main(String[] args) {
		String oldS = "12:30:22";
		String newS = subString(oldS, 5);
		
		System.out.println("截取前："+ oldS+"  截取后:"+newS);

		System.out.println(getURLParamterValue("http://footballer.cc/footballer/mobile/match/football/tournamentMatchVideo.html?method=video&videoId=128", "videoId"));
	}
}
