package com.github.hateoas.forms.samples.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.hateoas.forms.samples.bean.Transfer;
import com.github.hateoas.forms.samples.controllers.TransferController;
import com.github.hateoas.forms.samples.dao.ActivityDao;
import com.github.hateoas.forms.samples.dao.CashAccountDao;
import com.github.hateoas.forms.samples.dao.CreditAccountDao;
import com.github.hateoas.forms.samples.dao.TransferDao;

@Service
public class TransfersFacadeImpl implements TransfersFacade {

	@Autowired
	TransferDao transferDao;
	@Autowired
	CashAccountDao cashAccountDao;
	@Autowired
	CreditAccountDao creditAccountDao;
	@Autowired
	ActivityDao activityDaoImpl;

	public void createNewTransfer(Transfer transfer) {

		/*
		 * Insertar la nueva transferencia
		 */
		transferDao.insertTransfer(transfer);

		/*
		 * Actualizar tabla de CashAcount
		 */
		double actualAmount = cashAccountDao.getFromAccountActualAmount(transfer.getFromAccount());
		double amountTotal = actualAmount - (transfer.getAmount() + transfer.getFee());

		cashAccountDao.updateCashAccount(transfer.getFromAccount(), TransferController.round(amountTotal, 2));

		double amount = actualAmount - transfer.getAmount();
		double amountWithFees = amount - transfer.getFee();

		/*
		 * Actualizar tabla de CreditAcount
		 */

		int cashAccountId = cashAccountDao.getIdFromNumber(transfer.getFromAccount());
		creditAccountDao.updateCreditAccount(cashAccountId, TransferController.round(amountTotal, 2));

		/*
		 * Insertar nuevo movimiento
		 */
		String desc = transfer.getDescription().length() > 12 ? transfer.getDescription().substring(0, 12)
				: transfer.getDescription();
		activityDaoImpl.insertNewActivity(transfer.getDate(), "TRANSFER: " + desc, transfer.getFromAccount(),
				-TransferController.round(transfer.getAmount(), 2), TransferController.round(amount, 2));

		/*
		 * Insertar nuevo movimiento de comision
		 */
		activityDaoImpl.insertNewActivity(transfer.getDate(), "TRANSFER FEE", transfer.getFromAccount(),
				-TransferController.round(transfer.getFee(), 2), TransferController.round(amountWithFees, 2));

	}

}
