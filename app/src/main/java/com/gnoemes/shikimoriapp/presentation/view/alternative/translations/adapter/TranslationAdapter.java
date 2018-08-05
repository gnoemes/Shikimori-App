package com.gnoemes.shikimoriapp.presentation.view.alternative.translations.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.AlternativeTranslationViewModel;
import com.gnoemes.shikimoriapp.entity.series.domain.VideoHosting;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TranslationAdapter extends RecyclerView.Adapter<TranslationAdapter.ViewHolder> {

    private List<AlternativeTranslationViewModel> items = new ArrayList<>();
    private TranslationItemCallback callback;

    public TranslationAdapter(@NonNull TranslationItemCallback callback) {
        this.callback = callback;
    }

    public void bindItems(List<AlternativeTranslationViewModel> viewModels) {
        items.clear();
        items.addAll(viewModels);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_translation, parent, false);
        return new ViewHolder(view);
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

        @BindView(R.id.constraint)
        ConstraintLayout layout;

        @BindView(R.id.text_title)
        TextView titleText;

//        @BindView(R.id.label_quality)
//        TextView qualityLabel;

        @BindView(R.id.text_hosting)
        TextView hosting;

        private Drawable tv;
        private Drawable dvd;
        private Drawable bd;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            tv = DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.language_background)
                    .withColor(R.color.bittersweet)
                    .tint()
                    .get();

            dvd = DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.language_background)
                    .withColor(R.color.colorAccentInverse)
                    .tint()
                    .get();

            bd = DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.language_background)
                    .withColor(R.color.light_teal)
                    .tint()
                    .get();

            Drawable card = DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.card)
                    .withAttributeColor(R.attr.colorBackgroundContent)
                    .tint()
                    .get();

            layout.setBackground(card);
        }

        public void bind(AlternativeTranslationViewModel translation) {
            layout.setOnClickListener(null);

            titleText.setText(translation.getTitle());

            hosting.setText(VideoHosting.SMOTRET_ANIME.getSynonymType());

//            switch (translation.getQuality()) {
//                case TV:
//                    qualityLabel.setText(R.string.quality_tv);
//                    qualityLabel.setBackground(tv);
//                    break;
//                case DVD:
//                    qualityLabel.setText(R.string.quality_dvd);
//                    qualityLabel.setBackground(dvd);
//                    break;
//                case BD:
//                    qualityLabel.setText(R.string.quality_bd);
//                    qualityLabel.setBackground(bd);
//                    break;
//            }

            layout.setOnClickListener(v -> callback.onTranslationClicked(translation));
        }
    }
}
