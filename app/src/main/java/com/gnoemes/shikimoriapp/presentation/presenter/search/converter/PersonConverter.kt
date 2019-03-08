package com.gnoemes.shikimoriapp.presentation.presenter.search.converter

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem
import com.gnoemes.shikimoriapp.entity.roles.domain.Person
import com.gnoemes.shikimoriapp.entity.roles.presentation.person.PersonViewModel

interface PersonConverter {

    fun convertListFrom(persons: List<Person>?): List<BaseItem>

    fun convertPerson(person: Person): PersonViewModel
}