package com.github.hateoas.forms.samples.bean;

import org.springframework.hateoas.Identifiable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CashAccount implements Identifiable<String> {

	private Integer id;

	private String number;

	private String username;

	private double availableBalance;

	private String description;

	@Override
	public String getId() {
		return number;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CashAccount other = (CashAccount) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
