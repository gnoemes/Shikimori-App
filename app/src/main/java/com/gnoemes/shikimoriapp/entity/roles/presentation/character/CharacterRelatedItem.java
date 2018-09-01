package com.gnoemes.shikimoriapp.entity.roles.presentation.character;

import com.gnoemes.shikimoriapp.entity.common.domain.Image;

public class CharacterRelatedItem extends BaseCharacterItem {

    private long id;
    private Image image;

    public CharacterRelatedItem(long id, Image image) {
        this.id = id;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public Image getImage() {
        return image;
    }
}
