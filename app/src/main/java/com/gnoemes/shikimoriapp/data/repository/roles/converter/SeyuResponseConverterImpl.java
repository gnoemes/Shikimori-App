package com.gnoemes.shikimoriapp.data.repository.roles.converter;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeImageResponse;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeImage;
import com.gnoemes.shikimoriapp.entity.roles.data.SeyuResponse;
import com.gnoemes.shikimoriapp.entity.roles.domain.Seyu;
import com.gnoemes.shikimoriapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SeyuResponseConverterImpl implements SeyuResponseConverter {

    @Inject
    public SeyuResponseConverterImpl() {
    }

    @Override
    public List<Seyu> apply(List<SeyuResponse> seyuResponses) {
        List<Seyu> items = new ArrayList<>();

        for (SeyuResponse response : seyuResponses) {
            items.add(convertResponse(response));
        }

        return items;
    }

    private Seyu convertResponse(SeyuResponse response) {
        return new Seyu(response.getId(),
                response.getName(),
                response.getRussianName(),
                convertImage(response.getImageResponse()),
                Utils.appendHostIfNeed(response.getUrl()));
    }

    @Override
    public AnimeImage convertImage(AnimeImageResponse imageResponse) {
        return new AnimeImage(
                Utils.appendHostIfNeed(imageResponse.getImageOriginalUrl()),
                Utils.appendHostIfNeed(imageResponse.getImagePreviewUrl()),
                Utils.appendHostIfNeed(imageResponse.getImageX96Url()),
                Utils.appendHostIfNeed(imageResponse.getImageX48Url()));
    }
}
