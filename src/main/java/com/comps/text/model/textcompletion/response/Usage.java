package com.comps.text.model.textcompletion.response;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Usage {

	private int completionTokens;
	private int promptTokens;
	private int totalTokens;

	public int getPromptTokens() {
		return promptTokens;
	}

	@JsonSetter(value = "prompt_tokens")
	public void setPromptTokens(int promptTokens) {
		this.promptTokens = promptTokens;
	}

	public int getCompletionTokens() {
		return completionTokens;
	}

	@JsonSetter(value = "completion_tokens")
	public void setCompletionTokens(int completionTokens) {
		this.completionTokens = completionTokens;
	}

	public int getTotalTokens() {
		return totalTokens;
	}

	@JsonSetter(value = "total_tokens")
	public void setTotalTokens(int totalTokens) {
		this.totalTokens = totalTokens;
	}

}