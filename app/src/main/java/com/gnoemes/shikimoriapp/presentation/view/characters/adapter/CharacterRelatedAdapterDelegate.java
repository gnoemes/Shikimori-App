package com.gnoemes.shikimoriapp.presentation.view.characters.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.roles.presentation.character.CharacterRelatedListItem;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharacterRelatedAdapterDelegate extends AdapterDelegate<List<BaseItem>> {

    private ImageLoader imageLoader;
    private CharacterItemCallback callback;

    public CharacterRelatedAdapterDelegate(ImageLoader imageLoader, CharacterItemCallback callback) {
        this.imageLoader = imageLoader;
        this.callback = callback;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseItem> items, int position) {
        return items.get(position) instanceof CharacterRelatedListItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_character_related, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        CharacterRelatedListItem item = (CharacterRelatedListItem) items.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bind(item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint)
        ConstraintLayout layout;
        @BindView(R.id.list)
        RecyclerView recyclerView;
        @BindView(R.id.text_type)
        TextView typeView;

        private CharacterRelatedAdapter adapter;

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

        public void bind(CharacterRelatedListItem item) {
            recyclerView.setLayoutManager(null);
            recyclerView.setAdapter(null);
            typeView.setText(null);

            switch (item.getRelatedType()) {
                case SEYU:
                    typeView.setText(R.string.seyu);
                    break;
                case ANIME:
                    typeView.setText(R.string.anime);
                    break;
                case MANGA:
                    typeView.setText(R.string.type_manga_translatable);
                    break;
            }

            adapter = new CharacterRelatedAdapter(item.getRelatedType(), imageLoader, callback);

            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(adapter);
            adapter.bindItems(item.getItems());
        }
    }
}
