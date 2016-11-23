package com.github.hateoas.forms.samples.bean;

import java.util.ArrayList;
import java.util.List;

import com.github.hateoas.forms.action.Options;
import com.github.hateoas.forms.affordance.SimpleSuggest;
import com.github.hateoas.forms.affordance.Suggest;
import com.github.hateoas.forms.affordance.SuggestObjectWrapper;

public class TransferOptionsSuggest implements Options<SuggestObjectWrapper> {

	@Override
	public List<Suggest<SuggestObjectWrapper>> get(final String[] value, final Object... args) {
		List<Suggest<SuggestObjectWrapper>> data = new ArrayList<>();
		for (int i = 0; i < TransferOptions.values().length; i++) {
			final TransferOptions option = TransferOptions.values()[i];
			data.add((Suggest) new SimpleSuggest<SuggestObjectWrapper>(option.toString().toLowerCase(), option.toString()));
		}
		return data;
	}

}
