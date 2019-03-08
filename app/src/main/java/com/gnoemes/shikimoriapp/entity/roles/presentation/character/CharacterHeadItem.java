package com.gnoemes.shikimoriapp.entity.roles.presentation.character;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.common.domain.Image;

public class CharacterHeadItem extends BaseCharacterItem {

    private long id;
    private String name;
    private String russianName;
    private Image image;
    private String url;
    private String alternativeName;
    @Nullable
    private String japaneseName;
    @Nullable
    private String description;
    @Nullable
    private String descriptionSource;

    public CharacterHeadItem(long id, String name, String russianName,
                             Image image, String url, String alternativeName,
                             @Nullable String japaneseName, @Nullable String description,
                             @Nullable String descriptionSource) {
        this.id = id;
        this.name = name;
        this.russianName = russianName;
        this.image = image;
        this.url = url;
        this.alternativeName = alternativeName;
        this.japaneseName = japaneseName;
        this.description = description;
        this.descriptionSource = descriptionSource;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRussianName() {
        return russianName;
    }

    public Image getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public String getAlternativeName() {
        return alternativeName;
    }

    @Nullable
    public String getJapaneseName() {
        return japaneseName;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @Nullable
    public String getDescriptionSource() {
        return descriptionSource;
    }
}
