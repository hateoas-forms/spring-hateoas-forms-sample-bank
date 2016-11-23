package com.github.hateoas.forms.samples.controllers;

import static com.github.hateoas.forms.spring.AffordanceBuilder.linkTo;
import static com.github.hateoas.forms.spring.AffordanceBuilder.methodOn;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.github.hateoas.forms.samples.bean.Account;
import com.github.hateoas.forms.samples.bean.Message;
import com.github.hateoas.forms.samples.dao.AccountDao;

@RestController
public class AuthenticationController {

	@Autowired
	AccountDao accountDao;

	@RequestMapping("/login")
	public ResponseEntity<Message> login(final Model model) {

		return new ResponseEntity<Message>(new Message("Unauthorized", true), HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping("/logout")
	public ResponseEntity<Message> logout(final Model model) {

		return new ResponseEntity<Message>(new Message("Success", false), HttpStatus.OK);
	}

	@RequestMapping(path = "/api/")
	public ResponseEntity<ResourceSupport> home(final Principal principal) {
		ResourceSupport resource = new ResourceSupport();
		List<Link> links = new ArrayList<Link>();

		String uri = ServletUriComponentsBuilder.fromCurrentServletMapping().build().toString();
		if (principal != null) {
			Link linkAccount = linkTo(methodOn(AuthenticationController.class).account(principal)).withRel("account");
			links.add(linkAccount);

			Link linkCash = linkTo(methodOn(CashAccountController.class).list(principal)).withRel("cashaccounts");
			links.add(linkCash);

			Link linkCredit = linkTo(methodOn(CreditAccountController.class).list(principal)).withRel("creditaccounts");
			links.add(linkCredit);

			Link linkMakeTransfer = linkTo(methodOn(TransferController.class).transfer(null, null, principal)).withRel("make-transfer");
			links.add(linkMakeTransfer);

			Link linkListTransfers = linkTo(methodOn(TransferController.class).get(principal)).withRel("list-transfers");
			links.add(linkListTransfers);

			Link linkListAfterDateTransfers = linkTo(
					methodOn(TransferController.class).getFiltered((Date) null, (Date) null, null, principal))
							.withRel("list-after-date-transfers");
			links.add(linkListAfterDateTransfers);

			Link linkLogout = new Link(uri + "/j_spring_security_logout", "logout");
			links.add(linkLogout);
		}
		else {
			Link linkLogin = new Link(uri + "/j_spring_security_check", "login");
			links.add(linkLogin);
		}

		resource.add(links);
		ResponseEntity<ResourceSupport> responseEntity = new ResponseEntity<ResourceSupport>(resource,
				principal != null ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
		return responseEntity;
	}

	@RequestMapping(value = "/api/account", method = RequestMethod.GET)
	public ResponseEntity<Resource<Account>> account(final Principal principal) {
		Account account = accountDao.findUsersByUsername(principal.getName()).get(0);
		Resource<Account> accountResource = new Resource<Account>(account,
				linkTo(methodOn(AuthenticationController.class).account(principal)).withSelfRel());
		return new ResponseEntity<Resource<Account>>(accountResource, HttpStatus.OK);
	}

}