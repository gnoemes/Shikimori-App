package com.gnoemes.shikimoriapp.entity.user.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavouritesResponse {

    @SerializedName("animes")
    private List<FavouriteResponse> animeFavouriteResponses;

    @SerializedName("mangas")
    private List<FavouriteResponse> mangaFavouritesResponses;

    @SerializedName("characters")
    private List<FavouriteResponse> characterFavouriteResponses;

    @SerializedName("people")
    private List<FavouriteResponse> peopleFavouriteResponses;

    @SerializedName("mangakas")
    private List<FavouriteResponse> mangakasFavouriteResponses;

    @SerializedName("seyu")
    private List<FavouriteResponse> seyuFavouriteResponses;

    @SerializedName("producers")
    private List<FavouriteResponse> producersFavouriteResponses;

    public List<FavouriteResponse> getAnimeFavouriteResponses() {
        return animeFavouriteResponses;
    }

    public List<FavouriteResponse> getMangaFavouritesResponses() {
        return mangaFavouritesResponses;
    }

    public List<FavouriteResponse> getCharacterFavouriteResponses() {
        return characterFavouriteResponses;
    }

    public List<FavouriteResponse> getPeopleFavouriteResponses() {
        return peopleFavouriteResponses;
    }

    public List<FavouriteResponse> getMangakasFavouriteResponses() {
        return mangakasFavouriteResponses;
    }

    public List<FavouriteResponse> getSeyuFavouriteResponses() {
        return seyuFavouriteResponses;
    }

    public List<FavouriteResponse> getProducersFavouriteResponses() {
        return producersFavouriteResponses;
    }
}
