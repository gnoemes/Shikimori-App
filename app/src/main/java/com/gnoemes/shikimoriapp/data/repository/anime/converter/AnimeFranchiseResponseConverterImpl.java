package com.gnoemes.shikimoriapp.data.repository.anime.converter;

import com.gnoemes.shikimoriapp.BuildConfig;
import com.gnoemes.shikimoriapp.entity.anime.data.AnimeFranchiseNodeResponse;
import com.gnoemes.shikimoriapp.entity.anime.data.AnimeFranchiseResponse;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeFranchiseNode;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AnimeFranchiseResponseConverterImpl implements AnimeFranchiseResponseConverter {

    @Inject
    public AnimeFranchiseResponseConverterImpl() {
    }

    @Override
    public List<AnimeFranchiseNode> apply(AnimeFranchiseResponse animeFranchiseResponse) {
        List<AnimeFranchiseNode> items = new ArrayList<>();

        for (AnimeFranchiseNodeResponse nodeResponse : animeFranchiseResponse.getNodes()) {
            items.add(convertResponse(nodeResponse));
        }

        return items;
    }

    private AnimeFranchiseNode convertResponse(AnimeFranchiseNodeResponse nodeResponse) {
        return new AnimeFranchiseNode(nodeResponse.getId(),
                new DateTime(nodeResponse.getDateTime()),
                nodeResponse.getName(),
                buildUrl(nodeResponse.getImageUrl()),
                nodeResponse.getUrl(),
                nodeResponse.getYear(),
                nodeResponse.getType());
    }

    private String buildUrl(String url) {
        if (url.contains("http")) {
            return url;
        }
        return BuildConfig.ShikimoriBaseUrl.concat(url);
    }
}
