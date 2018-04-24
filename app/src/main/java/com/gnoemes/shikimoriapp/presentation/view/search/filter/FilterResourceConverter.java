package com.gnoemes.shikimoriapp.presentation.view.search.filter;

import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem;

import java.util.List;

public interface FilterResourceConverter {

    List<FilterItem> convertFrom(List<String> actions, List<String> names, Enum[] type);
}
