package com.gnoemes.shikimoriapp.data.repository.manga.converter;

import com.gnoemes.shikimoriapp.data.repository.app.converter.ImageResponseConverter;
import com.gnoemes.shikimoriapp.entity.manga.data.MangaResponse;
import com.gnoemes.shikimoriapp.entity.manga.domain.Manga;
import com.gnoemes.shikimoriapp.utils.Utils;

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
                response.getNameRu(),
                imageResponseConverter.convert(response.getImage()),
                Utils.appendHostIfNeed(response.getUrl()),
                response.getType(),
                response.getStatus(),
                response.getVolumes(),
                response.getChapters(),
                response.getDateAired(),
                response.getDateReleased()
        );
    }
}
