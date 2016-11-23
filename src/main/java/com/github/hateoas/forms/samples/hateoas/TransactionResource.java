package com.github.hateoas.forms.samples.hateoas;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.security.Principal;

import org.springframework.hateoas.Resource;

import com.github.hateoas.forms.samples.bean.Transaction;
import com.github.hateoas.forms.samples.controllers.CashAccountController;

public class TransactionResource extends Resource<Transaction> {

	public TransactionResource(Transaction content, String number, Principal principal) {
		super(content);

		add(linkTo(methodOn(CashAccountController.class).getTx(number, content.getId(), principal)).withSelfRel());
	}

}
