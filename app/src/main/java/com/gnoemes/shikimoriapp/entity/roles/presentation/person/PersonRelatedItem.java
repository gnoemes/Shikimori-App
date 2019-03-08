package com.gnoemes.shikimoriapp.entity.roles.presentation.person;

import com.gnoemes.shikimoriapp.entity.common.domain.Image;

public class PersonRelatedItem {

    private long id;
    private Image image;
    private String text;
    private PersonRelatedItemType type;

    public PersonRelatedItem(long id, Image image, String text, PersonRelatedItemType type) {
        this.id = id;
        this.image = image;
        this.text = text;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public Image getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public PersonRelatedItemType getType() {
        return type;
    }
}
