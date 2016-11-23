package com.github.hateoas.forms.samples.bean;

public class Message {
	private String text;
	private boolean error;

	public Message(String text, boolean error) {
		this.text = text;
		this.error = error;
	}

	public String getText() {
		return text;
	}

	public boolean isError() {
		return error;
	}
}
