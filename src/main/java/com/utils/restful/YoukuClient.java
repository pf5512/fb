package com.utils.restful;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public final class YoukuClient {
	
	private final static String API = "https://openapi.youku.com/v2/videos/";
	private final static String VIDEO_API = "show_basic.json?client_id=9b5a953f17e434ea&";
	private final static String BATCH_VIDEO_API = "show_basic_batch.json?client_id=9b5a953f17e434ea&";
	
	public final static String COMMON_VIDEO_URL = "v.youku.com";
	public final static String PLAYER_VIDEO_URL = "player.youku.com"; 
	
	private YoukuClient() {
		
	}
	
	private static String parseVideoId(String url) {
		if (url.indexOf(YoukuClient.PLAYER_VIDEO_URL) > -1) {
			return YoukuClient.parsePlayerVideoId(url);
		} else if (url.indexOf(YoukuClient.COMMON_VIDEO_URL) > -1) {
			return YoukuClient.parseCommonVideoId(url);
		}
		return null;
	}
	
	public static final String getVideoThumbnail(String videoUrl) {
		String videoId = parseVideoId(videoUrl);
		String url = API + VIDEO_API + "video_id=" + videoId;
		JsonObject jsonObject = RestfulClient.get(url); 
		return jsonObject.get("thumbnail").getAsString();
	}
	
	public static final List<String> batchVideoThumbnails(List<String> videoIdList) {
		List<String> thumbnails = new ArrayList<String>();
		String videoListString = StringUtils.arrayToCommaDelimitedString(videoIdList.toArray());
		String url = API + BATCH_VIDEO_API + "video_ids=" + videoListString;
		JsonArray array = RestfulClient.getBatch(url);
		
		for(JsonElement element : array) {
			JsonObject jsonObj = element.getAsJsonObject();
			thumbnails.add(jsonObj.get("thumbnail").getAsString());
		}
		
		return thumbnails;
	}
	
	/**
	 * http://v.youku.com/v_show/id_XMTUxODI0NTU5Mg==.html?from=y1.7-1.2
	 * @param url
	 * @return
	 */
	public static String parseCommonVideoId(String url) {
		String videoId = null;
		if (url.indexOf(COMMON_VIDEO_URL) > -1) {
			String pattern = "id_(\\w+)";
			Pattern compiledPattern = Pattern.compile(pattern);
	        Matcher matcher = compiledPattern.matcher(url);
	        if (matcher.find()) {
	        	videoId = matcher.group(1);
	        }
		}
        return videoId;
	}
	
	public static String parsePlayerVideoId(String url) {
		if (url.indexOf(PLAYER_VIDEO_URL) > -1) {
			String[] urlSegments = url.split("/");
			return urlSegments[urlSegments.length - 1];
		}
		return null;
	}
	
	public static void main(String[] args) {
		//http://player.youku.com/embed/XMjUzMDUwNzQ2NA==
		//"http://v.youku.com/v_show/id_XMTUxODI0NTU5Mg==.html?from=y1.7-1.2";
		//String url = "http://player.youku.com/embed/XMjUzMDUwNzQ2NA==";
		String url = "http://player.youku.com/embed/XMjY1Mjg1Njk4NA==";
		//String[] urlSegments = url.split("/");
		//String id = parseId(url);
		String image = getVideoThumbnail(url);
		//System.out.println("url:" + urlSegments[urlSegments.length - 1]);
		System.out.println("image:" + image);
	}

}
