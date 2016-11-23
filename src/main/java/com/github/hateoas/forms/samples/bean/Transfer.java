package com.github.hateoas.forms.samples.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.hateoas.forms.action.Input;
import com.github.hateoas.forms.action.Select;
import com.github.hateoas.forms.action.Type;
import com.github.hateoas.forms.affordance.SuggestType;
import com.github.hateoas.forms.samples.facade.CashAccountFilteredOptions;
import com.github.hateoas.forms.samples.facade.CashAccountOptions;

public class Transfer {

	private Integer id;

	private String fromAccount;

	private String toAccount;

	private String description;

	private double amount;

	private double fee = 5;

	private String username;

	private Date date;

	private TransferType type;

	private TransferStatus status;

	private List<TransferOptions> options = new ArrayList<>();

	private String email;

	private String telephone;

	public Integer getId() {
		return id;
	}

	public void setId(@Input(value = Type.HIDDEN, editable = false) final Integer id) {
		this.id = id;
	}

	public String getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(@Select(type = SuggestType.EXTERNAL, options = CashAccountOptions.class) final String fromAccount) {
		this.fromAccount = fromAccount;
	}

	public String getToAccount() {
		return toAccount;
	}

	public void setToAccount(@Select(type = SuggestType.REMOTE, options = CashAccountFilteredOptions.class) final String toAccount) {
		this.toAccount = toAccount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(@Input(editable = true, required = true) final String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(@Input(editable = true, required = true) final double amount) {
		this.amount = amount;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(final double fee) {
		this.fee = fee;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public TransferType getType() {
		return type;
	}

	public void setType(final TransferType type) {
		this.type = type;
	}

	public TransferStatus getStatus() {
		return status;
	}

	public void setStatus(final TransferStatus status) {
		this.status = status;
	}

	public void setOptions(
			@Select(type = SuggestType.INTERNAL, options = TransferOptionsSuggest.class) final List<TransferOptions> options) {
		this.options = options;
	}

	public List<TransferOptions> getOptions() {
		return options;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(@Input(value = Type.EMAIL) final String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(@Input(value = Type.TEL) final String telephone) {
		this.telephone = telephone;
	}

}
