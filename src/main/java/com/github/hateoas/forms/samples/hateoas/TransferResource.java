package com.github.hateoas.forms.samples.hateoas;

import static com.github.hateoas.forms.spring.AffordanceBuilder.linkTo;
import static com.github.hateoas.forms.spring.AffordanceBuilder.methodOn;

import org.springframework.hateoas.Resource;

import com.github.hateoas.forms.samples.bean.Transfer;
import com.github.hateoas.forms.samples.controllers.TransferController;
import com.github.hateoas.forms.spring.AffordanceBuilder;

public class TransferResource extends Resource<Transfer> {

	public TransferResource(final Transfer transfer) {
		super(transfer);

		add(linkTo(methodOn(TransferController.class).get(transfer.getId())).withSelfRel());
		AffordanceBuilder editTransferBuilder = linkTo(
				methodOn(TransferController.class).modifyTransfer(transfer.getId(), transfer, null, null));
		add(editTransferBuilder.withRel("modify"));
		AffordanceBuilder deleteTransferBuilder = linkTo(methodOn(TransferController.class).deleteTransfer(transfer.getId(), null));
		add(deleteTransferBuilder.withRel("delete"));

	}

}
