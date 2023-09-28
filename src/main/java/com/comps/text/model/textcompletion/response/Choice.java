package com.comps.text.model.textcompletion.response;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Choice {
	
    private String text;
	private int index;
    private Object logprobs;
    private String finishReason;
    
    public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Object getLogprobs() {
		return logprobs;
	}

	public void setLogprobs(Object logprobs) {
		this.logprobs = logprobs;
	}

	public String getFinishReason() {
		return finishReason;
	}
	
	@JsonSetter(value = "finish_reason")
	public void setFinishReason(String finishReason) {
		this.finishReason = finishReason;
	}
	

}