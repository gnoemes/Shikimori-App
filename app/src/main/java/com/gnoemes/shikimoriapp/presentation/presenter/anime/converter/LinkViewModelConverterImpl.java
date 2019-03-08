package com.gnoemes.shikimoriapp.presentation.presenter.anime.converter;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.domain.Link;
import com.gnoemes.shikimoriapp.entity.anime.presentation.LinkViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class LinkViewModelConverterImpl implements LinkViewModelConverter {

    @Inject
    public LinkViewModelConverterImpl() {
    }

    @Override
    public List<LinkViewModel> apply(List<Link> links) {
        List<LinkViewModel> viewModels = new ArrayList<>();

        for (Link link : links) {
            viewModels.add(new LinkViewModel(convertName(link.getName()), link.getUrl()));
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
