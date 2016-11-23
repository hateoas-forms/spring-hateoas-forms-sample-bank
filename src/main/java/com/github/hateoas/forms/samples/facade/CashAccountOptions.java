package com.github.hateoas.forms.samples.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.github.hateoas.forms.action.Options;
import com.github.hateoas.forms.affordance.Suggest;
import com.github.hateoas.forms.affordance.SuggestImpl;
import com.github.hateoas.forms.samples.bean.CashAccount;
import com.github.hateoas.forms.samples.dao.CashAccountDao;

@Component
public class CashAccountOptions implements Options<CashAccount> {

	@Autowired
	private CashAccountDao cashAccounts;

	@Override
	public List<Suggest<CashAccount>> get(final String[] value, final Object... args) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return SuggestImpl.wrap(cashAccounts.findCashAccountsByUsername(username), "number", "description");
	}

}
