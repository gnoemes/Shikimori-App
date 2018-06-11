package com.gnoemes.shikimoriapp.presentation.presenter.related;

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.related.domain.Related;

import java.util.List;

import io.reactivex.functions.Function;

public interface RelatedViewModelConverter extends Function<List<Related>, List<BaseItem>> {

}
