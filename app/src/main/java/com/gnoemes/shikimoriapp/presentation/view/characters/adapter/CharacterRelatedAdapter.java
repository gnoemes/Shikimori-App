package com.gnoemes.shikimoriapp.presentation.view.characters.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.roles.domain.CharacterRelatedType;
import com.gnoemes.shikimoriapp.entity.roles.presentation.character.CharacterRelatedItem;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharacterRelatedAdapter extends RecyclerView.Adapter<CharacterRelatedAdapter.ViewHolder> {

    private CharacterRelatedType relatedType;
    private ImageLoader imageLoader;
    private CharacterItemCallback callback;

    private List<CharacterRelatedItem> items = new ArrayList<>();

    public CharacterRelatedAdapter(CharacterRelatedType relatedType,
                                   ImageLoader imageLoader,
                                   CharacterItemCallback callback) {
        this.relatedType = relatedType;
        this.imageLoader = imageLoader;
        this.callback = callback;
    }

    public void bindItems(List<CharacterRelatedItem> relatedItems) {
        items.clear();
        items.addAll(relatedItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_medium, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
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

        public void bind(CharacterRelatedItem item) {
            imageView.setOnClickListener(null);
            imageLoader.setImageWithFit(imageView, item.getImage().getOriginal());
            imageView.setOnClickListener(v -> callback.onItemClicked(relatedType, item.getId()));
        }
    }
}
