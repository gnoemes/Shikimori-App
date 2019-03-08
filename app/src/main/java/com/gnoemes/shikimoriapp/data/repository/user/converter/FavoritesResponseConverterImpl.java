package com.gnoemes.shikimoriapp.data.repository.user.converter;

import com.gnoemes.shikimoriapp.entity.app.domain.FavoriteType;
import com.gnoemes.shikimoriapp.entity.user.data.FavoriteResponse;
import com.gnoemes.shikimoriapp.entity.user.data.FavoritesResponse;
import com.gnoemes.shikimoriapp.entity.user.domain.Favorite;
import com.gnoemes.shikimoriapp.entity.user.domain.Favorites;
import com.gnoemes.shikimoriapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FavoritesResponseConverterImpl implements FavoritesResponseConverter {

    @Inject
    public FavoritesResponseConverterImpl() {
    }

    @Override
    public Favorites apply(FavoritesResponse favoritesResponse) {
        List<Favorite> animes = new ArrayList<>();
        List<Favorite> mangas = new ArrayList<>();
        List<Favorite> characters = new ArrayList<>();
        List<Favorite> people = new ArrayList<>();
        List<Favorite> mangakas = new ArrayList<>();
        List<Favorite> seyu = new ArrayList<>();
        List<Favorite> producers = new ArrayList<>();
        List<Favorite> all = new ArrayList<>();

        for (FavoriteResponse response : favoritesResponse.getAnimeFavouriteResponses()) {
            Favorite favorite = convertFavorite(response, FavoriteType.ANIME);
            animes.add(favorite);
            all.add(favorite);
        }

        for (FavoriteResponse response : favoritesResponse.getMangaFavouritesResponses()) {
            Favorite favorite = convertFavorite(response, FavoriteType.MANGA);
            mangas.add(favorite);
            all.add(favorite);
        }

        for (FavoriteResponse response : favoritesResponse.getCharacterFavouriteResponses()) {
            Favorite favorite = convertFavorite(response, FavoriteType.CHARACTERS);
            characters.add(favorite);
            all.add(favorite);
        }

        for (FavoriteResponse response : favoritesResponse.getPeopleFavouriteResponses()) {
            Favorite favorite = convertFavorite(response, FavoriteType.PEOPLE);
            people.add(favorite);
            all.add(favorite);
        }

        for (FavoriteResponse response : favoritesResponse.getMangakasFavouriteResponses()) {
            Favorite favorite = convertFavorite(response, FavoriteType.MANGAKAS);
            mangakas.add(favorite);
            all.add(favorite);
        }

        for (FavoriteResponse response : favoritesResponse.getSeyuFavouriteResponses()) {
            Favorite favorite = convertFavorite(response, FavoriteType.SEYU);
            seyu.add(favorite);
            all.add(favorite);
        }

        for (FavoriteResponse response : favoritesResponse.getProducersFavouriteResponses()) {
            Favorite favorite = convertFavorite(response, FavoriteType.PRODUCERS);
            producers.add(favorite);
            all.add(favorite);
        }

        return new Favorites(animes, mangas, characters, people, mangakas, seyu, producers, all);
    }

    private Favorite convertFavorite(FavoriteResponse response, FavoriteType type) {
        return new Favorite(response.getId(),
                type,
                response.getName(),
                response.getRussianName(),
                Utils.appendHostIfNeed(response.getImageUrl()).replace("x64", "original"),
                response.getUrl());
    }

}
