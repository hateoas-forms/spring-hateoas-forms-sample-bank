package com.github.hateoas.forms.samples.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.github.hateoas.forms.samples.bean.Transaction;

@Repository
public class ActivityDaoImpl implements ActivityDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate paramJdbcTemplate) {
		this.jdbcTemplate = paramJdbcTemplate;
	}

	public List<Transaction> findTransactionsByCashAccountNumber(String number) {
		String sql = "SELECT * FROM transaction WHERE number = ? ORDER BY date DESC";

		List<Transaction> customers = jdbcTemplate.query(sql, new Object[] { number },
				BeanPropertyRowMapper.newInstance(Transaction.class));

		return customers;

	}

	public Transaction findTransactionByCashAccountNumberAndId(String number, Integer id) {
		String sql = "SELECT * FROM transaction WHERE number = ? AND id = ?";

		return jdbcTemplate.queryForObject(sql, new Object[] { number, id },
				BeanPropertyRowMapper.newInstance(Transaction.class));

	}

	public void insertNewActivity(Date date, String description, String number, double amount,
			double availablebalance) {

		try {

			String sql = "INSERT INTO transaction "
					+ "(date, description, number, amount, availablebalance) VALUES (?, ?, ?, ?, ?)";

			jdbcTemplate.update(sql, new Object[] { date, description, number, amount, availablebalance, });
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
