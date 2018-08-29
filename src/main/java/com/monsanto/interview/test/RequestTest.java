package com.monsanto.interview.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class RequestTest {

	@Test
	public void	givenRequestWithNoAcceptHeader_whenRequestIsExecuted_thenDefaultResponseContentTypeIsJson() throws ClientProtocolException, IOException {
		
		HttpUriRequest requestSarah = new HttpGet( "http://localhost:8080/generateFortuneCookie?client=Sarah&company=MegaMarket");
		HttpUriRequest requestBarney = new HttpGet( "http://localhost:8080/generateFortuneCookie?client=Barney&company=SuperStore");
		
		// Then
		JSONObject resultJson = getJsonResponse(requestSarah);
		
		//assert
		assertTrue(resultJson.has("id"));
		assertTrue(resultJson.has("message"));
		
		String resultMessage = resultJson.get("message").toString();
		assertTrue(resultMessage.contains("Sarah"));
		assertTrue(resultMessage.contains("MegaMarket"));
		
		// Then
		resultJson = getJsonResponse(requestBarney);
		
		//assert
		resultMessage = resultJson.get("message").toString();
		assertTrue(resultMessage.contains("Barney"));
		assertTrue(resultMessage.contains("SuperStore"));
				
		
	}
	
	private JSONObject getJsonResponse(HttpUriRequest request) throws ClientProtocolException, IOException {
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		HttpEntity entity = response.getEntity();
		String responseString = EntityUtils.toString(entity, "UTF-8");
		
		return new JSONObject(responseString);
		
	}
}
