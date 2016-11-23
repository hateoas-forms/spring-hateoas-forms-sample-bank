package com.github.hateoas.forms.samples.dao;

import java.util.List;

import com.github.hateoas.forms.samples.bean.CashAccount;

public interface CashAccountDao {

	public List<CashAccount> findCashAccountsByUsername(String username);

	public double getFromAccountActualAmount(String fromAccount);

	public void updateCashAccount(String fromAccount, double amountTotal);

	public int getIdFromNumber(String fromAccount);

	public List<CashAccount> findAccounts(String filter, String name);

}
