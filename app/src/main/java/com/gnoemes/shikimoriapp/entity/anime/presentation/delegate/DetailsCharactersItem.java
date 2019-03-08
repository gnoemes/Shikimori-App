package com.gnoemes.shikimoriapp.entity.anime.presentation.delegate;

import com.gnoemes.shikimoriapp.entity.roles.domain.Character;

import java.util.List;

public class DetailsCharactersItem extends BaseAnimeItem {

    private List<Character> characters;

    public DetailsCharactersItem(List<Character> characters) {
        this.characters = characters;
    }

    public List<Character> getCharacters() {
        return characters;
    }
}
