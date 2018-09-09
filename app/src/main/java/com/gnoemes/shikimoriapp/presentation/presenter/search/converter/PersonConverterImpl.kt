package com.gnoemes.shikimoriapp.presentation.presenter.search.converter

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem
import com.gnoemes.shikimoriapp.entity.roles.domain.Person
import com.gnoemes.shikimoriapp.entity.roles.presentation.person.PersonViewModel
import javax.inject.Inject

class PersonConverterImpl @Inject constructor() : PersonConverter {


    override fun convertListFrom(persons: List<Person>?): List<BaseItem> {
        if (persons == null) {
            return emptyList()
        }

        return persons.map { convertPerson(it) }
    }

    override fun convertPerson(person: Person): PersonViewModel =
            PersonViewModel(
                    person.id,
                    person.name,
                    person.russianName,
                    person.image,
                    person.url
            )
}