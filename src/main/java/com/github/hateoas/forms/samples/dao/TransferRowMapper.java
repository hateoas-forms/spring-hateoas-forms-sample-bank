package com.github.hateoas.forms.samples.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.github.hateoas.forms.samples.bean.Transfer;
import com.github.hateoas.forms.samples.bean.TransferOptions;
import com.github.hateoas.forms.samples.bean.TransferStatus;
import com.github.hateoas.forms.samples.bean.TransferType;

public class TransferRowMapper implements RowMapper<Transfer> {

	@Override
	public Transfer mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		Transfer transfer = new Transfer();
		transfer.setId(rs.getInt("id"));
		transfer.setFromAccount(rs.getString("fromAccount"));
		transfer.setToAccount(rs.getString("toAccount"));
		transfer.setDescription(rs.getString("description"));
		transfer.setAmount(rs.getDouble("amount"));
		transfer.setFee(rs.getDouble("fee"));
		transfer.setUsername(rs.getString("username"));
		transfer.setDate(rs.getDate("date"));
		transfer.setType(TransferType.valueOf(rs.getString("type")));
		transfer.setStatus(TransferStatus.valueOf(rs.getString("status")));
		transfer.setEmail(rs.getString("email"));
		transfer.setTelephone(rs.getString("telephone"));
		String[] options = rs.getString("options").split(",");
		for (String transferOptions : options) {
			if (transferOptions.length() > 0) {
				transfer.getOptions().add(TransferOptions.valueOf(transferOptions));
			}
		}

		return transfer;
	}

}
