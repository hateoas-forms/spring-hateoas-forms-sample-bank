package com.github.hateoas.forms.samples.hateoas;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.security.Principal;

import org.springframework.hateoas.Resource;

import com.github.hateoas.forms.samples.bean.CashAccount;
import com.github.hateoas.forms.samples.controllers.CashAccountController;

public class CashAccountResource extends Resource<CashAccount> {

	public CashAccountResource(CashAccount cashAccount, Principal principal) {
		super(cashAccount);

		add(linkTo(methodOn(CashAccountController.class).get(cashAccount.getNumber(), principal)).withSelfRel());
	}

}
