package com.gnoemes.shikimoriapp.presentation.view.profile.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.domain.FavoriteType;
import com.gnoemes.shikimoriapp.entity.user.domain.Favorite;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.BaseProfileItem;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.ProfileAction;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.ProfileOtherItem;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileOtherDelegateAdapter extends AdapterDelegate<List<BaseProfileItem>> {

    private ImageLoader imageLoader;
    private ProfileCallback callback;

    public ProfileOtherDelegateAdapter(ImageLoader imageLoader,
                                       ProfileCallback callback) {
        this.imageLoader = imageLoader;
        this.callback = callback;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseProfileItem> items, int position) {
        return items.get(position) instanceof ProfileOtherItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_profile_other, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseProfileItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ProfileOtherItem item = (ProfileOtherItem) items.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bind(item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint)
        ConstraintLayout layout;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.placeholder)
        TextView placeholder;
        @BindView(R.id.list)
        RecyclerView list;

        private FavoriteAdapter adapter;

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

            title.setText(R.string.common_favorite);

            LinearLayoutManager manager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            list.setLayoutManager(manager);
            adapter = new FavoriteAdapter();
        }

        public void bind(ProfileOtherItem item) {
            List<Favorite> favorites = item.getFavorites();
            if (favorites != null && !favorites.isEmpty()) {
                list.setAdapter(adapter);
                adapter.bindItems(favorites);
                list.setVisibility(View.VISIBLE);
                placeholder.setVisibility(View.GONE);
            } else {
                placeholder.setVisibility(View.VISIBLE);
                list.setVisibility(View.GONE);
            }
        }
    }


    class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

        private List<Favorite> items = new ArrayList<>();

        void bindItems(List<Favorite> items) {
            this.items.clear();
            this.items.addAll(items);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_image_rectangle, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Favorite item = items.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.image)
            ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void bind(Favorite item) {
                imageLoader.setImageWithFit(imageView, item.getImageUrl());

                imageView.setOnClickListener(v -> callback.onAction(convertAction(item.getType()), item.getId()));
            }

            private ProfileAction convertAction(FavoriteType type) {
                switch (type) {
                    case ANIME:
                        return ProfileAction.FAVORITE_ANIME;
                    case MANGA:
                        return ProfileAction.FAVORITE_MANGA;
                    case SEYU:
                        return ProfileAction.FAVORITE_SEYU;
                    case PEOPLE:
                        return ProfileAction.FAVORITE_PEOPLE;
                    case MANGAKAS:
                        return ProfileAction.FAVORITE_MANGAKAS;
                    case PRODUCERS:
                        return ProfileAction.FAVORITE_PRODUCERS;
                    case CHARACTERS:
                        return ProfileAction.FAVORITE_CHARACTERS;
                    default:
                        return ProfileAction.NONE;
                }
            }
        }
    }
}
