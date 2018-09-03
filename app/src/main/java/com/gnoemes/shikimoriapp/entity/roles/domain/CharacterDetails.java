package com.gnoemes.shikimoriapp.entity.roles.domain;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.entity.common.domain.Image;
import com.gnoemes.shikimoriapp.entity.manga.domain.Manga;

import java.util.List;

public class CharacterDetails {

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
    private List<Person> seyus;
    private List<Anime> animes;
    private List<Manga> mangas;

    public CharacterDetails(long id, String name, String russianName,
                            Image image, String url, String alternativeName,
                            @Nullable String japaneseName, @Nullable String description,
                            @Nullable String descriptionSource, List<Person> seyus,
                            List<Anime> animes, List<Manga> mangas) {
        this.id = id;
        this.name = name;
        this.russianName = russianName;
        this.image = image;
        this.url = url;
        this.alternativeName = alternativeName;
        this.japaneseName = japaneseName;
        this.description = description;
        this.descriptionSource = descriptionSource;
        this.seyus = seyus;
        this.animes = animes;
        this.mangas = mangas;
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

    public List<Person> getSeyus() {
        return seyus;
    }

    public List<Anime> getAnimes() {
        return animes;
    }

    public List<Manga> getMangas() {
        return mangas;
    }
}
