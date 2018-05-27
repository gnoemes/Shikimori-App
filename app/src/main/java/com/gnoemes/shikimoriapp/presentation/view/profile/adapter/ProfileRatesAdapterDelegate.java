package com.gnoemes.shikimoriapp.presentation.view.profile.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.BaseProfileItem;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.ProfileAction;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.ProfileRatesInfoItem;
import com.gnoemes.shikimoriapp.presentation.view.common.widget.RateProgressView;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileRatesAdapterDelegate extends AdapterDelegate<List<BaseProfileItem>> {

    private ProfileCallback callback;

    public ProfileRatesAdapterDelegate(ProfileCallback callback) {
        this.callback = callback;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseProfileItem> items, int position) {
        return items.get(position) instanceof ProfileRatesInfoItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_profile_rates_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseProfileItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ViewHolder viewHolder = (ViewHolder) holder;
        ProfileRatesInfoItem item = (ProfileRatesInfoItem) items.get(position);
        viewHolder.bind(item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint)
        ConstraintLayout layout;
        @BindView(R.id.rate_progress)
        RateProgressView progressView;
        @BindView(R.id.spinner)
        Spinner spinner;
        @BindView(R.id.placeholder)
        TextView placeholder;

        public ViewHolder(View itemVIew) {
            super(itemVIew);
            ButterKnife.bind(this, itemVIew);

            Drawable card = DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.card)
                    .withAttributeColor(R.attr.colorBackgroundContent)
                    .tint()
                    .get();

            layout.setBackground(card);

            Drawable spinnerBack = DrawableHelper
                    .withContext(itemVIew.getContext())
                    .withDrawable(spinner.getBackground())
                    .withAttributeColor(R.attr.colorAccent)
                    .tint()
                    .get();

            spinner.setBackground(spinnerBack);
        }

        public void bind(ProfileRatesInfoItem item) {
            layout.setVisibility(View.VISIBLE);

            if (item.getWatchedStatus() == null && item.getInProgressStatus() == null
                    && item.getDroppedStatus() == null) {
                String rates = itemView.getResources().getString(R.string.common_anime_rates);
                spinner.setAdapter(new ArrayAdapter<>(itemView.getContext(), R.layout.item_spinner_normal_transparent, Collections.singletonList(rates)));
                spinner.setEnabled(false);
                placeholder.setVisibility(View.VISIBLE);
            } else {
                placeholder.setVisibility(View.GONE);
                spinner.setEnabled(true);
                spinner.setAdapter(new ArrayAdapter<>(itemView.getContext(), R.layout.item_spinner_normal_transparent, item.getStatuses()));
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != 0) {
                            callback.onAction(ProfileAction.RATES, -1);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                if (item.getWatchedStatus() != null) {
                    progressView.addRate(item.getWatchedStatus().getStatus(), item.getWatchedStatus().getSize());
                }

                if (item.getInProgressStatus() != null) {
                    progressView.addRate(item.getInProgressStatus().getStatus(), item.getInProgressStatus().getSize());
                }

                if (item.getDroppedStatus() != null) {
                    progressView.addRate(item.getDroppedStatus().getStatus(), item.getDroppedStatus().getSize());
                }

                progressView.setOnClickListener(v -> callback.onAction(ProfileAction.RATES, -1));
            }
        }
    }
}
