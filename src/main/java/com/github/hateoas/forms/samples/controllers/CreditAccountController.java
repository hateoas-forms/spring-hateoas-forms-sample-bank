package com.github.hateoas.forms.samples.controllers;

import static com.github.hateoas.forms.spring.AffordanceBuilder.linkTo;
import static com.github.hateoas.forms.spring.AffordanceBuilder.methodOn;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.hateoas.forms.samples.bean.CreditAccount;
import com.github.hateoas.forms.samples.dao.CreditAccountDao;

@RestController
@RequestMapping("/api/creditaccounts")
public class CreditAccountController {

	@Autowired
	private CreditAccountDao creditaccountDao;

	@RequestMapping(method = RequestMethod.GET)
	public Resources<Resource<CreditAccount>> list(final Principal principal) {
		List<Resource<CreditAccount>> list = new ArrayList<Resource<CreditAccount>>();
		for (CreditAccount creditAcc : creditaccountDao.findCreditAccountsByUsername(principal.getName())) {
			Resource<CreditAccount> res = new Resource<CreditAccount>(creditAcc);
			list.add(res);
		}

		Resources<Resource<CreditAccount>> resources = new Resources<Resource<CreditAccount>>(list);
		resources.add(linkTo(methodOn(CreditAccountController.class).list(principal)).withSelfRel());
		return resources;
	}
}
