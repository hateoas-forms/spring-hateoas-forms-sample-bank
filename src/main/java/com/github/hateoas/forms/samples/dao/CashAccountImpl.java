package com.github.hateoas.forms.samples.dao;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.github.hateoas.forms.samples.bean.CashAccount;

@Repository
public class CashAccountImpl implements CashAccountDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<CashAccount> findCashAccountsByUsername(String username) {

		String str = "select * from cashaccount  where username='" + username + "' order by id";

		RowMapper<CashAccount> rowMapper = new RowMapper<CashAccount>() {
			public CashAccount mapRow(ResultSet paramResultSet, int paramInt) throws java.sql.SQLException {
				CashAccount localAccount = new CashAccount();
				localAccount.setId(paramResultSet.getInt("id"));
				localAccount.setNumber(paramResultSet.getString("number"));
				localAccount.setUsername(paramResultSet.getString("username"));
				localAccount.setAvailableBalance(paramResultSet.getDouble("availablebalance"));
				localAccount.setDescription(paramResultSet.getString("description"));
				return localAccount;
			}
		};
		return this.jdbcTemplate.query(str, rowMapper);
	}

	public void setJdbcTemplate(JdbcTemplate paramJdbcTemplate) {
		this.jdbcTemplate = paramJdbcTemplate;
	}

	public double getFromAccountActualAmount(String fromAccount) {

		String sql = "SELECT availablebalance FROM cashaccount WHERE number = ?";

		double id = jdbcTemplate.queryForObject(sql, new Object[] { fromAccount }, Double.class);

		return id;

	}

	public void updateCashAccount(String fromAccount, double amountTotal) {

		String sql = "UPDATE cashaccount SET availablebalance= ? WHERE number = ?";

		jdbcTemplate.update(sql, new Object[] { amountTotal, fromAccount });
	}

	public int getIdFromNumber(String fromAccount) {

		String sql = "SELECT id FROM cashaccount WHERE number = ?";

		int id = jdbcTemplate.queryForObject(sql, new Object[] { fromAccount }, Integer.class);

		return id;

	}

	@Override
	public List<CashAccount> findAccounts(String filter, String name) {
		String str = "select ca.id as id, ca.number as number, ca.description as description, ca.availablebalance as availablebalance, a.name as name, a.surname as surname from cashaccount ca join account a ON a.username = ca.username where ca.number LIKE ? and ca.username != ? order by id";

		RowMapper<CashAccount> rowMapper = new RowMapper<CashAccount>() {
			public CashAccount mapRow(ResultSet paramResultSet, int paramInt) throws java.sql.SQLException {
				CashAccount localAccount = new CashAccount();
				localAccount.setId(paramResultSet.getInt("id"));
				localAccount.setNumber(paramResultSet.getString("number"));
				localAccount.setUsername(paramResultSet.getString("name") + " " + paramResultSet.getString("surname"));
				localAccount.setAvailableBalance(paramResultSet.getDouble("availablebalance"));
				localAccount.setDescription(paramResultSet.getString("description"));
				return localAccount;
			}
		};
		return this.jdbcTemplate.query(str, new Object[] { filter + "%", name }, rowMapper);
	}

}
