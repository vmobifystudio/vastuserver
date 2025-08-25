package com.vishwakarma.vastu.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.map.ObjectMapper;

import com.vishwakarma.vastu.api.ApiResponse;

/**
 * @author Vishal
 *
 */
public class HttpUtils {

	public static ApiResponse sendSecuredHttpGet(String host, String path, Map<String, String> params) throws URISyntaxException {
		
		ApiResponse apiResponse = new ApiResponse(false);
		URIBuilder uriBuilder = new URIBuilder();
		uriBuilder.setScheme("https");
		uriBuilder.setHost(host);
		uriBuilder.setPath(path);
		for (String key : params.keySet()) {
			uriBuilder.addParameter(key, params.get(key));
		}
		URI uri = uriBuilder.build();
		
		HttpGet httpGet = new HttpGet(uri);
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		HttpClient httpClient = httpClientBuilder.build();
		
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			int responseCode = httpResponse.getStatusLine().getStatusCode();
			ObjectMapper objectMapper = new ObjectMapper();
			if(responseCode == 200) {
				Map<String, Object> map = objectMapper.readValue(httpResponse.getEntity().getContent(), Map.class);
				apiResponse.setData(map);
				apiResponse.setSuccess(true);
			} else {
				String string = objectMapper.readValue(httpResponse.getEntity().getContent(), String.class);
				apiResponse.setError(string, responseCode + "");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return apiResponse;
	}
}
