package com.gnoemes.shikimoriapp.presentation.presenter.anime;

import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeDetails;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeDetailsViewModel;

import io.reactivex.functions.Function;

/**
 * Interface of converter from domain to view model
 */
public interface AnimeDetailsViewModelConverter extends Function<AnimeDetails, AnimeDetailsViewModel> {
}
