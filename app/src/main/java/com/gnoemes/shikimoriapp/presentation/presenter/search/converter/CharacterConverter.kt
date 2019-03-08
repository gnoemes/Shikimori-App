package com.gnoemes.shikimoriapp.presentation.presenter.search.converter

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem
import com.gnoemes.shikimoriapp.entity.roles.domain.Character
import com.gnoemes.shikimoriapp.entity.roles.presentation.character.CharacterViewModel

interface CharacterConverter {

    fun convertListFrom(characters: List<Character>?): List<BaseItem>

    fun convertCharacter(character: Character): CharacterViewModel
}