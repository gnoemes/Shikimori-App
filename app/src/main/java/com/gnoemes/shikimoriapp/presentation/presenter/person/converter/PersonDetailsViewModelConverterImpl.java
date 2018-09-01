package com.gnoemes.shikimoriapp.presentation.presenter.person.converter;

import com.gnoemes.shikimoriapp.entity.anime.domain.Anime;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.BottomDividerItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.DoubleDividerItem;
import com.gnoemes.shikimoriapp.entity.common.domain.Image;
import com.gnoemes.shikimoriapp.entity.manga.domain.Manga;
import com.gnoemes.shikimoriapp.entity.manga.domain.MangaImage;
import com.gnoemes.shikimoriapp.entity.roles.domain.Character;
import com.gnoemes.shikimoriapp.entity.roles.domain.PersonDetails;
import com.gnoemes.shikimoriapp.entity.roles.domain.Work;
import com.gnoemes.shikimoriapp.entity.roles.presentation.person.PersonHeadItem;
import com.gnoemes.shikimoriapp.entity.roles.presentation.person.PersonRelatedItem;
import com.gnoemes.shikimoriapp.entity.roles.presentation.person.PersonRelatedItemType;
import com.gnoemes.shikimoriapp.entity.roles.presentation.person.PersonRelatedListItem;
import com.gnoemes.shikimoriapp.entity.roles.presentation.person.PersonRelatedType;
import com.gnoemes.shikimoriapp.utils.date.converter.DateTimeConverter;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PersonDetailsViewModelConverterImpl implements PersonDetailsViewModelConverter {

    private DateTimeConverter dateTimeConverter;

    @Inject
    public PersonDetailsViewModelConverterImpl(DateTimeConverter dateTimeConverter) {
        this.dateTimeConverter = dateTimeConverter;
    }

    @Override
    public List<BaseItem> apply(PersonDetails personDetails) {
        List<BaseItem> items = new ArrayList<>();

        items.add(convertHeadItem(personDetails));

        if (personDetails.getCharacters() != null && !personDetails.getCharacters().isEmpty()) {
            items.add(new DoubleDividerItem());
            items.add(convertCharactersRelatedItem(personDetails.getCharacters()));
        }

        if (personDetails.getWorks() != null && !personDetails.getWorks().isEmpty()) {
            items.add(new DoubleDividerItem());
            items.add(convertWorksRelatedItem(personDetails.getWorks()));
        }

        items.add(new BottomDividerItem());

        return items;
    }

    private PersonRelatedListItem convertCharactersRelatedItem(List<Character> characters) {
        List<PersonRelatedItem> items = new ArrayList<>();

        for (Character character : characters) {
            items.add(new PersonRelatedItem(character.getId(),
                    character.getImage(),
                    character.getRussianName(),
                    PersonRelatedItemType.CHARACTER));
        }

        return new PersonRelatedListItem(PersonRelatedType.CHARACTERS, items);
    }

    private PersonRelatedListItem convertWorksRelatedItem(List<Work> works) {
        List<PersonRelatedItem> items = new ArrayList<>();

        for (Work work : works) {
            items.add(convertWork(work));
        }

        return new PersonRelatedListItem(PersonRelatedType.WORKS, items);
    }

    private PersonRelatedItem convertWork(Work work) {
        switch (work.getType()) {
            case ANIME:
                Anime anime = work.getAnime();
                return new PersonRelatedItem(anime.getId(), anime.getImage(), work.getRole(), PersonRelatedItemType.ANIME);
            case MANGA:
                Manga manga = work.getManga();
                return new PersonRelatedItem(manga.getId(), convertImage(manga.getImage()), work.getRole(), PersonRelatedItemType.MANGA);
        }

        return null;
    }

    private Image convertImage(MangaImage image) {
        return new Image(image.getImageOriginalUrl(),
                image.getImagePreviewUrl(),
                image.getImageX96Url(),
                image.getImageX48Url());
    }

    private PersonHeadItem convertHeadItem(PersonDetails personDetails) {
        return new PersonHeadItem(
                personDetails.getId(),
                personDetails.getImage(),
                personDetails.getName(),
                personDetails.getRussianName(),
                personDetails.getJapaneseName(),
                personDetails.getJobTitle(),
                convertBirthDay(personDetails.getBirthDay()),
                convertRoles(personDetails.getRoles()));
    }

    private String convertRoles(List<List<String>> roles) {
        StringBuilder builder = new StringBuilder();

        for (List<String> list : roles) {
            boolean first = true;
            for (String role : list) {
                builder.append(role);
                if (first) {
                    builder.append(": ");
                } else {
                    builder.append("\n");
                }
                first = false;
            }
        }

        return builder.toString();
    }

    private String convertBirthDay(DateTime birthDay) {
        return dateTimeConverter.convertToFullHumanDateString(birthDay);
    }
}
