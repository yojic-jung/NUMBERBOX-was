package com.numberbox.common.util;


import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
@RequiredArgsConstructor
public class MathProblemAnalyzer {
	
	 @Value("${numberbox.openaiSecretKey}")
	 private String apiKey;
	 
	 private final Logger logger = LoggerFactory.getLogger(this.getClass());
	 
	 public String questionToChatGtp(String question, double temperature, int max_tokens, double top_p, double frequency_penalty, double presence_penalty) {
	    	  
				try {
					OkHttpClient client = new OkHttpClient().newBuilder().build();
		    	    MediaType mediaType = MediaType.parse("application/json");
		    	    
		    	    JSONObject json = new JSONObject();
		    	    json.put("prompt", question + "\nA:");
		    	    json.put("temperature", temperature);
		    	    json.put("max_tokens", max_tokens);
		    	    json.put("top_p", top_p);
		    	    json.put("frequency_penalty", frequency_penalty); 
		    	    json.put("presence_penalty", presence_penalty);
		    	    RequestBody body = RequestBody.create(mediaType, json.toString());
		    	    
		    	    Request request = new Request.Builder()
		    	        .url("https://api.openai.com/v1/engines/text-davinci-002/completions")
		    	        .method("POST", body)
		    	        .addHeader("Content-Type", "application/json")
		    	        .addHeader("Authorization", "Bearer " + apiKey)
		    	        .build();
		    	    Response response;
					response = client.newCall(request).execute();
					String responseData = response.body().string();
		    	    JSONObject jsonResponse = new JSONObject(responseData);
		    	    String answer = jsonResponse.getJSONArray("choices").getJSONObject(0).getString("text").trim();
		    	    return answer;
				} catch (IOException e) {
					logger.warn("openAi 통신 오류 발생");
				}catch (JSONException e) {
					logger.warn("openAi 사용량 초과 오류 발생");
					try {
						CommonUtil.mailSenderCustom("dywlr74@naver.com", "[N명의 수학] openAi 사용량 초과 오류 발생", "[N명의 수학] openAi 사용량 초과 오류 발생");
					}catch(AddressException except) {
						logger.warn("openAi 사용량 초과 오류 발생");
					}catch(MessagingException except) {
						logger.warn("openAi 사용량 초과 오류 발생");
					}
					
					return "";
				}
				return "nothing";
	}

}
