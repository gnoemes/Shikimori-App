package com.gnoemes.shikimoriapp.data.repository.roles.converter;

import com.gnoemes.shikimoriapp.entity.anime.data.DefaultImageResponse;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeImage;
import com.gnoemes.shikimoriapp.entity.roles.data.PersonResponse;
import com.gnoemes.shikimoriapp.entity.roles.domain.Person;
import com.gnoemes.shikimoriapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PersonResponseConverterImpl implements PersonResponseConverter {

    @Inject
    public PersonResponseConverterImpl() {
    }

    @Override
    public List<Person> apply(List<PersonResponse> responses) {
        List<Person> items = new ArrayList<>();

        for (PersonResponse response : responses) {
            items.add(convertResponse(response));
        }

        return items;
    }

    @Override
    public Person convertResponse(PersonResponse response) {
        if (response == null) {
            return null;
        }

        return new Person(response.getId(),
                response.getName(),
                response.getRussianName(),
                convertAnimeImage(response.getImageResponse()),
                response.getUrl());
    }

    @Override
    public AnimeImage convertAnimeImage(DefaultImageResponse image) {
        return new AnimeImage(
                Utils.appendHostIfNeed(image.getImageOriginalUrl()),
                Utils.appendHostIfNeed(image.getImagePreviewUrl()),
                Utils.appendHostIfNeed(image.getImageX96Url()),
                Utils.appendHostIfNeed(image.getImageX48Url()));
    }
}
