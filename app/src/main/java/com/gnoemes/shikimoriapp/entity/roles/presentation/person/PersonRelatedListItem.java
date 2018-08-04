package com.gnoemes.shikimoriapp.entity.roles.presentation.person;

import java.util.List;

public class PersonRelatedListItem extends BasePersonItem {

    private PersonRelatedType type;
    private List<PersonRelatedItem> items;

    public PersonRelatedListItem(PersonRelatedType type, List<PersonRelatedItem> items) {
        this.type = type;
        this.items = items;
    }

    public PersonRelatedType getType() {
        return type;
    }

    public List<PersonRelatedItem> getItems() {
        return items;
    }
}
