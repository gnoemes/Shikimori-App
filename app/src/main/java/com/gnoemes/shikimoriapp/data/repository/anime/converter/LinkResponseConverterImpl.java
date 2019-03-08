package com.gnoemes.shikimoriapp.data.repository.anime.converter;

import com.gnoemes.shikimoriapp.entity.anime.data.LinkResponse;
import com.gnoemes.shikimoriapp.entity.anime.domain.Link;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class LinkResponseConverterImpl implements LinkResponseConverter {

    @Inject
    public LinkResponseConverterImpl() {
    }

    @Override
    public List<Link> apply(List<LinkResponse> linkRespons) {
        List<Link> links = new ArrayList<>();

        for (LinkResponse response : linkRespons) {
            links.add(convertResponse(response));
        }

        return links;
    }

    private Link convertResponse(LinkResponse response) {
        return new Link(response.getId(), response.getName(), response.getUrl());
    }
}
