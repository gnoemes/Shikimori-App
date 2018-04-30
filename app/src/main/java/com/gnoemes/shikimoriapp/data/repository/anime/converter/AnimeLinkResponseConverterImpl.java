package com.gnoemes.shikimoriapp.data.repository.anime.converter;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeLinkResponse;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeLink;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AnimeLinkResponseConverterImpl implements AnimeLinkResponseConverter {

    @Inject
    public AnimeLinkResponseConverterImpl() {
    }

    @Override
    public List<AnimeLink> apply(List<AnimeLinkResponse> animeLinkResponses) {
        List<AnimeLink> links = new ArrayList<>();

        for (AnimeLinkResponse response : animeLinkResponses) {
            links.add(convertResponse(response));
        }

        return links;
    }

    private AnimeLink convertResponse(AnimeLinkResponse response) {
        return new AnimeLink(response.getId(), response.getName(), response.getUrl());
    }
}
