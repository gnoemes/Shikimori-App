package com.gnoemes.shikimoriapp.entity.user.domain;

import java.util.List;

public class Favorites {

    private List<Favorite> animes;
    private List<Favorite> mangas;
    private List<Favorite> characters;
    private List<Favorite> people;
    private List<Favorite> mangakas;
    private List<Favorite> seyu;
    private List<Favorite> producers;
    private List<Favorite> all;

    public Favorites(List<Favorite> animes, List<Favorite> mangas, List<Favorite> characters,
                     List<Favorite> people, List<Favorite> mangakas, List<Favorite> seyu,
                     List<Favorite> producers, List<Favorite> all) {
        this.animes = animes;
        this.mangas = mangas;
        this.characters = characters;
        this.people = people;
        this.mangakas = mangakas;
        this.seyu = seyu;
        this.producers = producers;
        this.all = all;
    }

    public List<Favorite> getAnimes() {
        return animes;
    }

    public List<Favorite> getMangas() {
        return mangas;
    }

    public List<Favorite> getCharacters() {
        return characters;
    }

    public List<Favorite> getPeople() {
        return people;
    }

    public List<Favorite> getMangakas() {
        return mangakas;
    }

    public List<Favorite> getSeyu() {
        return seyu;
    }

    public List<Favorite> getProducers() {
        return producers;
    }

    public List<Favorite> getAll() {
        return all;
    }
}
