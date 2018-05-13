package com.gnoemes.shikimoriapp.presentation.view.anime.adapter.anime;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeAction;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.AnimeHeadItem;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.BaseAnimeItem;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.GenresAdapter;
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

public class AnimeHeadAdapterDelegate extends AdapterDelegate<List<BaseAnimeItem>> {

    private AnimeItemCallback callback;

    AnimeHeadAdapterDelegate(@NonNull AnimeItemCallback callback) {
        this.callback = callback;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseAnimeItem> items, int position) {
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
    protected void onBindViewHolder(@NonNull List<BaseAnimeItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ViewHolder viewHolder = (ViewHolder) holder;
        AnimeHeadItem headItem = (AnimeHeadItem) items.get(position);
        viewHolder.bind(headItem);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint)
        ConstraintLayout layout;

        @BindView(R.id.text_name)
        TextView nameView;

        @BindView(R.id.text_japan_name)
        TextView secondName;

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

        @BindView(R.id.button_online)
        Button buttonOnline;

        @BindView(R.id.btn_related)
        Button relatedBtn;

        @BindView(R.id.btn_attach)
        Button linksBtn;

        @BindView(R.id.image_add)
        ImageView addImage;

        private GenresAdapter adapter;
        private int textColor;
        private Drawable addEmpty;
        private Drawable add;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            textColor = AttributesHelper.withContext(itemView.getContext())
                    .getColor(R.attr.colorText);

            Drawable card = DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.card)
                    .withAttributeColor(R.attr.colorBackgroundContent)
                    .tint()
                    .get();

            layout.setBackground(card);

            adapter = new GenresAdapter();

            Drawable related = DrawableHelper.withContext(itemView.getContext())
                    .withDrawable(R.drawable.ic_arrange_send_backward)
                    .withAttributeColor(R.attr.colorText)
                    .tint()
                    .get();

            Drawable links = DrawableHelper.withContext(itemView.getContext())
                    .withDrawable(R.drawable.ic_attachment)
                    .withAttributeColor(R.attr.colorText)
                    .tint()
                    .get();

            addEmpty = DrawableHelper.withContext(itemView.getContext())
                    .withDrawable(R.drawable.ic_star_border)
                    .withAttributeColor(R.attr.colorText)
                    .tint()
                    .get();

            add = DrawableHelper.withContext(itemView.getContext())
                    .withDrawable(R.drawable.ic_star_big)
                    .withAttributeColor(R.attr.colorText)
                    .tint()
                    .get();

            relatedBtn.setCompoundDrawablesWithIntrinsicBounds(null, related, null, null);
            linksBtn.setCompoundDrawablesWithIntrinsicBounds(null, links, null, null);
            addImage.setImageDrawable(add);

            genresList.setItemAnimator(new DefaultItemAnimator());
            FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(itemView.getContext());
            flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
            genresList.setLayoutManager(flexboxLayoutManager);
            genresList.setAdapter(adapter);
            genresList.addOnItemTouchListener(new RecyclerItemClickListener(itemView.getContext(), (view, position) ->
                    callback.onAction(AnimeAction.GENRE, adapter.getItemByPosition(position))));

            relatedBtn.setOnClickListener(v -> callback.onAction(AnimeAction.RELATED, null));
            linksBtn.setOnClickListener(v -> callback.onAction(AnimeAction.LINKS, null));
            buttonOnline.setOnClickListener(v -> callback.onAction(AnimeAction.WATCH_ONLINE, null));
        }

        public void bind(AnimeHeadItem item) {
            seasonView.reset();
            typeView.reset();
            statusView.reset();
            genreView.reset();

            seasonView.addSlice(getSliceTitle(itemView.getResources().getString(R.string.common_season)));
            typeView.addSlice(getSliceTitle(itemView.getResources().getString(R.string.common_type)));
            statusView.addSlice(getSliceTitle(itemView.getResources().getString(R.string.common_status)));
            genreView.addSlice(getSliceTitle(itemView.getResources().getString(R.string.common_genre)));

            nameView.setText(item.getName());

            secondName.setText(item.getJpOrEngName());

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
                addImage.setImageDrawable(addEmpty);
            } else {
                addImage.setImageDrawable(add);
            }

            addImage.setOnClickListener(v -> callback.onAction(AnimeAction.ADD_TO_LIST, item.getAnimeRate()));
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
                    .build();
        }
    }
}
