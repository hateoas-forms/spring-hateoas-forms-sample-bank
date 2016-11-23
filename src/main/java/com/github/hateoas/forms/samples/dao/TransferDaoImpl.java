package com.github.hateoas.forms.samples.dao;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.github.hateoas.forms.samples.bean.Transfer;
import com.github.hateoas.forms.samples.bean.TransferStatus;

@Repository
public class TransferDaoImpl implements TransferDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Integer autoincrement = 10;

	public void setJdbcTemplate(final JdbcTemplate paramJdbcTemplate) {
		jdbcTemplate = paramJdbcTemplate;
	}

	@Override
	public void insertTransfer(final Transfer transfer) {

		String sql = "INSERT INTO transfer "
				+ "(id, fromAccount, toAccount, description, amount, fee, username, date, type, status, options, email, telephone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		transfer.setId(++autoincrement);

		StringBuilder options = new StringBuilder("");
		for (int i = 0; i < transfer.getOptions().size(); i++) {
			if (i != 0) {
				options.append(',');
			}
			options.append(transfer.getOptions().get(i));
		}

		jdbcTemplate.update(sql,
				new Object[] { transfer.getId(), transfer.getFromAccount(), transfer.getToAccount(), transfer.getDescription(),
						transfer.getAmount(), transfer.getFee(), transfer.getUsername(), transfer.getDate(), transfer.getType(),
						TransferStatus.PENDING, options.toString(), transfer.getEmail(), transfer.getTelephone() });

	}

	@Override
	public Transfer findById(final Integer id) {
		String sql = "SELECT * FROM transfer WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] { id }, new TransferRowMapper());
	}

	@Override
	public List<Transfer> findByUserName(final String userName) {
		String sql = "SELECT * FROM transfer WHERE username = ?";
		return jdbcTemplate.query(sql, new Object[] { userName }, new TransferRowMapper());
	}

	@Override
	public List<Transfer> findAfterDate(final String userName, final Date dateFrom, final Date dateTo, final TransferStatus status) {
		StringBuilder sb = new StringBuilder(100);
		sb.append("SELECT * FROM transfer WHERE username = ? ");
		List<Object> queryValues = new LinkedList<>();
		queryValues.add(userName);
		if (dateFrom != null) {
			sb.append(" AND date >= ? ");
			queryValues.add(dateFrom);
		}
		if (dateTo != null) {
			sb.append(" AND date <= ? ");
			queryValues.add(dateTo);
		}
		if (status != null) {
			sb.append(" AND status = ?");
			queryValues.add(status);
		}
		return jdbcTemplate.query(sb.toString(), queryValues.toArray(), new TransferRowMapper());
	}

	@Override
	public void deleteTransfer(final Integer id, final String userName) {
		String sql = "DELETE FROM transfer WHERE id = ? AND username = ?";
		jdbcTemplate.update(sql, new Object[] { id, userName });
	}
}
