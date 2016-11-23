package com.github.hateoas.forms.samples.dao;

import java.util.Date;
import java.util.List;

import com.github.hateoas.forms.samples.bean.Transfer;
import com.github.hateoas.forms.samples.bean.TransferStatus;

public interface TransferDao {

	void insertTransfer(Transfer transfer);

	Transfer findById(Integer id);

	List<Transfer> findByUserName(String userName);

	List<Transfer> findAfterDate(String userName, final Date dateFrom, final Date dateTo, final TransferStatus status);

	void deleteTransfer(Integer id, String userName);

}
