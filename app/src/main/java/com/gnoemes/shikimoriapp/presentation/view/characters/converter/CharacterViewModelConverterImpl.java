package com.gnoemes.shikimoriapp.presentation.view.characters.converter;

import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeImage;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.DividerItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.manga.domain.Manga;
import com.gnoemes.shikimoriapp.entity.roles.domain.CharacterDetails;
import com.gnoemes.shikimoriapp.entity.roles.domain.CharacterRelatedType;
import com.gnoemes.shikimoriapp.entity.roles.domain.Seyu;
import com.gnoemes.shikimoriapp.entity.roles.presentation.CharacterHeadItem;
import com.gnoemes.shikimoriapp.entity.roles.presentation.CharacterRelatedItem;
import com.gnoemes.shikimoriapp.entity.roles.presentation.CharacterRelatedListItem;

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
            items.add(new DividerItem());
            items.add(convertSeyuRelatedItem(CharacterRelatedType.SEYU, characterDetails.getSeyus()));
        }

        if (characterDetails.getAnimes() != null && !characterDetails.getAnimes().isEmpty()) {
            items.add(new DividerItem());
            items.add(convertAnimeRelatedItem(CharacterRelatedType.ANIME, characterDetails.getAnimes()));
        }

        if (characterDetails.getMangas() != null && !characterDetails.getMangas().isEmpty()) {
            items.add(new DividerItem());
            items.add(convertMangaRelatedItem(CharacterRelatedType.MANGA, characterDetails.getMangas()));
            items.add(new DividerItem());
        }

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
        return new CharacterRelatedItem(manga.getId(),
                new AnimeImage(manga.getImage().getImageOriginalUrl(),
                        manga.getImage().getImagePreviewUrl(),
                        manga.getImage().getImageX96Url(),
                        manga.getImage().getImageX48Url()));
    }

    private BaseItem convertAnimeRelatedItem(CharacterRelatedType type, List<Anime> animes) {
        List<CharacterRelatedItem> items = new ArrayList<>();

        for (Anime anime : animes) {
            items.add(convertAnime(anime));
        }

        return new CharacterRelatedListItem(type, items);
    }

    private CharacterRelatedItem convertAnime(Anime anime) {
        return new CharacterRelatedItem(anime.getId(),
                anime.getAnimeImage());
    }

    private BaseItem convertSeyuRelatedItem(CharacterRelatedType type, List<Seyu> seyus) {
        List<CharacterRelatedItem> items = new ArrayList<>();

        for (Seyu seyu : seyus) {
            items.add(convertSeyu(seyu));
        }

        return new CharacterRelatedListItem(type, items);
    }

    private CharacterRelatedItem convertSeyu(Seyu seyu) {
        return new CharacterRelatedItem(seyu.getId(),
                seyu.getImageResponse());
    }

    private BaseItem convertHeadItem(CharacterDetails item) {
        return new CharacterHeadItem(item.getId(),
                item.getName(),
                item.getRussianName(),
                item.getAnimeImage(),
                item.getUrl(),
                item.getAlternativeName(),
                item.getJapaneseName(),
                item.getDescription(),
                item.getDescriptionSource());
    }
}
