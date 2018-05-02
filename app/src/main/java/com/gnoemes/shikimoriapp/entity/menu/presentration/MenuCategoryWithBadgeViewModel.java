package com.gnoemes.shikimoriapp.entity.menu.presentration;

import com.gnoemes.shikimoriapp.entity.menu.domain.MenuCategory;

public class MenuCategoryWithBadgeViewModel extends BaseMenuItem {

    private MenuCategory category;
    private boolean isNeedBadge;
    private int badgeValue;

    public MenuCategoryWithBadgeViewModel(MenuCategory category,
                                          boolean isNeedBadge,
                                          int badgeValue) {
        this.category = category;
        this.isNeedBadge = isNeedBadge;
        this.badgeValue = badgeValue;
    }

    public MenuCategory getCategory() {
        return category;
    }

    public boolean isNeedBadge() {
        return isNeedBadge;
    }

    public int getBadgeValue() {
        return badgeValue;
    }
}
