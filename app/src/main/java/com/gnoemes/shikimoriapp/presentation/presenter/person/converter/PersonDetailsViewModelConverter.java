package com.gnoemes.shikimoriapp.presentation.presenter.person.converter;

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.roles.domain.PersonDetails;

import java.util.List;

import io.reactivex.functions.Function;

public interface PersonDetailsViewModelConverter extends Function<PersonDetails, List<BaseItem>> {
}
