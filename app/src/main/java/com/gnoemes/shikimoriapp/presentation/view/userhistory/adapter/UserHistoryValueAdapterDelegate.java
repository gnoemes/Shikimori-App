package com.gnoemes.shikimoriapp.presentation.view.userhistory.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.user.presentation.history.HistoryItem;
import com.gnoemes.shikimoriapp.utils.view.DefaultItemCallback;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserHistoryValueAdapterDelegate extends AdapterDelegate<List<BaseItem>> {

    private DefaultItemCallback callback;

    public UserHistoryValueAdapterDelegate(DefaultItemCallback callback) {
        this.callback = callback;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseItem> items, int position) {
        return items.get(position) instanceof HistoryItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_user_history, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        HistoryItem item = (HistoryItem) items.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bind(item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint)
        ConstraintLayout layout;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.description)
        TextView desctiption;

        @BindView(R.id.date)
        TextView date;

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

        @SuppressWarnings("ConstantConditions")
        public void bind(HistoryItem item) {
            title.setText(null);
            desctiption.setText(null);

            date.setText(item.getActionDate());
            if (item.isHasTarget()) {
                title.setText(item.getValueName());
                desctiption.setText(Html.fromHtml(item.getDescription()));

                if (item.getValueType() == Type.ANIME) {
                    layout.setOnClickListener(v -> callback.onItemClick(item.getValueId()));
                }
            } else {
                title.setText(item.getDescription());
            }
        }
    }
}
