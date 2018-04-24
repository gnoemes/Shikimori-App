package com.gnoemes.shikimoriapp.entity.anime.domain;

public enum AnimeGenre {
    ACTION("action", 1),
    ADVENTURE("adventure", 2),
    CARS("cars", 3),
    COMEDY("comedy", 4),
    DEMENTIA("dementia", 5),
    DEMONS("demons", 6),
    MYSTERY("mystery", 7),
    DRAMA("drama", 8),
    ECCHI("ecchi", 9),
    FANTASY("fantasy", 10),
    GAME("game", 11),
    HENTAI("hentai", 12),
    HISTORICAL("historical", 13),
    HORROR("horror", 14),
    KIDS("kids", 15),
    MAGIC("magic", 16),
    MARTIAL_ARTS("martial_arts", 17),
    MECHA("mecha", 18),
    MUSIC("music", 19),
    PARODY("parody", 20),
    SAMURAI("samurai", 21),
    ROMANCE("romance", 22),
    SCHOOL("school", 23),
    SCI_FI("sci_fi", 24),
    SHOUJO("shoujo", 25),
    SHOUJO_AI("shoujo_ai", 26),
    SHOUNEN("shounen", 27),
    SHOUNEN_AI("shounen_ai", 28),
    SPACE("space", 29),
    SPORTS("sports", 30),
    SUPER_POWER("super_power", 31),
    VAMPIRE("vampire", 32),
    YAOI("yaoi", 33),
    YURI("yuri", 34),
    HAREM("harem", 35),
    SLICE_OF_LIFE("slice_of_life", 36),
    SUPERNATURAL("supernatural", 37),
    MILITATY("military", 38),
    POLICE("police", 39),
    PSYCHOLOGICAL("psychological", 40),
    THRILLER("thriller", 41),
    SEINEN("seinen", 42),
    JOSEI("josei", 43);

    private final String name;
    private final int id;

    AnimeGenre(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    @Override
    public String toString() {
        return name;
    }

    public String getId() {
        return String.valueOf(id);
    }
}
