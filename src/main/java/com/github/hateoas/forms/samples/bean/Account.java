package com.github.hateoas.forms.samples.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {

	private String username;
	private String name;
	private String surname;

	@JsonIgnore
	private String password;
}
