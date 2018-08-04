package com.gnoemes.shikimoriapp.presentation.view.menu.adapter;

import android.support.annotation.NonNull;

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.menu.domain.MenuCategory;
import com.gnoemes.shikimoriapp.entity.menu.presentration.MenuCategoryWithBadgeViewModel;
import com.gnoemes.shikimoriapp.entity.menu.presentration.MenuProfileViewModel;
import com.gnoemes.shikimoriapp.presentation.view.common.adapter.BottomDividerAdapterDelegate;
import com.gnoemes.shikimoriapp.presentation.view.common.adapter.DoubleDividerAdapterDelegate;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends ListDelegationAdapter<List<BaseItem>> {

    public MenuAdapter(@NonNull ImageLoader loader,
                       @NonNull MenuItemCallback callback) {
        delegatesManager.addDelegate(new MenuProfileDelegationAdapter(loader, callback));
        delegatesManager.addDelegate(new MenuCategoryWithBadgeAdapterDelegate(callback));
        delegatesManager.addDelegate(new MenuCategoryAdapterDelegate(callback));
        delegatesManager.addDelegate(new BottomDividerAdapterDelegate());
        delegatesManager.addDelegate(new DoubleDividerAdapterDelegate());

        setItems(new ArrayList<>());
    }

    public void bindItems(List<BaseItem> menuItems) {
        items.clear();
        items.addAll(menuItems);
    }

    public void updateBadge(MenuCategoryWithBadgeViewModel viewModel) {
        items.set(viewModel.getCategory().getPosition(), viewModel);
        notifyItemChanged(viewModel.getCategory().getPosition());
    }

    public void updateUser(MenuProfileViewModel viewModel) {
        items.set(MenuCategory.PROFILE.getPosition(), viewModel);
        notifyItemChanged(MenuCategory.PROFILE.getPosition());
    }
}
