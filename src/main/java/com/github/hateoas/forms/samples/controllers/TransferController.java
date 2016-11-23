package com.github.hateoas.forms.samples.controllers;

import static com.github.hateoas.forms.spring.AffordanceBuilder.linkTo;
import static com.github.hateoas.forms.spring.AffordanceBuilder.methodOn;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.hateoas.forms.action.Input;
import com.github.hateoas.forms.action.Type;
import com.github.hateoas.forms.samples.bean.Transfer;
import com.github.hateoas.forms.samples.bean.TransferStatus;
import com.github.hateoas.forms.samples.dao.TransferDao;
import com.github.hateoas.forms.samples.facade.TransfersFacade;
import com.github.hateoas.forms.samples.hateoas.TransferResource;
import com.github.hateoas.forms.spring.AffordanceBuilder;

@RestController
@RequestMapping(value = "/api/transfer")
public class TransferController {
	@Autowired
	private TransfersFacade transfersFacade;

	@Autowired
	private TransferDao transferDao;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Resource<Transfer>> transfer(@Valid @RequestBody final Transfer transfer, final BindingResult bindingResult,
			final Principal principal) {

		transfer.setUsername(principal.getName());
		transfer.setDate(new Date());

		double amount = transfer.getAmount();
		transfer.setAmount(round(amount, 2));

		double feeAmount = transfer.getAmount() * transfer.getFee() / 100.0;
		transfer.setFee(round(feeAmount, 2));

		transfersFacade.createNewTransfer(transfer);

		// HttpHeaders responseHeaders = new HttpHeaders();
		// responseHeaders.setLocation(linkTo(methodOn(TransferController.class).get(transfer.getId())).toUri());
		// return new ResponseEntity<Resource<Transfer>>(responseHeaders, HttpStatus.CREATED);

		Resource<Transfer> resource = new Resource<Transfer>(transfer);
		resource.add(linkTo(methodOn(TransferController.class).get(transfer.getId())).withSelfRel());
		return new ResponseEntity<Resource<Transfer>>(resource, HttpStatus.CREATED);

	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Resource<Transfer>> modifyTransfer(@PathVariable("id") final Integer id,
			@Valid @RequestBody final Transfer transfer, final BindingResult bindingResult, final Principal principal) {

		transfer.setUsername(principal.getName());
		transfer.setDate(new Date());

		double amount = transfer.getAmount();
		transfer.setAmount(round(amount, 2));

		double feeAmount = transfer.getAmount() * transfer.getFee() / 100.0;
		transfer.setFee(round(feeAmount, 2));

		transfersFacade.createNewTransfer(transfer);

		Resource<Transfer> resource = new Resource<Transfer>(transfer);
		resource.add(linkTo(methodOn(TransferController.class).get(transfer.getId())).withSelfRel());
		return new ResponseEntity<Resource<Transfer>>(resource, HttpStatus.CREATED);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteTransfer(@PathVariable("id") final Integer id, final Principal principal) {

		transferDao.deleteTransfer(id, principal.getName());

		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public Resource<Transfer> get(@Input(required = true, pattern = "^[1-9]\\d*$") @PathVariable("id") final Integer id) {
		Transfer transfer = transferDao.findById(id);

		Resource<Transfer> resource = new TransferResource(transfer);
		AffordanceBuilder editTransferBuilder = linkTo(methodOn(TransferController.class).modifyTransfer(id, transfer, null, null));

		resource.add(linkTo(methodOn(TransferController.class).get(transfer.getId())).and(editTransferBuilder).withSelfRel());
		return resource;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Resources<TransferResource> get(final Principal principal) {
		List<Transfer> transfers = transferDao.findByUserName(principal.getName());

		List<TransferResource> resources = new ArrayList<TransferResource>();
		for (Transfer transfer : transfers) {
			resources.add(new TransferResource(transfer));
		}

		return new Resources<TransferResource>(resources, linkTo(methodOn(TransferController.class).get(principal)).withSelfRel(),
				linkTo(methodOn(TransferController.class).getFiltered((Date) null, (Date) null, null, principal))
						.withRel("list-after-date-transfers"));
	}

	@RequestMapping(path = "/filter", method = RequestMethod.GET)
	public Resources<TransferResource> getFiltered(
			@RequestParam(name = "dateFrom", required = false) @Input(value = Type.DATE) @DateTimeFormat(pattern = "yyyy-MM-dd") final Date dateFrom,
			@RequestParam(name = "dateTo", required = false) @Input(value = Type.DATE) @DateTimeFormat(pattern = "yyyy-MM-dd") final Date dateTo,
			@RequestParam(name = "status", required = false) final TransferStatus status, final Principal principal) {
		List<Transfer> transfers = transferDao.findAfterDate(principal.getName(), dateFrom, dateTo, status);

		List<TransferResource> resources = new ArrayList<TransferResource>();
		for (Transfer transfer : transfers) {
			resources.add(new TransferResource(transfer));
		}

		return new Resources<TransferResource>(resources, linkTo(methodOn(TransferController.class).get(principal)).withSelfRel(),
				linkTo(methodOn(TransferController.class).getFiltered((Date) null, (Date) null, null, principal))
						.withRel("list-after-date-transfers"));
	}

	public static double round(double value, final int places) {
		if (places < 0) {
			throw new IllegalArgumentException();
		}

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

}
