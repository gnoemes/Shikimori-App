package com.gnoemes.shikimoriapp.entity.menu.presentration;

import com.gnoemes.shikimoriapp.entity.menu.domain.MenuCategory;

public class MenuCategoryViewModel extends BaseMenuItem {

    private MenuCategory category;

    public MenuCategoryViewModel(MenuCategory category) {
        this.category = category;
    }

    public MenuCategory getCategory() {
        return category;
    }
}
