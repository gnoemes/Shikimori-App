package com.gnoemes.shikimoriapp.presentation.view.profile.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.BaseProfileItem;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.ProfileAction;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.ProfileSocialItem;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileSocialAdapterDelegate extends AdapterDelegate<List<BaseProfileItem>> {

    private ImageLoader imageLoader;
    private ProfileCallback callback;

    public ProfileSocialAdapterDelegate(ImageLoader imageLoader, ProfileCallback callback) {
        this.imageLoader = imageLoader;
        this.callback = callback;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseProfileItem> items, int position) {
        return items.get(position) instanceof ProfileSocialItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_profile_social, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseProfileItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ViewHolder viewHolder = (ViewHolder) holder;
        ProfileSocialItem item = (ProfileSocialItem) items.get(position);
        viewHolder.bind(item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint)
        ConstraintLayout layout;
        @BindView(R.id.list_friends)
        RecyclerView friendsList;
        @BindView(R.id.list_clubs)
        RecyclerView clubsList;

        private ImageContentAdapter friendsAdapter;
        private ImageContentAdapter clubsAdapter;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            Drawable card = DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.card)
                    .withAttributeColor(R.attr.colorBackgroundContent)
                    .tint()
                    .get();

            layout.setBackground(card);

            FlexboxLayoutManager friendsManager = new FlexboxLayoutManager(itemView.getContext());
            friendsManager.setFlexWrap(FlexWrap.WRAP);
            friendsManager.setFlexWrap(FlexWrap.WRAP);
            friendsManager.setJustifyContent(JustifyContent.FLEX_START);

            FlexboxLayoutManager clubsManager = new FlexboxLayoutManager(itemView.getContext());
            clubsManager.setFlexWrap(FlexWrap.WRAP);
            clubsManager.setFlexWrap(FlexWrap.WRAP);
            clubsManager.setJustifyContent(JustifyContent.FLEX_START);

            friendsList.setLayoutManager(friendsManager);
            clubsList.setLayoutManager(clubsManager);
        }

        public void bind(ProfileSocialItem item) {

            friendsAdapter = new ImageContentAdapter(R.layout.item_image_content_friends,
                    R.string.common_no_friends,
                    imageLoader,
                    id -> callback.onAction(ProfileAction.FRIEND, id));
            clubsAdapter = new ImageContentAdapter(R.layout.item_image_content_clubs,
                    R.string.common_no_clubs,
                    imageLoader,
                    id -> callback.onAction(ProfileAction.CLUB, id));

            friendsList.setAdapter(friendsAdapter);
            clubsList.setAdapter(clubsAdapter);

            friendsAdapter.bindItems(item.getFriends());
            clubsAdapter.bindItems(item.getClubs());
        }
    }
}
