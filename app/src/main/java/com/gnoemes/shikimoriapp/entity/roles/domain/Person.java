package com.gnoemes.shikimoriapp.entity.roles.domain;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.app.domain.LinkedContent;
import com.gnoemes.shikimoriapp.entity.app.domain.LinkedType;
import com.gnoemes.shikimoriapp.entity.common.domain.Image;

public class Person extends LinkedContent {

    private long id;
    private String name;
    @Nullable
    private String russianName;
    private Image image;
    private String url;

    public Person(long id, String name, @Nullable String russianName, Image image, String url) {
        super(id, name, LinkedType.PERSON, image.getOriginal());
        this.id = id;
        this.name = name;
        this.russianName = russianName;
        this.image = image;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public String getRussianName() {
        return russianName;
    }

    public Image getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }
}
