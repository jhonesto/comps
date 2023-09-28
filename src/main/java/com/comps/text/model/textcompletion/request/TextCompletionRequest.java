package com.comps.text.model.textcompletion.request;

import org.json.JSONPropertyName;

public class TextCompletionRequest {
    private String model;
    private String prompt;
    private float temperature;
    
    private int maxTokens; 

    public TextCompletionRequest(){
    }

    public TextCompletionRequest(String model, String prompt, float temperature, int maxTokens){
        this.model = model;
        this.prompt = prompt;
        this.temperature = temperature;
        this.maxTokens = maxTokens;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    @JSONPropertyName(value = "max_tokens")
	public int getMaxTokens() {
		return maxTokens;
	}

	public void setMaxTokens(int maxTokens) {
		this.maxTokens = maxTokens;
	}
}

