package com.gnoemes.shikimoriapp.presentation.view.characters.adapter;

import com.gnoemes.shikimoriapp.entity.roles.domain.CharacterRelatedType;

public interface CharacterItemCallback {

    void onItemClicked(CharacterRelatedType type, long id);
}
