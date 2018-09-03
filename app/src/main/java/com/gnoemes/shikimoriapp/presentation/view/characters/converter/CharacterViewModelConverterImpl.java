package com.gnoemes.shikimoriapp.presentation.view.characters.converter;

import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.BottomDividerItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.DoubleDividerItem;
import com.gnoemes.shikimoriapp.entity.manga.domain.Manga;
import com.gnoemes.shikimoriapp.entity.roles.domain.CharacterDetails;
import com.gnoemes.shikimoriapp.entity.roles.domain.CharacterRelatedType;
import com.gnoemes.shikimoriapp.entity.roles.domain.Person;
import com.gnoemes.shikimoriapp.entity.roles.presentation.character.CharacterHeadItem;
import com.gnoemes.shikimoriapp.entity.roles.presentation.character.CharacterRelatedItem;
import com.gnoemes.shikimoriapp.entity.roles.presentation.character.CharacterRelatedListItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CharacterViewModelConverterImpl implements CharacterViewModelConverter {

    @Inject
    public CharacterViewModelConverterImpl() {
    }

    @Override
    public List<BaseItem> apply(CharacterDetails characterDetails) {
        List<BaseItem> items = new ArrayList<>();

        items.add(convertHeadItem(characterDetails));

        if (characterDetails.getSeyus() != null && !characterDetails.getSeyus().isEmpty()) {
            items.add(new DoubleDividerItem());
            items.add(convertSeyuRelatedItem(CharacterRelatedType.SEYU, characterDetails.getSeyus()));
        }

        if (characterDetails.getAnimes() != null && !characterDetails.getAnimes().isEmpty()) {
            items.add(new DoubleDividerItem());
            items.add(convertAnimeRelatedItem(CharacterRelatedType.ANIME, characterDetails.getAnimes()));
        }

        if (characterDetails.getMangas() != null && !characterDetails.getMangas().isEmpty()) {
            items.add(new DoubleDividerItem());
            items.add(convertMangaRelatedItem(CharacterRelatedType.MANGA, characterDetails.getMangas()));
        }

        items.add(new BottomDividerItem());

        return items;
    }

    private BaseItem convertMangaRelatedItem(CharacterRelatedType type, List<Manga> mangas) {
        List<CharacterRelatedItem> items = new ArrayList<>();

        for (Manga manga : mangas) {
            items.add(convertManga(manga));
        }

        return new CharacterRelatedListItem(type, items);
    }

    private CharacterRelatedItem convertManga(Manga manga) {
        return new CharacterRelatedItem(manga.getId(), manga.getImage());
    }

    private BaseItem convertAnimeRelatedItem(CharacterRelatedType type, List<Anime> animes) {
        List<CharacterRelatedItem> items = new ArrayList<>();

        for (Anime anime : animes) {
            items.add(convertAnime(anime));
        }

        return new CharacterRelatedListItem(type, items);
    }

    private CharacterRelatedItem convertAnime(Anime anime) {
        return new CharacterRelatedItem(anime.getId(), anime.getImage());
    }

    private BaseItem convertSeyuRelatedItem(CharacterRelatedType type, List<Person> seyus) {
        List<CharacterRelatedItem> items = new ArrayList<>();

        for (Person seyu : seyus) {
            items.add(convertSeyu(seyu));
        }

        return new CharacterRelatedListItem(type, items);
    }

    private CharacterRelatedItem convertSeyu(Person seyu) {
        return new CharacterRelatedItem(seyu.getId(),
                seyu.getImage());
    }

    private BaseItem convertHeadItem(CharacterDetails item) {
        return new CharacterHeadItem(item.getId(),
                item.getName(),
                item.getRussianName(),
                item.getImage(),
                item.getUrl(),
                item.getAlternativeName(),
                item.getJapaneseName(),
                item.getDescription(),
                item.getDescriptionSource());
    }
}
