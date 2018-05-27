package com.gnoemes.shikimoriapp.presentation.view.profile.adapter;

import com.gnoemes.shikimoriapp.entity.user.presentation.profile.BaseProfileItem;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProfileAdapter extends ListDelegationAdapter<List<BaseProfileItem>> {

    private int HEAD_POSITION = 1;
    private int RATES_POSITION = 3;
    private int SOCIAL_POSITION = 5;
    private int OTHER_POSITION = 7;

    public ProfileAdapter(ImageLoader loader,
                          ProfileCallback callback) {
        delegatesManager.addDelegate(new ProfileHeadAdapterDelegate(loader, callback));
        delegatesManager.addDelegate(new ProfileRatesAdapterDelegate(callback));
        delegatesManager.addDelegate(new ProfileActivityAdapterDelegate(callback));
        delegatesManager.addDelegate(new ProfileSocialAdapterDelegate(loader, callback));
        delegatesManager.addDelegate(new ProfileOtherDelegateAdapter(loader, callback));
        delegatesManager.addDelegate(new ProfileDividerAdapterDelegate());

        setItems(new ArrayList<>());
    }

    public void bindItems(List<BaseProfileItem> itemList) {
        items.clear();
        items.addAll(itemList);
        notifyDataSetChanged();
    }

    public void updateHead(BaseProfileItem item) {
        items.set(HEAD_POSITION, item);
        notifyItemChanged(HEAD_POSITION);
    }

    public void updateRateStatuses(BaseProfileItem item) {
        items.set(RATES_POSITION, item);
        notifyItemChanged(RATES_POSITION);
    }

    public void updateSocial(BaseProfileItem item) {
        items.set(SOCIAL_POSITION, item);
        notifyItemChanged(SOCIAL_POSITION);
    }

    public void updateOther(BaseProfileItem item) {
        items.set(OTHER_POSITION, item);
        notifyItemChanged(OTHER_POSITION);
    }
}
