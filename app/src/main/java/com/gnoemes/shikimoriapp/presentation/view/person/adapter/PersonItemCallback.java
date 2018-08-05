package com.gnoemes.shikimoriapp.presentation.view.person.adapter;

import com.gnoemes.shikimoriapp.entity.roles.presentation.person.PersonRelatedItemType;

public interface PersonItemCallback {

    void onRelatedItemClicked(PersonRelatedItemType type, long id);
}
