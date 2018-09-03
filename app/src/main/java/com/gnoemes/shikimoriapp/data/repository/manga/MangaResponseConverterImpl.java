package com.gnoemes.shikimoriapp.data.repository.manga;

import com.gnoemes.shikimoriapp.data.repository.app.converter.ImageResponseConverter;
import com.gnoemes.shikimoriapp.entity.manga.data.MangaResponse;
import com.gnoemes.shikimoriapp.entity.manga.domain.Manga;
import com.gnoemes.shikimoriapp.entity.manga.domain.MangaStatus;
import com.gnoemes.shikimoriapp.entity.manga.domain.MangaType;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MangaResponseConverterImpl implements MangaResponseConverter {

    private ImageResponseConverter imageResponseConverter;

    @Inject
    public MangaResponseConverterImpl(ImageResponseConverter imageResponseConverter) {
        this.imageResponseConverter = imageResponseConverter;
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
                imageResponseConverter.convert(response.getImage()),
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
}
