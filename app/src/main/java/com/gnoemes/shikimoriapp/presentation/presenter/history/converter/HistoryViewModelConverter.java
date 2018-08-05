package com.gnoemes.shikimoriapp.presentation.presenter.history.converter;

import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;

import java.util.List;

public interface HistoryViewModelConverter {

    List<BaseItem> convertFrom(List<Anime> animes);
}
