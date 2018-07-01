package com.gnoemes.shikimoriapp.presentation.view.anime.adapter.anime;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.roles.domain.Character;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DefaultItemCallback;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimeCharacterAdapter extends RecyclerView.Adapter<AnimeCharacterAdapter.ViewHolder> {

    private ImageLoader imageLoader;
    private DefaultItemCallback callback;

    private List<Character> items = new ArrayList<>();

    public AnimeCharacterAdapter(ImageLoader imageLoader,
                                 DefaultItemCallback callback) {
        this.imageLoader = imageLoader;
        this.callback = callback;
    }

    public void bindItems(List<Character> charactersItems) {
        items.clear();
        items.addAll(charactersItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_character, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Character item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint)
        ConstraintLayout layout;
        @BindView(R.id.character_image)
        ImageView characterImage;
        @BindView(R.id.character_name)
        TextView characterName;

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
        }

        public void bind(Character item) {
            layout.setOnClickListener(null);
            characterName.setText(null);

            imageLoader.setImageWithFit(characterImage, item.getAnimeImage().getImageOriginalUrl());

            String name = TextUtils.isEmpty(item.getRussianName()) ? item.getName() : item.getRussianName();
            characterName.setText(name);

            layout.setOnClickListener(v -> callback.onItemClick(item.getId()));
        }
    }

}
