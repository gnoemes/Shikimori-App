package com.gnoemes.shikimoriapp.entity.roles.presentation.person;

import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeImage;

public class PersonRelatedItem {

    private long id;
    private AnimeImage image;
    private String text;
    private PersonRelatedItemType type;

    public PersonRelatedItem(long id, AnimeImage image, String text, PersonRelatedItemType type) {
        this.id = id;
        this.image = image;
        this.text = text;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public AnimeImage getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public PersonRelatedItemType getType() {
        return type;
    }
}
