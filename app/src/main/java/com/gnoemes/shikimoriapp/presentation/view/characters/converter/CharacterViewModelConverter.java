package com.gnoemes.shikimoriapp.presentation.view.characters.converter;

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.roles.domain.CharacterDetails;

import java.util.List;

import io.reactivex.functions.Function;

public interface CharacterViewModelConverter extends Function<CharacterDetails, List<BaseItem>> {
}
