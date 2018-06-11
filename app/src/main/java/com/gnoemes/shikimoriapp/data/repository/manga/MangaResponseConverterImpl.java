package com.gnoemes.shikimoriapp.data.repository.manga;

import com.gnoemes.shikimoriapp.BuildConfig;
import com.gnoemes.shikimoriapp.entity.manga.data.MangaImageResponse;
import com.gnoemes.shikimoriapp.entity.manga.data.MangaResponse;
import com.gnoemes.shikimoriapp.entity.manga.domain.Manga;
import com.gnoemes.shikimoriapp.entity.manga.domain.MangaImage;
import com.gnoemes.shikimoriapp.entity.manga.domain.MangaStatus;
import com.gnoemes.shikimoriapp.entity.manga.domain.MangaType;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MangaResponseConverterImpl implements MangaResponseConverter {

    @Inject
    public MangaResponseConverterImpl() {
    }

    @Override
    public List<Manga> apply(List<MangaResponse> mangaResponses) {
        List<Manga> items = new ArrayList<>();

        for (MangaResponse response : mangaResponses) {
            items.add(convertResponse(response));
        }

        return items;
    }

    @Override
    public Manga convertResponse(MangaResponse response) {

        if (response == null) {
            return null;
        }

        return new Manga(response.getId(),
                response.getName(),
                response.getRussianName(),
                convertMangaImage(response.getImage()),
                response.getUrl(),
                convertType(response.getType()),
                convertStatus(response.getStatus()),
                response.getVolume(),
                response.getChapters(),
                response.getAiredDate(),
                response.getReleasedDate()
        );
    }

    private MangaStatus convertStatus(String status) {
        for (MangaStatus mangaStatus : MangaStatus.values()) {
            if (mangaStatus.equalsStatus(status)) {
                return mangaStatus;
            }
        }
        throw new IllegalArgumentException(status + " is not valid status");
    }

    private MangaType convertType(String type) {
        for (MangaType mangaType : MangaType.values()) {
            if (mangaType.equalsType(type)) {
                return mangaType;
            }
        }
        throw new IllegalArgumentException(type + " is not valid status");
    }


    @Override
    public MangaImage convertMangaImage(MangaImageResponse image) {
        return new MangaImage(
                buildUrl(image.getImageOriginalUrl()),
                buildUrl(image.getImagePreviewUrl()),
                buildUrl(image.getImageX96Url()),
                buildUrl(image.getImageX48Url()));
    }

    private String buildUrl(String url) {
        if (url.contains("http")) {
            return url;
        }
        return BuildConfig.ShikimoriBaseUrl.concat(url);
    }
}
