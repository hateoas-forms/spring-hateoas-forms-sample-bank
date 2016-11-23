package com.github.hateoas.forms.samples.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.github.hateoas.forms.samples.bean.Account;
import com.github.hateoas.forms.samples.dao.AccountDao;

public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final AccountDao accountDao;

	public CustomAuthenticationProvider(final AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		List<Account> listAccounts = new ArrayList<Account>();

		try {

			listAccounts = accountDao.findUsersByUsernameAndPassword(username.toLowerCase(), password);

		}
		catch (DataAccessException e) {

			listAccounts.clear();

		}

		if (listAccounts.isEmpty()) {
			throw new BadCredentialsException("Bad Credentials");
		}

		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(1);
		authList.add(new SimpleGrantedAuthority("ROLE_USER"));

		User user = new User(listAccounts.get(0).getUsername(), listAccounts.get(0).getPassword(), authList);

		return new UsernamePasswordAuthenticationToken(user, password, authList);
	}

	@Override
	public boolean supports(final Class<?> arg0) {
		return true;
	}
}