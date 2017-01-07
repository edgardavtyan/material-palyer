package com.edavtyan.materialplayer.utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

public class WebClient {
	private final OkHttpClient client;

	public WebClient() {
		this.client = new OkHttpClient();
	}

	public byte[] getBytes(String url) {
		try {
			return get(url).bytes();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getString(String url) {
		try {
			return get(url).string();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private ResponseBody get(String url) {
		try {
			Request request = new Request.Builder().url(url).build();
			return client.newCall(request).execute().body();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
