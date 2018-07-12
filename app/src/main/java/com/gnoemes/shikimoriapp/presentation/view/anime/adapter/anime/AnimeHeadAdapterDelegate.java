package com.gnoemes.shikimoriapp.presentation.view.anime.adapter.anime;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeAction;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.AnimeHeadItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.GenresAdapter;
import com.gnoemes.shikimoriapp.presentation.view.anime.provider.RateResourceProvider;
import com.gnoemes.shikimoriapp.utils.view.AttributesHelper;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.gnoemes.shikimoriapp.utils.view.RecyclerItemClickListener;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.mpt.android.stv.Slice;
import com.mpt.android.stv.SpannableTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimeHeadAdapterDelegate extends AdapterDelegate<List<BaseItem>> {

    private AnimeItemCallback callback;
    private RateResourceProvider resourceProvider;

    AnimeHeadAdapterDelegate(RateResourceProvider resourceProvider, @NonNull AnimeItemCallback callback) {
        this.resourceProvider = resourceProvider;
        this.callback = callback;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseItem> items, int position) {
        return items.get(position) instanceof AnimeHeadItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_anime_head, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ViewHolder viewHolder = (ViewHolder) holder;
        AnimeHeadItem headItem = (AnimeHeadItem) items.get(position);
        viewHolder.bind(headItem);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint)
        ConstraintLayout layout;

        @BindView(R.id.button_online)
        TextView buttonOnline;

        @BindView(R.id.image_options)
        ImageView optionsView;

        @BindView(R.id.compound_rate)
        TextView rateView;

        @BindView(R.id.compound_similar)
        TextView similarView;

        @BindView(R.id.compound_related)
        TextView relatedView;

        @BindView(R.id.text_season)
        SpannableTextView seasonView;

        @BindView(R.id.text_type)
        SpannableTextView typeView;

        @BindView(R.id.text_status)
        SpannableTextView statusView;

        @BindView(R.id.text_genre)
        SpannableTextView genreView;

        @BindView(R.id.list_genres)
        RecyclerView genresList;

        @BindView(R.id.rating)
        RatingBar ratingBar;

        @BindView(R.id.text_rating)
        TextView ratingValue;


        private GenresAdapter adapter;
        private int textColor;
        private int textSize;
        private Drawable addEmpty;
        private Drawable add;
        private Drawable similar;
        private Drawable related;
        private Drawable play;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            textColor = AttributesHelper.withContext(itemView.getContext())
                    .getColor(R.attr.colorText);
            textSize = (int) itemView.getContext().getResources().getDimension(R.dimen.text_default);

            Drawable card = DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.card)
                    .withAttributeColor(R.attr.colorBackgroundContent)
                    .tint()
                    .get();

            layout.setBackground(card);

            addEmpty = DrawableHelper.withContext(itemView.getContext())
                    .withDrawable(R.drawable.ic_star_border)
                    .withAttributeColor(R.attr.colorText)
                    .tint()
                    .get();

            add = DrawableHelper.withContext(itemView.getContext())
                    .withDrawable(R.drawable.ic_star_big)
                    .withColor(R.color.colorAccentInverse)
                    .tint()
                    .get();

            similar = DrawableHelper.withContext(itemView.getContext())
                    .withDrawable(R.drawable.ic_arrange_send_backward)
                    .withAttributeColor(R.attr.colorText)
                    .tint()
                    .get();

            related = DrawableHelper.withContext(itemView.getContext())
                    .withDrawable(R.drawable.ic_arrange_bring_to_front)
                    .withAttributeColor(R.attr.colorText)
                    .tint()
                    .get();

            play = DrawableHelper.withContext(itemView.getContext())
                    .withDrawable(R.drawable.ic_play_circle_filled)
                    .withColor(R.color.white)
                    .tint()
                    .get();

            adapter = new GenresAdapter();

            FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(itemView.getContext());
            flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
            genresList.setLayoutManager(flexboxLayoutManager);
            genresList.setAdapter(adapter);
            genresList.addOnItemTouchListener(new RecyclerItemClickListener(itemView.getContext(), (view, position) ->
                    callback.onAction(AnimeAction.GENRE, adapter.getItemByPosition(position))));

            similarView.setCompoundDrawablesWithIntrinsicBounds(null, similar, null, null);
            relatedView.setCompoundDrawablesWithIntrinsicBounds(null, related, null, null);
            buttonOnline.setCompoundDrawablesWithIntrinsicBounds(play, null, null, null);


            buttonOnline.setOnClickListener(v -> callback.onAction(AnimeAction.WATCH_ONLINE, null));
            similarView.setOnClickListener(v -> callback.onAction(AnimeAction.SIMILAR, null));
            relatedView.setOnClickListener(v -> callback.onAction(AnimeAction.RELATED, null));
            optionsView.setOnClickListener(v -> showPopup());
        }

        public void bind(AnimeHeadItem item) {
            rateView.setOnClickListener(null);

            seasonView.reset();
            typeView.reset();
            statusView.reset();
            genreView.reset();

            seasonView.addSlice(getSliceTitle(itemView.getResources().getString(R.string.common_season)));
            typeView.addSlice(getSliceTitle(itemView.getResources().getString(R.string.common_type)));
            statusView.addSlice(getSliceTitle(itemView.getResources().getString(R.string.common_status)));
            genreView.addSlice(getSliceTitle(itemView.getResources().getString(R.string.common_genre)));

            seasonView.addSlice(getSliceContent(item.getSeason()));
            seasonView.display();

            typeView.addSlice(getSliceContent(item.getAnimeType()));
            typeView.display();

            statusView.addSlice(getSliceContent(item.getAnimeStatus()));
            statusView.display();

            genreView.display();
            adapter.bindItems(item.getGenres());

            ratingBar.setRating((float) (item.getScore() / 2));
            ratingValue.setText(String.valueOf(item.getScore()));

            if (item.getAnimeRate() == null) {
                rateView.setCompoundDrawablesWithIntrinsicBounds(null, addEmpty, null, null);
                rateView.setText(itemView.getResources().getString(R.string.no_rate));
            } else {
                rateView.setCompoundDrawablesWithIntrinsicBounds(null, add, null, null);
                rateView.setText(resourceProvider.getLocalizedStatus(item.getAnimeRate().getStatus()));
            }


            rateView.setOnClickListener(v -> callback.onAction(AnimeAction.ADD_TO_LIST, item.getAnimeRate()));
        }

        private void showPopup() {
            Context wrapper = new ContextThemeWrapper(itemView.getContext(), R.style.PopupMenuTheme);
            PopupMenu popupMenu = new PopupMenu(wrapper, optionsView);
            popupMenu.inflate(R.menu.menu_anime);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.item_open:
                        callback.onAction(AnimeAction.OPEN_IN_BROWSER, null);
                        break;
                    case R.id.item_clear_history:
                        callback.onAction(AnimeAction.CLEAR_HISTORY, null);
                        break;
                }
                return false;
            });
            popupMenu.show();
        }


        /**
         * Returns slice for title (e.g. <b>Genre:</b>)
         *
         * @param text String
         * @return Slice title
         */
        private Slice getSliceTitle(String text) {
            return new Slice.Builder(text.concat(": "))
                    .textColor(textColor)
                    .textSize(textSize)
                    .style(Typeface.BOLD)
                    .build();
        }

        /**
         * Returns slice for content (base text)
         *
         * @param text String
         * @return Slice content
         */
        private Slice getSliceContent(String text) {
            return new Slice.Builder(text)
                    .textColor(textColor)
                    .textSize(textSize)
                    .build();
        }
    }
}
