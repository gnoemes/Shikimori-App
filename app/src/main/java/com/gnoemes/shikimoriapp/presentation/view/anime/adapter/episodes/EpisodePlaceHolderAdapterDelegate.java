package com.gnoemes.shikimoriapp.presentation.view.anime.adapter.episodes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.BaseEpisodeItem;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodeOptionAction;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.EpisodePlaceholderItem;
import com.gnoemes.shikimoriapp.presentation.view.common.widget.EmptyContentViewWithButton;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EpisodePlaceHolderAdapterDelegate extends AdapterDelegate<List<BaseEpisodeItem>> {


    private EpisodeOptionCallback callback;

    public EpisodePlaceHolderAdapterDelegate(EpisodeOptionCallback callback) {
        this.callback = callback;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseEpisodeItem> items, int position) {
        return items.get(position) instanceof EpisodePlaceholderItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_placeholder_with_button, parent, false);
        return new EpisodePlaceHolderAdapterDelegate.ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseEpisodeItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        EpisodePlaceholderItem item = (EpisodePlaceholderItem) items.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bind(item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.view_empty)
        EmptyContentViewWithButton emptyContentView;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            emptyContentView.setText(R.string.episode_nothing);
            emptyContentView.setButtonText(R.string.episodes_alternative);
        }


        public void bind(EpisodePlaceholderItem item) {
            emptyContentView.setCallback(null);
            emptyContentView.setCallback(v -> callback.onAction(EpisodeOptionAction.ALTERNATIVE_SOURCE, null));
        }
    }
}
