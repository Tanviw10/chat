package com.ai.chat.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ai.chat.modules.ChatMessage;

@Service
public class SarvamAiService {

	@Value("${sarvam.api.key}")
	private String apiKey;

	@Value("${sarvam.model}")
	private String model;

	private final RestTemplate restTemplate =
			new RestTemplate();

	public String askSarvam(
			List<ChatMessage> history,
			String userMessage) {

		try {

			String url =
				"https://api.sarvam.ai/v1/chat/completions";

			List<Map<String, String>> messages =
					new ArrayList<>();

			messages.add(
				Map.of(
					"role",
					"system",
					"content",
					"You are a helpful AI Assistant."
				)
			);

			for (ChatMessage msg : history) {

				messages.add(
					Map.of(
						"role",
						msg.getRole(),
						"content",
						msg.getContent()
					)
				);
			}

			messages.add(
				Map.of(
					"role",
					"user",
					"content",
					userMessage
				)
			);

			Map<String, Object> body =
					new HashMap<>();

			body.put("model", model);
			body.put("messages", messages);
			body.put("temperature", 0.2);
			body.put("max_tokens", 1000);

			HttpHeaders headers =
					new HttpHeaders();

			headers.setContentType(
					MediaType.APPLICATION_JSON);

			headers.setBearerAuth(apiKey);

			HttpEntity<Map<String, Object>> entity =
					new HttpEntity<>(body, headers);

			ResponseEntity<Map> response =
					restTemplate.postForEntity(
						url,
						entity,
						Map.class
					);

			Map responseBody = response.getBody();

			if (responseBody == null) {
				return "No response from AI.";
			}

			List choices =
					(List) responseBody.get("choices");

			if (choices == null || choices.isEmpty()) {
				return "No AI response generated.";
			}

			Map firstChoice =
					(Map) choices.get(0);

			Map message =
					(Map) firstChoice.get("message");

			return message.get("content").toString();

		} catch (Exception e) {

			e.printStackTrace();

			return "AI service error.";
		}
	}
}