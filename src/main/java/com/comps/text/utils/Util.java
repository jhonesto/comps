package com.comps.text.utils;

import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONObject;

import com.comps.text.model.textcompletion.request.TextCompletionRequest;
import com.comps.text.model.textcompletion.response.TextCompletionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.core.Response;

public final class Util {

	public static final String PROMPT_MESSAGE = "\n\033[36mInsert your message: \033[0m";

	public static final String INFO_MSG = "\033[33m\n[type exit to quit, ? for help]\n\033[0m";

	public static final String INFO_TOKEN_MSG = "\033[33m\n[your token is empty, type ? for help]\n\033[0m";

	public static final String INFO_SUBMENU = "\033[33m\n[type enter to back to main chat]\n\033[0m";

	public static final String EXIT_CMD = "exit";

	public static final String MENU_CMD = "?";

	public static final String EMPTY = "";

	public static final String ACCEPT = "application/json";

	public static final String CONTENT = "application/json";

	public static final String AUTHORIZATION = "Authorization";

	public static final String API_URL = "https://api.openai.com/v1/completions";

	public static final String SUB_MENU_MSG = "\n\033[33m" 
		+ "Select an option:\n"
		+ "\033[36m" 
		+ "1) Insert Token\n" 
		+ "2) Change Model\n" 
		+ "3) Change Temperature\n" 
		+ "4) Change MaxTokens\n" 
		+ "5) back to main menu"
		+ " \033[0m";

	private Util() {
	}

	public static JSONObject object2Json(TextCompletionRequest model) {
		return new JSONObject(model);

	}

	public static WebClient createWebClient(String token) {
		WebClient client = WebClient.create(API_URL);
		client.accept(ACCEPT);
		client.type(CONTENT);
		client.header(AUTHORIZATION, "Bearer " + token);
		return client;
	}

	public static TextCompletionResponse getTextResponse(Response response) throws JsonProcessingException {

		return new ObjectMapper().readValue(new JSONObject(response.readEntity(String.class)).toString(),
				TextCompletionResponse.class);

	}

	public static void printText(TextCompletionResponse comp) {
		System.out.println("\033[33m" + comp.getChoices().get(0).getText().trim() + "\033[0m");
	}

	public static void print(String str) {
		System.out.print(str);
	}

	public static void println(String str) {
		System.out.println(str);
	}

	public static boolean equalsIgnoreCase(String str1, String str2) {
		return str1.trim().equalsIgnoreCase(str2.trim());
	}
}
