package com.gnoemes.shikimoriapp.entity.related.presentation;

import com.gnoemes.shikimoriapp.entity.manga.domain.Manga;

public class RelatedMangaItem extends BaseRelatedItem {

    private String relation;
    private String relationRussian;
    //TODO view model
    private Manga manga;

    public RelatedMangaItem(String relation, String relationRussian, Manga manga) {
        this.relation = relation;
        this.relationRussian = relationRussian;
        this.manga = manga;
    }

    public String getRelation() {
        return relation;
    }

    public String getRelationRussian() {
        return relationRussian;
    }

    public Manga getManga() {
        return manga;
    }
}
