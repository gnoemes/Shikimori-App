package com.gnoemes.shikimoriapp.entity.roles.presentation.character;

import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeImage;

public class CharacterRelatedItem extends BaseCharacterItem {

    private long id;
    private AnimeImage image;

    public CharacterRelatedItem(long id, AnimeImage image) {
        this.id = id;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public AnimeImage getImage() {
        return image;
    }
}
