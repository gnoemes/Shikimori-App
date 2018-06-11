package com.gnoemes.shikimoriapp.presentation.presenter.related;

import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeViewModel;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.DividerItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.related.domain.Related;
import com.gnoemes.shikimoriapp.entity.related.presentation.RelatedAnimeItem;
import com.gnoemes.shikimoriapp.entity.related.presentation.RelatedMangaItem;
import com.gnoemes.shikimoriapp.presentation.presenter.search.converter.AnimeViewModelConverter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RelatedViewModelConverterImpl implements RelatedViewModelConverter {

    private AnimeViewModelConverter converter;

    @Inject
    public RelatedViewModelConverterImpl(AnimeViewModelConverter converter) {
        this.converter = converter;
    }

    @Override
    public List<BaseItem> apply(List<Related> relateds) {
        List<BaseItem> items = new ArrayList<>();

        for (Related related : relateds) {
            items.add(convert(related));
            items.add(new DividerItem());
        }

        return items;
    }

    private BaseItem convert(Related related) {
        switch (related.getType()) {
            case MANGA:
                return convertManga(related);
            case ANIME:
                return convertAnime(related);
        }
        return null;
    }

    private BaseItem convertManga(Related related) {
        return new RelatedMangaItem(related.getRelation(),
                related.getRelationRussian(),
                related.getManga());
    }

    private BaseItem convertAnime(Related related) {
        return new RelatedAnimeItem(related.getRelation(),
                related.getRelationRussian(),
                (AnimeViewModel) converter.convertAnime(related.getAnime()));
    }
}
