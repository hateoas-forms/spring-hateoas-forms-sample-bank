package com.github.hateoas.forms.samples.dao;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.github.hateoas.forms.samples.bean.Account;

@Repository
public class AccountDaoImpl implements AccountDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Account> findUsersByUsernameAndPassword(String username, String password){
		 
		String str = "select * from account where username='" + username + "' AND password='" + password + "'";
		
		RowMapper<Account> rowMapper = new RowMapper<Account>() {
			public Account mapRow(ResultSet paramResultSet, int paramInt) throws java.sql.SQLException {
				Account localAccount = new Account();
				localAccount.setUsername(paramResultSet.getString("username"));
				localAccount.setName(paramResultSet.getString("name"));
				localAccount.setSurname(paramResultSet.getString("surname"));
				localAccount.setPassword(paramResultSet.getString("password"));
				return localAccount;
			}
		}; 
		
		return this.jdbcTemplate.query(str, rowMapper);
	}
	 

	public void setJdbcTemplate(JdbcTemplate paramJdbcTemplate) {
		this.jdbcTemplate = paramJdbcTemplate;
	}


	public List<Account> findUsersByUsername(String username) {
		String str = "select * from account where username='" + username + "'";

		RowMapper<Account> rowMapper = new RowMapper<Account>() {
			public Account mapRow(ResultSet paramResultSet, int paramInt) throws java.sql.SQLException {
				Account localAccount = new Account();
				localAccount.setUsername(paramResultSet.getString("username"));
				localAccount.setName(paramResultSet.getString("name"));
				localAccount.setSurname(paramResultSet.getString("surname"));
				localAccount.setPassword(paramResultSet.getString("password"));
				return localAccount;
			}
		}; 
		
		return this.jdbcTemplate.query(str, rowMapper);
	}
}
