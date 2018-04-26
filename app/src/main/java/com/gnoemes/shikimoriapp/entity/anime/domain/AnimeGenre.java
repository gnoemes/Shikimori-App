package com.gnoemes.shikimoriapp.entity.anime.domain;

public enum AnimeGenre {
    ACTION("action", "Экшен", 1),
    ADVENTURE("adventure", "Приключения", 2),
    CARS("cars", "Машины", 3),
    COMEDY("comedy", "Комедия", 4),
    DEMENTIA("dementia", "Безумие", 5),
    DEMONS("demons", "Демоны", 6),
    MYSTERY("mystery", "Мистика", 7),
    DRAMA("drama", "Драма", 8),
    ECCHI("ecchi", "Этти", 9),
    FANTASY("fantasy", "Фэнтези", 10),
    GAME("game", "Игры", 11),
    HENTAI("hentai", "Хентай", 12),
    HISTORICAL("historical", "Исторический", 13),
    HORROR("horror", "Ужасы", 14),
    KIDS("kids", "Детское", 15),
    MAGIC("magic", "Магия", 16),
    MARTIAL_ARTS("martial_arts", "Боевые исскуства", 17),
    MECHA("mecha", "Меха", 18),
    MUSIC("music", "Музыка", 19),
    PARODY("parody", "Пародия", 20),
    SAMURAI("samurai", "Самураи", 21),
    ROMANCE("romance", "Романтика", 22),
    SCHOOL("school", "Школа", 23),
    SCI_FI("sci_fi", "Фантастика", 24),
    SHOUJO("shoujo", "Сёдзе", 25),
    SHOUJO_AI("shoujo_ai", "Сёдзе Ай", 26),
    SHOUNEN("shounen", "Сёнен", 27),
    SHOUNEN_AI("shounen_ai", "Сёнен Ай", 28),
    SPACE("space", "Космос", 29),
    SPORTS("sports", "Спорт", 30),
    SUPER_POWER("super_power", "Супер силы", 31),
    VAMPIRE("vampire", "Вампиры", 32),
    YAOI("yaoi", "Яойчик", 33),
    YURI("yuri", "Юри", 34),
    HAREM("harem", "Гарем", 35),
    SLICE_OF_LIFE("slice_of_life", "Повседневность", 36),
    SUPERNATURAL("supernatural", "Сверхъестественное", 37),
    MILITATY("military", "Военное", 38),
    POLICE("police", "Полиция", 39),
    PSYCHOLOGICAL("psychological", "Психологический", 40),
    THRILLER("thriller", "Триллер", 41),
    SEINEN("seinen", "Сейнен", 42),
    JOSEI("josei", "Дзёсей", 43);

    private final String name;
    private final String russianName;
    private final int id;

    AnimeGenre(String name, String russianName, int id) {
        this.name = name;
        this.russianName = russianName;
        this.id = id;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public boolean equalsRussianName(String otherName) {
        return russianName.equals(otherName);
    }

    @Override
    public String toString() {
        return name;
    }

    public String getId() {
        return String.valueOf(id);
    }

    public String getRussianName() {
        return russianName;
    }
}
