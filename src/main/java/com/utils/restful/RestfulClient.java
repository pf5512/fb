package com.utils.restful;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public final class RestfulClient {

	private RestfulClient() {

	}

	public static JsonObject get(String restUrl) {
		JsonObject json = null;
		StringBuilder stringBuilder = getJsonString(restUrl);
		JsonParser parser = new JsonParser();
		json = parser.parse(stringBuilder.toString()).getAsJsonObject();
		return json;
	}
	
	public static JsonArray getBatch(String restUrl) {
		StringBuilder stringBuilder = getJsonString(restUrl);
		JsonParser parser = new JsonParser();
		System.out.println("------> batch string:" + stringBuilder.toString());
		JsonObject json = parser.parse(stringBuilder.toString()).getAsJsonObject();
		JsonArray array = json.get("videos").getAsJsonArray();		
		return array;
	}


	private static StringBuilder getJsonString(String restUrl) {
		StringBuilder stringBuilder = new StringBuilder();
		HttpURLConnection conn = null;
		
		try {
			URL url = new URL(restUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				stringBuilder.append(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {
			conn.disconnect();
		} catch (IOException e) {
			conn.disconnect();
		} finally {
			conn.disconnect();
		}
		return stringBuilder;
	}
}
