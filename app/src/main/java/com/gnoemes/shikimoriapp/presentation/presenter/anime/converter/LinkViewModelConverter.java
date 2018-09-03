package com.gnoemes.shikimoriapp.presentation.presenter.anime.converter;

import com.gnoemes.shikimoriapp.entity.anime.domain.Link;
import com.gnoemes.shikimoriapp.entity.anime.presentation.LinkViewModel;

import java.util.List;

import io.reactivex.functions.Function;

public interface LinkViewModelConverter extends Function<List<Link>, List<LinkViewModel>> {
}
