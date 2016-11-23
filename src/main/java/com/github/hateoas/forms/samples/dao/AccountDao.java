package com.github.hateoas.forms.samples.dao;

import java.util.List;

import com.github.hateoas.forms.samples.bean.Account;

public interface AccountDao {

	public List<Account> findUsersByUsernameAndPassword(String username, String password);
	public List<Account> findUsersByUsername(String username);

}
