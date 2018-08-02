package com.gnoemes.shikimoriapp.domain.history;

import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import javax.inject.Inject;

public class HistorySortConverterImpl implements HistorySortConverter {

    @Inject
    public HistorySortConverterImpl() {
    }

    @Override
    public List<Anime> sortAnimeByIds(List<Anime> animes, LinkedHashSet<Long> ids) {
        List<Anime> list = new ArrayList<>();

        for (Long id : ids) {
            for (Anime anime : animes) {
                if (anime.getId() == id) {
                    list.add(anime);
                }
            }
        }

        return list;
    }
}
