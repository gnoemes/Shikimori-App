package com.gnoemes.shikimoriapp.entity.roles.presentation;

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.roles.domain.CharacterRelatedType;

import java.util.List;

public class CharacterRelatedListItem extends BaseItem {

    private CharacterRelatedType relatedType;
    private List<CharacterRelatedItem> items;

    public CharacterRelatedListItem(CharacterRelatedType relatedType, List<CharacterRelatedItem> items) {
        this.relatedType = relatedType;
        this.items = items;
    }

    public CharacterRelatedType getRelatedType() {
        return relatedType;
    }

    public List<CharacterRelatedItem> getItems() {
        return items;
    }
}
