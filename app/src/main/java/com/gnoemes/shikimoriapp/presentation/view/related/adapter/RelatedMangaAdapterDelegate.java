package com.gnoemes.shikimoriapp.presentation.view.related.adapter;

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
import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.related.presentation.RelatedMangaItem;
import com.gnoemes.shikimoriapp.presentation.view.main.provider.MangaResourceProvider;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RelatedMangaAdapterDelegate extends AdapterDelegate<List<BaseItem>> {

    private ImageLoader imageLoader;
    private MangaResourceProvider resourceProvider;
    private RelatedItemCallback callback;

    public RelatedMangaAdapterDelegate(ImageLoader imageLoader,
                                       MangaResourceProvider resourceProvider,
                                       RelatedItemCallback callback) {
        this.imageLoader = imageLoader;
        this.resourceProvider = resourceProvider;
        this.callback = callback;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseItem> items, int position) {
        return items.get(position) instanceof RelatedMangaItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_related, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        RelatedMangaItem item = (RelatedMangaItem) items.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bind(item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint)
        ConstraintLayout layout;
        @BindView(R.id.image)
        ImageView imageView;
        @BindView(R.id.text_type)
        TextView typeView;
        @BindView(R.id.title)
        TextView titleView;
        @BindView(R.id.description)
        TextView descriptionView;

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

        public void bind(RelatedMangaItem item) {

            imageLoader.setImageWithFit(imageView, item.getManga().getImage().getOriginal());

            typeView.setText(item.getManga().getType().name().replace("_", " "));

            String name = TextUtils.isEmpty(item.getManga().getNameRu()) ? item.getManga().getName() : item.getManga().getNameRu();
            titleView.setText(name);

            descriptionView.setText(String.format("%s\n(%s, %s г.)", item.getRelationRussian(),
                    resourceProvider.getLocalizedType(item.getManga().getType()),
                    String.valueOf(item.getManga().getDateAired() == null ? "xxx" : item.getManga().getDateAired().getYear())
            ));

            layout.setOnClickListener(v -> callback.onRelatedItemClicked(Type.MANGA, item.getManga().getId()));
        }
    }
}
