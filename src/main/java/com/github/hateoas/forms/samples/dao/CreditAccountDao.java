package com.github.hateoas.forms.samples.dao;

import java.util.List;

import com.github.hateoas.forms.samples.bean.CreditAccount;

public interface CreditAccountDao {

	public List<CreditAccount> findCreditAccountsByUsername(String username);

	public void updateCreditAccount(int cashAccountId, double round);

}
