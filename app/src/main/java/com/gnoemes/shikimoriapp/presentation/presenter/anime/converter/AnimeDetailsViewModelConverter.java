package com.gnoemes.shikimoriapp.presentation.presenter.anime.converter;

import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeDetails;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeDetailsViewModel;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;

import java.util.List;

import io.reactivex.functions.Function;

/**
 * Interface of converter from domain to view model
 */
public interface AnimeDetailsViewModelConverter extends Function<AnimeDetails, AnimeDetailsViewModel> {

    List<BaseItem> convertFromViewModel(AnimeDetailsViewModel viewModel);
}
