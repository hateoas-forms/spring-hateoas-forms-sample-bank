package com.github.hateoas.forms.samples.controllers;

import static com.github.hateoas.forms.spring.AffordanceBuilder.linkTo;
import static com.github.hateoas.forms.spring.AffordanceBuilder.methodOn;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.hateoas.forms.samples.bean.CashAccount;
import com.github.hateoas.forms.samples.bean.Transaction;
import com.github.hateoas.forms.samples.dao.ActivityDao;
import com.github.hateoas.forms.samples.dao.CashAccountDao;
import com.github.hateoas.forms.samples.hateoas.CashAccountResource;
import com.github.hateoas.forms.samples.hateoas.TransactionResource;

@RestController
@RequestMapping("/api/cashaccounts")
public class CashAccountController {

	@Autowired
	private CashAccountDao cashaccountDao;

	@Autowired
	private ActivityDao activityDao;

	@RequestMapping(method = RequestMethod.GET)
	public Resources<CashAccountResource> list(final Principal principal) {
		List<CashAccount> cashAccounts = cashaccountDao.findCashAccountsByUsername(principal.getName());
		List<CashAccountResource> resources = new ArrayList<CashAccountResource>();
		for (CashAccount cashAcc : cashAccounts) {
			resources.add(new CashAccountResource(cashAcc, principal));
		}
		return new Resources<CashAccountResource>(resources, linkTo(methodOn(CashAccountController.class).list(principal)).withSelfRel());
	}

	@RequestMapping(method = RequestMethod.GET, params = "filter")
	public Resources<CashAccountResource> search(@RequestParam String filter, final Principal principal) {
		List<CashAccount> cashAccounts = cashaccountDao.findAccounts(filter, principal.getName());
		List<CashAccountResource> resources = new ArrayList<CashAccountResource>();
		for (CashAccount cashAcc : cashAccounts) {
			resources.add(new CashAccountResource(cashAcc, principal));
		}
		return new Resources<CashAccountResource>(resources, linkTo(methodOn(CashAccountController.class).list(principal)).withSelfRel());
	}

	@RequestMapping(path = "/{number}", method = RequestMethod.GET)
	public Resources<TransactionResource> get(@PathVariable("number")
	final String number, final Principal principal) {
		List<TransactionResource> firstCashAccountTransfers = new ArrayList<TransactionResource>();
		for (Transaction tx : activityDao.findTransactionsByCashAccountNumber(number)) {
			firstCashAccountTransfers.add(new TransactionResource(tx, number, principal));
		}

		Resources<TransactionResource> resources = new Resources<TransactionResource>(firstCashAccountTransfers,
				linkTo(methodOn(CashAccountController.class).get(number, principal)).withSelfRel());

		return resources;
	}

	@RequestMapping(path = "/{number}/{id}", method = RequestMethod.GET)
	public TransactionResource getTx(@PathVariable("number")
	final String number, @PathVariable("id")
	final Integer id, final Principal principal) {
		Transaction tx = activityDao.findTransactionByCashAccountNumberAndId(number, id);
		return new TransactionResource(tx, number, principal);
	}

}
