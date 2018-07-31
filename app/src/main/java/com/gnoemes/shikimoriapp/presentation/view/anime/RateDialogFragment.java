package com.gnoemes.shikimoriapp.presentation.view.anime;

import android.app.Dialog;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.afollestad.materialdialogs.MaterialDialog;
import com.arellomobile.mvp.MvpAppCompatDialogFragment;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
import com.gnoemes.shikimoriapp.entity.main.domain.Constants;
import com.gnoemes.shikimoriapp.entity.rates.domain.RateStatus;
import com.gnoemes.shikimoriapp.entity.rates.domain.UserRate;
import com.gnoemes.shikimoriapp.presentation.view.anime.provider.RateResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.anime.provider.RateResourceProviderImpl;
import com.gnoemes.shikimoriapp.utils.view.AttributesHelper;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.mpt.android.stv.Slice;
import com.mpt.android.stv.SpannableTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RateDialogFragment extends MvpAppCompatDialogFragment {

    @BindView(R.id.rates)
    Spinner rates;

    @BindView(R.id.episodes_content)
    EditText episodes;

    @BindView(R.id.rewatches_content)
    EditText reWatches;

    @BindView(R.id.rating_title)
    SpannableTextView ratingTitle;

    @BindView(R.id.rating)
    RatingBar ratingBar;

    @BindView(R.id.comment_content)
    EditText comment;

    private RateResourceProvider resourceProvider;
    private RateDialogCallback callback;

    @Nullable
    private UserRate rate;

    private int textColor;
    private RateStatus status;

    public static RateDialogFragment newInstance(@Nullable UserRate rate) {
        RateDialogFragment fragment = new RateDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(AppExtras.ARGUMENT_ANIME_RATE, rate);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.dialog_rate, null);
        ButterKnife.bind(this, view);
        resourceProvider = new RateResourceProviderImpl(getContext());

        if (getArguments() != null) {
            rate = (UserRate) getArguments().getSerializable(AppExtras.ARGUMENT_ANIME_RATE);
        }
        initViews();

        MaterialDialog.Builder builder = new MaterialDialog.Builder(view.getContext())
                .customView(view, true)
                .titleColorAttr(R.attr.colorText)
                .dividerColor(R.attr.colorAccent)
                .backgroundColorAttr(R.attr.colorBackgroundWindow)
                .buttonRippleColorAttr(R.attr.colorAccentTransparent)
                .positiveText(R.string.common_save)
                .positiveColorAttr(R.attr.colorAction)
                .negativeText(R.string.common_cancel)
                .negativeColorAttr(R.attr.colorAction)
                .autoDismiss(true)
                .canceledOnTouchOutside(true)
                .onPositive((dialog, which) -> {
                    if (callback != null) {
                        long id = Constants.NO_ID;
                        if (rate != null) {
                            id = rate.getId();
                        }

                        rate = convertUserRate(id);
                        callback.onSaveAnimeRate(rate);
                    }
                })
                .onNegative((dialog, which) -> dismiss());

        if (rate != null) {
            builder.neutralText(R.string.common_delete)
                    .neutralColorAttr(R.attr.colorAction)
                    .onNeutral((dialog, which) -> {
                        if (callback != null) {
                            callback.onDeleteAnimeRate(rate.getId());
                        }
                    });
        }


        return builder.build();
    }

    private UserRate convertUserRate(long id) {
        String score = String.valueOf(Math.round(ratingBar.getRating() * 2));
        String text = comment.getText().toString();
        String episodesText = episodes.getText().toString();
        String reWatchesText = reWatches.getText().toString();


        return new UserRate(id,
                status,
                score,
                TextUtils.isEmpty(text) ? null : text,
                TextUtils.isEmpty(episodesText) ? null : episodesText,
                TextUtils.isEmpty(reWatchesText) ? null : reWatchesText);
    }

    public void setCallback(RateDialogCallback callback) {
        this.callback = callback;
    }

    private void initViews() {
        rates.setAdapter(new ArrayAdapter<>(getContext(), R.layout.item_spinner_small, resourceProvider.getRateStasuses()));

        Drawable rateBackground = DrawableHelper
                .withContext(getContext())
                .withDrawable(rates.getBackground())
                .withAttributeColor(R.attr.colorAccent)
                .tint()
                .get();
        rates.setBackground(rateBackground);

        Drawable editTextBackground = DrawableHelper
                .withContext(getContext())
                .withDrawable(episodes.getBackground())
                .withAttributeColor(R.attr.colorAccent)
                .tint()
                .get();

        episodes.setBackground(editTextBackground);
        reWatches.setBackground(editTextBackground);

        Drawable commentBackground = DrawableHelper
                .withContext(getContext())
                .withDrawable(R.drawable.language_background)
                .withAttributeColor(R.attr.colorBackgroundContent)
                .tint()
                .get();

        textColor = AttributesHelper
                .withContext(getContext())
                .getColor(R.attr.colorText);

        comment.setBackground(commentBackground);

        comment.setText(rate == null ? null : rate.getText());

        episodes.setText(rate == null ? null : String.valueOf(rate.getEpisodes()));
        reWatches.setText(rate == null ? null : String.valueOf(rate.getRewatches()));

        ratingTitle.addSlice(getRatingTitleSlice());
        ratingTitle.addSlice(getRatingSlice(rate == null ? "0" : rate.getScore()));

        ratingBar.setRating(rate == null ? 0 : TextUtils.isDigitsOnly(rate.getScore()) ? Float.parseFloat(rate.getScore()) / 2 : 0);
        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            ratingTitle.replaceSliceAt(1, RateDialogFragment.this.getRatingSlice(String.valueOf(Math.round(rating * 2))));
            ratingTitle.display();
        });

        rates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        status = RateStatus.WATCHING;
                        break;
                    case 1:
                        status = RateStatus.PLANNED;
                        break;
                    case 2:
                        status = RateStatus.REWATCHING;
                        break;
                    case 3:
                        status = RateStatus.COMPLETED;
                        break;
                    case 4:
                        status = RateStatus.ON_HOLD;
                        break;
                    case 5:
                        status = RateStatus.DROPPED;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ratingTitle.display();

        if (rate != null) {
            rates.setSelection(convertStatus(rate.getStatus()));
        }
    }

    private int convertStatus(RateStatus status) {
        switch (status) {
            case WATCHING:
                return 0;
            case PLANNED:
                return 1;
            case REWATCHING:
                return 2;
            case COMPLETED:
                return 3;
            case ON_HOLD:
                return 4;
            case DROPPED:
                return 5;
        }
        return 0;
    }

    private Slice getRatingTitleSlice() {
        return new Slice.Builder(getResources().getString(R.string.rate_rating).concat(" "))
                .style(Typeface.BOLD)
                .textColor(textColor)
                .setSliceId(0)
                .build();
    }

    private Slice getRatingSlice(String rate) {
        return new Slice.Builder(String.format("%s/10", rate))
                .style(Typeface.BOLD)
                .textColor(textColor)
                .setSliceId(1)
                .build();
    }

    public interface RateDialogCallback {
        void onSaveAnimeRate(UserRate rate);

        void onDeleteAnimeRate(long id);
    }

}
