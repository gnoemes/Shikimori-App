package com.gnoemes.shikimoriapp.data.repository.anime.converter;

import com.gnoemes.shikimoriapp.BuildConfig;
import com.gnoemes.shikimoriapp.entity.anime.data.FranchiseNodeResponse;
import com.gnoemes.shikimoriapp.entity.anime.data.FranchiseResponse;
import com.gnoemes.shikimoriapp.entity.anime.domain.FranchiseNode;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FranchiseResponseConverterImpl implements FranchiseResponseConverter {

    @Inject
    public FranchiseResponseConverterImpl() {
    }

    @Override
    public List<FranchiseNode> apply(FranchiseResponse franchiseResponse) {
        List<FranchiseNode> items = new ArrayList<>();

        for (FranchiseNodeResponse nodeResponse : franchiseResponse.getNodes()) {
            items.add(convertResponse(nodeResponse));
        }

        return items;
    }

    private FranchiseNode convertResponse(FranchiseNodeResponse nodeResponse) {
        return new FranchiseNode(nodeResponse.getId(),
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
