package com.github.hateoas.forms.samples.dao;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.github.hateoas.forms.samples.bean.CreditAccount;

@Repository
public class CreditAccountImpl implements CreditAccountDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<CreditAccount> findCreditAccountsByUsername(String username) {
	
		String str = "select * from creditaccount  where username='" + username + "'";
		
			RowMapper<CreditAccount> rowMapper = new RowMapper<CreditAccount>() {
				public CreditAccount mapRow(ResultSet paramResultSet, int paramInt) throws java.sql.SQLException {
					CreditAccount localAccount = new CreditAccount();
					localAccount.setId(paramResultSet.getInt("id"));
					localAccount.setNumber(paramResultSet.getString("number"));
					localAccount.setUsername(paramResultSet.getString("username"));
					localAccount.setDescription(paramResultSet.getString("description"));
					localAccount.setAvailableBalance(paramResultSet.getDouble("availablebalance"));
				return localAccount;
				}
			}; 
			return this.jdbcTemplate.query(str, rowMapper);
		}	

	public void setJdbcTemplate(JdbcTemplate paramJdbcTemplate) {
		this.jdbcTemplate = paramJdbcTemplate;
	}

	public void updateCreditAccount(int cashAccountId, double round) {

		String sql = "UPDATE creditAccount SET availablebalance='"+round+"' WHERE cashaccountid ='"+ cashAccountId+ "'";
		jdbcTemplate.update(sql);
	}

}
