package com.gnoemes.shikimoriapp.data.repository.roles.converter;

import com.gnoemes.shikimoriapp.data.repository.app.converter.ImageResponseConverter;
import com.gnoemes.shikimoriapp.entity.roles.data.PersonResponse;
import com.gnoemes.shikimoriapp.entity.roles.domain.Person;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PersonResponseConverterImpl implements PersonResponseConverter {

    private ImageResponseConverter imageResponseConverter;

    @Inject
    public PersonResponseConverterImpl(ImageResponseConverter imageResponseConverter) {
        this.imageResponseConverter = imageResponseConverter;
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
                imageResponseConverter.convert(response.getImageResponse()),
                response.getUrl());
    }
}
