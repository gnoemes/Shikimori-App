package com.gnoemes.shikimoriapp.entity.user.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavoritesResponse {

    @SerializedName("animes")
    private List<FavoriteResponse> animeFavoriteRespons;

    @SerializedName("mangas")
    private List<FavoriteResponse> mangaFavouritesResponses;

    @SerializedName("characters")
    private List<FavoriteResponse> characterFavoriteRespons;

    @SerializedName("people")
    private List<FavoriteResponse> peopleFavoriteRespons;

    @SerializedName("mangakas")
    private List<FavoriteResponse> mangakasFavoriteRespons;

    @SerializedName("seyu")
    private List<FavoriteResponse> seyuFavoriteRespons;

    @SerializedName("producers")
    private List<FavoriteResponse> producersFavoriteRespons;

    public List<FavoriteResponse> getAnimeFavouriteResponses() {
        return animeFavoriteRespons;
    }

    public List<FavoriteResponse> getMangaFavouritesResponses() {
        return mangaFavouritesResponses;
    }

    public List<FavoriteResponse> getCharacterFavouriteResponses() {
        return characterFavoriteRespons;
    }

    public List<FavoriteResponse> getPeopleFavouriteResponses() {
        return peopleFavoriteRespons;
    }

    public List<FavoriteResponse> getMangakasFavouriteResponses() {
        return mangakasFavoriteRespons;
    }

    public List<FavoriteResponse> getSeyuFavouriteResponses() {
        return seyuFavoriteRespons;
    }

    public List<FavoriteResponse> getProducersFavouriteResponses() {
        return producersFavoriteRespons;
    }
}
