package com.github.hateoas.forms.samples.dao;

import java.util.Date;
import java.util.List;

import com.github.hateoas.forms.samples.bean.Transaction;

public interface ActivityDao {

	List<Transaction> findTransactionsByCashAccountNumber(String number);

	Transaction findTransactionByCashAccountNumberAndId(String number, Integer id);

	void insertNewActivity(Date date, String description, String number, double amount, double availablebalance);

}
