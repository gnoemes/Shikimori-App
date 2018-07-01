package com.gnoemes.shikimoriapp.entity.roles.data;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeImageResponse;
import com.gnoemes.shikimoriapp.entity.anime.data.AnimeResponse;
import com.gnoemes.shikimoriapp.entity.manga.data.MangaResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CharacterDetailsResponse {
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("russian")
    private String russianName;
    @SerializedName("image")
    private AnimeImageResponse animeImage;
    @SerializedName("url")
    private String url;
    @Nullable
    @SerializedName("altname")
    private String alternativeName;
    @Nullable
    @SerializedName("japanese")
    private String japaneseName;
    @Nullable
    @SerializedName("description")
    private String description;
    @Nullable
    @SerializedName("description_source")
    private String descriptionSource;
    @SerializedName("seyu")
    private List<SeyuResponse> seyuResponses;
    @SerializedName("animes")
    private List<AnimeResponse> animeResponses;
    @SerializedName("mangas")
    private List<MangaResponse> mangaResponses;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRussianName() {
        return russianName;
    }

    public AnimeImageResponse getAnimeImage() {
        return animeImage;
    }

    public String getUrl() {
        return url;
    }

    @Nullable
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

    public List<SeyuResponse> getSeyuResponses() {
        return seyuResponses;
    }

    public List<AnimeResponse> getAnimeResponses() {
        return animeResponses;
    }

    public List<MangaResponse> getMangaResponses() {
        return mangaResponses;
    }
}
