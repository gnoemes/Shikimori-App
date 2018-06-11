package com.gnoemes.shikimoriapp.entity.related.presentation;

import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeViewModel;

public class RelatedAnimeItem extends BaseRelatedItem {

    private String relation;
    private String relationRussian;
    private AnimeViewModel anime;

    public RelatedAnimeItem(String relation,
                            String relationRussian,
                            AnimeViewModel anime) {
        this.relation = relation;
        this.relationRussian = relationRussian;
        this.anime = anime;
    }

    public String getRelation() {
        return relation;
    }

    public String getRelationRussian() {
        return relationRussian;
    }

    public AnimeViewModel getAnime() {
        return anime;
    }
}
