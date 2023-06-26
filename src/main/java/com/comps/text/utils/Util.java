package com.comps.text.utils;

import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.comps.text.model.textcompletion.request.TextCompletionRequest;
import com.comps.text.model.textcompletion.response.TextCompletionResponse;

import jakarta.ws.rs.core.Response;

public final class Util {

	public static final String prompt_msg = "\n\033[36mInsert your message: \033[0m";

	public static final String info_msg = "\033[33m\n[type exit to quit, ? for help]\n\033[0m";

	public static final String info_token_msg = "\033[33m\n[your token is empty, type ? for help]\n\033[0m";

	public static final String info_submenu = "\033[33m\n[type enter to back to main chat]\n\033[0m";

	public static final String exit_cmd = "exit";

	public static final String menu_cmd = "?";

	public static final String empty = "";

	public static final String accept = "application/json";

	public static final String content = "application/json";

	public static final String auth_header = "Authorization";

	public static final String apiUrl = "https://api.openai.com/v1/completions";

	public static final String sub_menu_msg = "\n\033[33m" 
	  + "Select an option:\n\033[36m" + "1) Insert Token\n" + "2) Change Model\n" 
	  + "3) Change Temperature\n" + "4) Change MaxTokens\n" 
	  + "5) back to main menu" + " \033[0m";

	private Util() {
	}

	public static JSONObject object2Json(TextCompletionRequest model) {
		JSONObject json = new JSONObject(model);
		return json;
	}

	public static WebClient createWebClient(String token) {
		WebClient client = WebClient.create(apiUrl);
		client.accept(accept);
		client.type(content);
		client.header(auth_header, "Bearer " + token);
		return client;
	}

	public static TextCompletionResponse getTextResponse(Response response)
			throws JsonProcessingException, JsonMappingException {

		ObjectMapper objectMapper = new ObjectMapper();

		TextCompletionResponse comp = objectMapper
				.readValue(new JSONObject(response.readEntity(String.class)).toString(), TextCompletionResponse.class);
		return comp;
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
