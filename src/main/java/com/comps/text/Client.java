package com.comps.text;

import static com.comps.text.utils.Util.createWebClient;
import static com.comps.text.utils.Util.empty;
import static com.comps.text.utils.Util.equalsIgnoreCase;
import static com.comps.text.utils.Util.exit_cmd;
import static com.comps.text.utils.Util.getTextResponse;
import static com.comps.text.utils.Util.info_msg;
import static com.comps.text.utils.Util.info_token_msg;
import static com.comps.text.utils.Util.menu_cmd;
import static com.comps.text.utils.Util.object2Json;
import static com.comps.text.utils.Util.print;
import static com.comps.text.utils.Util.printText;
import static com.comps.text.utils.Util.println;
import static com.comps.text.utils.Util.prompt_msg;
import static com.comps.text.utils.Util.sub_menu_msg;

import java.util.Scanner;

import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONObject;

import com.comps.text.model.textcompletion.request.TextCompletionRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.ws.rs.core.Response;

public class Client {

	private static String token;
	private static int max_tokens;
	private static float temperature;
	private static String model;

	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {

		initDefaults();

		print(info_msg);

		Scanner scan = new Scanner(System.in);

		while (true) {

			print(prompt_msg);

			String prompt = scan.nextLine();

			if (equalsIgnoreCase(prompt, exit_cmd))
				break;
			if (equalsIgnoreCase(prompt, empty)) {
				print(info_msg);
				continue;
			}
			if (equalsIgnoreCase(prompt, menu_cmd)) {
				execSubMenu(scan);
				continue;
			}
			if (equalsIgnoreCase(getToken(), empty)) {
				print(info_token_msg);
				continue;
			}

			WebClient client = createWebClient(token);

			TextCompletionRequest model = createModel(prompt.trim());

			JSONObject json = object2Json(model);

			try ( Response response = client.post(json.toString()) ) {

				if (response.getStatus() == 200) {
					printText(getTextResponse(response));
				} else {
					println("An error ocurred: " + response.getStatus());
					println("Reason: " + response.getStatusInfo().getReasonPhrase());
					println("Status Code: " + response.getStatusInfo().getStatusCode());
					break;
				}

			} catch (Exception e) {

				println("An error ocurred: " + e.getMessage());
				println("The application will be closed.");
				break;

			}

		}

		scan.close();
		return;

	}

	private static void initDefaults() {
		setToken("");
		setMax_tokens(500);
		setTemperature(1);
		setModel("text-davinci-003");
	}

	private static TextCompletionRequest createModel(String prompt) {
		TextCompletionRequest model = new TextCompletionRequest();
		model.setMax_tokens(getMax_tokens());
		model.setPrompt(prompt);
		model.setTemperature(getTemperature());
		model.setModel(getModel());
		return model;
	}

	private static void execSubMenu(Scanner scan) {

		int opcao = 0;

		showMenuValues();

		subMenuOptions();

		while (opcao != 5) {

			opcao = 0;

			String newOption = scan.nextLine();

			if (newOption.matches("^[0-5]$")) {
				opcao = Integer.parseInt(newOption);
			}

			switch (opcao) {
				case 1:
					insertToken(scan);
					subMenuOptions();
					break;
				case 2:
					changeModel(scan);
					subMenuOptions();
					break;
				case 3:
					changeTemperature(scan);
					subMenuOptions();
					break;
				case 4:
					changeMaxTokens(scan);
					subMenuOptions();
					break;
				case 5:
					break;
				default:
					println("Wrong option! Please Choose an option from 1 to 5.");
					subMenuOptions();
			}
		}
	}

	private static void showMenuValues() {
		println("");
		System.out.printf("%-11s %-55s\n", "temperature", ": " + String.valueOf(getTemperature()));
		System.out.printf("%-11s %-55s\n", "max tokens", ": " + String.valueOf(getMax_tokens()));
		System.out.printf("%-11s %-55s\n", "token", ": " + getToken());
		System.out.printf("%-11s %-55s\n", "model", ": " + getModel());
	}

	private static void subMenuOptions() {
		println(sub_menu_msg);
	}

	static void insertToken(Scanner scan) {
		String oldToken = getToken();

		System.out.printf("Your token is %s\n", oldToken);
		print("Insert a new  Token: ");
		String newToken = scan.nextLine();

		if (equalsIgnoreCase(oldToken, newToken))
			return;

		System.out.printf("Confirm the change for token %s? (y,N) ", newToken.trim());
		if (equalsIgnoreCase(scan.nextLine(), "y")) {
			setToken(newToken.trim());
			System.out.printf("Your token has been changed,\nfrom: %s \nto: %s\n", oldToken, newToken.trim());
		}
	}

	static void changeModel(Scanner scan) {
		println("Change model method not implemented yet!");
	}

	static void changeTemperature(Scanner scan) {
		float oldTemperature = getTemperature();
		String newTemp = scan.nextLine();

		System.out.printf("Your completions temperature is %s\n", oldTemperature);

		if (newTemp.matches("^(0(\\.\\d{0,1})?|1|1\\.0)$")) {

			float newTemperature = Float.parseFloat(newTemp);

			if (oldTemperature == newTemperature)
				return;

			System.out.printf("Confirm the change for temperature %s? (y,N) ", newTemp.trim());
			if (equalsIgnoreCase(scan.nextLine(), "y")) {
				setTemperature(newTemperature);
				System.out.printf("The Temperature was changed from: %s to: %s\n", oldTemperature, newTemp.trim());
			}

		} else {
			println("The temperature should be between 0 and 1, e.g. 0.8");
		}
	}

	static void changeMaxTokens(Scanner scan) {
		int oldMaxTokens = getMax_tokens();
		String newMaxTokens = scan.nextLine();

		if (newMaxTokens.matches("\\d+")) {

			int newMaxToken = Integer.parseInt(newMaxTokens);

			if (oldMaxTokens == newMaxToken)
				return;

			System.out.printf("Confirm Max Tokens change to %s? (y,N) ", newMaxTokens);
			if (equalsIgnoreCase(scan.nextLine(), "y")) {
				setMax_tokens(newMaxToken);
				System.out.printf("The Max Tokens has been changed,\nfrom: %s to: %s\n", oldMaxTokens, newMaxToken);
				return;
			}

		} else {

			println("Max Tokens should be only integers!");
		}
	}

	// Getters and Setters

	public static String getToken() {
		return token;
	}

	public static void setToken(String token) {
		Client.token = token;
	}

	public static int getMax_tokens() {
		return max_tokens;
	}

	public static void setMax_tokens(int max_tokens) {
		Client.max_tokens = max_tokens;
	}

	public static float getTemperature() {
		return temperature;
	}

	public static void setTemperature(float temperature) {
		Client.temperature = temperature;
	}

	public static String getModel() {
		return model;
	}

	public static void setModel(String model) {
		Client.model = model;
	}

}
