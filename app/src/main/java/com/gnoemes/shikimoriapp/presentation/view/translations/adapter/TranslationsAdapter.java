package com.gnoemes.shikimoriapp.presentation.view.translations.adapter;

import android.content.Context;
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
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.TranslationViewModel;
import com.gnoemes.shikimoriapp.utils.Utils;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TranslationsAdapter extends RecyclerView.Adapter<TranslationsAdapter.ViewHolder> {

    private List<TranslationViewModel> items = new ArrayList<>();
    private TranslationItemCallback callback;

    public TranslationsAdapter(@NonNull TranslationItemCallback callback) {
        this.callback = callback;
    }

    public void bindItems(List<TranslationViewModel> viewModels) {
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

        @BindView(R.id.text_hosting)
        TextView hosting;

        @BindView(R.id.image_menu)
        ImageView menu;

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

        public void bind(TranslationViewModel translation) {
            layout.setOnClickListener(null);
            layout.setOnLongClickListener(null);

            String type = translation.getType() == null ? "" : translation.getType().getType();
            String title = String.format("%s %s", Utils.firstUpperCase(type), translation.getAuthor());

            if (Utils.isAwailableForDownload(translation.getHosting())) {
                menu.setVisibility(View.VISIBLE);
            } else {
                menu.setVisibility(View.GONE);
            }

            titleText.setText(title);
            hosting.setText(translation.getHosting().getSynonymType());
            menu.setOnClickListener(v -> showPopup(translation));
            layout.setOnLongClickListener(v -> {
                callback.onDownloadTranslation(translation);
                return true;
            });
            layout.setOnClickListener(v -> callback.onTranslationClicked(translation));
        }

        private void showPopup(TranslationViewModel translation) {
            Context wrapper = new ContextThemeWrapper(itemView.getContext(), R.style.PopupMenuTheme);
            PopupMenu popupMenu = new PopupMenu(wrapper, menu);
            popupMenu.inflate(R.menu.menu_translation);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.item_download:
                        callback.onDownloadTranslation(translation);
                        break;
                }
                return false;
            });
            itemView.post(popupMenu::show);

        }
    }
}
