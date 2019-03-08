package com.gnoemes.shikimoriapp.presentation.presenter.search.converter

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem
import com.gnoemes.shikimoriapp.entity.roles.domain.Character
import com.gnoemes.shikimoriapp.entity.roles.presentation.character.CharacterViewModel
import javax.inject.Inject

class CharacterConverterImpl @Inject constructor() : CharacterConverter {

    override fun convertListFrom(characters: List<Character>?): List<BaseItem> {
        if (characters == null) {
            return emptyList()
        }

        return characters.map { convertCharacter(it) }
    }

    override fun convertCharacter(character: Character): CharacterViewModel =
            CharacterViewModel(
                    character.id,
                    character.name,
                    character.russianName,
                    character.image,
                    character.url
            )
}