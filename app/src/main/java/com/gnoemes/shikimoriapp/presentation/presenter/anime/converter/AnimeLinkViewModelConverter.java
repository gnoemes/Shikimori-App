package com.gnoemes.shikimoriapp.presentation.presenter.anime.converter;

import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeLink;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeLinkViewModel;

import java.util.List;

import io.reactivex.functions.Function;

public interface AnimeLinkViewModelConverter extends Function<List<AnimeLink>, List<AnimeLinkViewModel>> {
}
