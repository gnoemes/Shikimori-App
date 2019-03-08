package com.gnoemes.shikimoriapp.presentation.view.anime.adapter.anime;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.presentation.DetailsAction;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.DetailsActionItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActionAdapterDelegate extends AdapterDelegate<List<BaseItem>> {

    private AnimeItemCallback callback;

    public DetailsActionAdapterDelegate(AnimeItemCallback callback) {
        this.callback = callback;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseItem> items, int position) {
        return items.get(position) instanceof DetailsActionItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_details_action, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DetailsActionItem item = (DetailsActionItem) items.get(position);
        viewHolder.bind(item);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint)
        ConstraintLayout layout;

        @BindView(R.id.image_action)
        ImageView imageAction;

        @BindView(R.id.text_action)
        TextView textAction;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void bind(DetailsActionItem item) {
            layout.setOnClickListener(null);

            Drawable image = getActionDrawable(item.getAction());
            imageAction.setImageDrawable(image);

            textAction.setText(getText(item.getAction()));

            layout.setOnClickListener(v -> callback.onAction(item.getAction(), null));
        }

        private int getText(DetailsAction action) {
            switch (action) {
                case LINKS:
                    return R.string.common_links;
                case SIMILAR:
                    return R.string.common_similar;
                case CHRONOLOGY:
                    return R.string.chronology;
                case RELATED:
                    return R.string.related;
                case COMMENTS:
                    return R.string.common_comments;
                default:
                    return 0;
            }
        }

        private Drawable getActionDrawable(DetailsAction action) {
            switch (action) {
                case LINKS:
                    return getDrawable(R.drawable.ic_attachment);
                case SIMILAR:
                    return getDrawable(R.drawable.ic_arrange_send_backward);
                case CHRONOLOGY:
                    return getDrawable(R.drawable.ic_animation_play);
                case RELATED:
                    return getDrawable(R.drawable.ic_arrange_bring_to_front);
                default:
                    return null;
            }
        }

        private Drawable getDrawable(@DrawableRes int drawableRes) {
            return DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(drawableRes)
                    .withAttributeColor(R.attr.colorText)
                    .tint()
                    .get();
        }
    }
}
