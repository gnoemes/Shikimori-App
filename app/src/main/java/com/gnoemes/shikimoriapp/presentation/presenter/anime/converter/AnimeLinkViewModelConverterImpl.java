package com.gnoemes.shikimoriapp.presentation.presenter.anime.converter;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeLink;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeLinkViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AnimeLinkViewModelConverterImpl implements AnimeLinkViewModelConverter {

    @Inject
    public AnimeLinkViewModelConverterImpl() {
    }

    @Override
    public List<AnimeLinkViewModel> apply(List<AnimeLink> animeLinks) {
        List<AnimeLinkViewModel> viewModels = new ArrayList<>();

        for (AnimeLink link : animeLinks) {
            viewModels.add(new AnimeLinkViewModel(convertName(link.getName()), link.getUrl()));
        }

        return viewModels;
    }

    private String convertName(@Nullable String rawName) {
        if (rawName == null) {
            return null;
        }
        String name = rawName.replaceAll("_", " ");
        return name.substring(0, 1).toUpperCase().concat(name.substring(1));
    }
}
