package com.comps.text.model.textcompletion.request;

public class TextCompletionRequest {
    private String model;
    private String prompt;
    private float temperature;
    private int max_tokens; 

    public TextCompletionRequest(){
    }

    public TextCompletionRequest(String model, String prompt, float temperature, int max_tokens){
        this.model = model;
        this.prompt = prompt;
        this.temperature = temperature;
        this.max_tokens = max_tokens;
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

    public int getMax_tokens() {
        return max_tokens;
    }

    public void setMax_tokens(int max_tokens) {
        this.max_tokens = max_tokens;
    }
}

