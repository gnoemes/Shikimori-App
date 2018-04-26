package com.gnoemes.shikimoriapp.presentation.view.anime;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeGenre;
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.AnimePresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.GenresAdapter;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.AttributesHelper;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.gnoemes.shikimoriapp.utils.view.RecyclerItemClickListener;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.mpt.android.stv.Slice;
import com.mpt.android.stv.SpannableTextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Fragment of anime detail information
 */
public class AnimeFragment extends BaseFragment<AnimePresenter, AnimeView> implements AnimeView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.image_background)
    ImageView backgroundImage;

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

    @BindView(R.id.text_description)
    TextView descriptionView;

    @BindView(R.id.text_desctiption_title)
    SpannableTextView desctiptionTitleView;

    @BindView(R.id.image_add)
    ImageView addImage;

    @BindView(R.id.btn_related)
    Button relatedBtn;

    @BindView(R.id.btn_attach)
    Button linksBtn;

    @BindView(R.id.btn_comments)
    Button commentsBtn;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Inject
    ImageLoader imageLoader;

    @InjectPresenter
    AnimePresenter presenter;

    private int textColor;
    private GenresAdapter adapter;

    public static AnimeFragment newInstance(long animeId) {
        Bundle args = new Bundle();
        AnimeFragment fragment = new AnimeFragment();
        args.putLong(AppExtras.ARGUMENT_ANIME_ID, animeId);
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    AnimePresenter provideAnimePresenter() {
        presenter = presenterProvider.get();
        if (getParentFragment() != null) {
            presenter.setLocalRouter(((RouterProvider) getParentFragment()).getLocalRouter());
        }
        if (getArguments() != null) {
            presenter.setAnimeId(getArguments().getLong(AppExtras.ARGUMENT_ANIME_ID));
        }
        return presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getFragmentLayout(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        textColor = AttributesHelper.withContext(getContext())
                .getColor(R.attr.colorText);

        seasonView.addSlice(getSliceTitle(getString(R.string.common_season)));
        typeView.addSlice(getSliceTitle(getString(R.string.common_type)));
        statusView.addSlice(getSliceTitle(getString(R.string.common_status)));
        genreView.addSlice(getSliceTitle(getString(R.string.common_genre)));
        desctiptionTitleView.addSlice(getSliceTitle(getString(R.string.common_description)));

        Drawable related;
        Drawable links;
        Drawable add;
        related = DrawableHelper.withContext(getContext())
                .withDrawable(R.drawable.ic_arrange_send_backward)
                .withAttributeColor(R.attr.colorText)
                .tint()
                .get();

        links = DrawableHelper.withContext(getContext())
                .withDrawable(R.drawable.ic_attachment)
                .withAttributeColor(R.attr.colorText)
                .tint()
                .get();

        add = DrawableHelper.withContext(getContext())
                .withDrawable(R.drawable.ic_star_border)
                .withAttributeColor(R.attr.colorText)
                .tint()
                .get();

        relatedBtn.setCompoundDrawablesWithIntrinsicBounds(null, related, null, null);
        linksBtn.setCompoundDrawablesWithIntrinsicBounds(null, links, null, null);
        addImage.setImageDrawable(add);

        adapter = new GenresAdapter();
        genresList.setItemAnimator(new DefaultItemAnimator());
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getContext());
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        genresList.setLayoutManager(flexboxLayoutManager);
        genresList.setAdapter(adapter);
        genresList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (view, position) -> getPresenter().onGenreClick(adapter.getItemByPosition(position))));

        addImage.setOnClickListener(v -> getPresenter().onAddListClick());
        relatedBtn.setOnClickListener(v -> getPresenter().onRelatedClicked());
        linksBtn.setOnClickListener(v -> getPresenter().onLinksClicked());
        commentsBtn.setOnClickListener(v -> getPresenter().onCommentsClicked());
        buttonOnline.setOnClickListener(v -> getPresenter().onOnlineClicked());
    }

    /**
     * Returns slice for title (e.g. <b>Genre:</b>)
     *
     * @param text
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
     * @param text
     * @return Slice content
     */
    private Slice getSliceContent(String text) {
        return new Slice.Builder(text)
                .textColor(textColor)
                .build();
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected AnimePresenter getPresenter() {
        return presenter;
    }

    @Override
    @LayoutRes
    protected int getFragmentLayout() {
        return R.layout.fragment_anime;
    }

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Init toolbar and change color of menu icon to a theme
     */
    @Override
    public void initToolbar() {
        collapsingToolbarLayout.setCollapsedTitleTextColor(textColor);
        Drawable navigationIcon = DrawableHelper.withContext(getContext())
                .withDrawable(R.drawable.ic_arrow_back)
                .withAttributeColor(R.attr.colorText)
                .tint()
                .get();

        toolbar.setNavigationIcon(navigationIcon);
        toolbar.setNavigationOnClickListener(v -> getPresenter().onBackPressed());
        toolbar.inflateMenu(R.menu.menu_anime);

        Drawable overFlowIcon = toolbar.getOverflowIcon();
        overFlowIcon = DrawableHelper.withContext(getContext())
                .withDrawable(overFlowIcon)
                .withAttributeColor(R.attr.colorText)
                .tint()
                .get();

        toolbar.setOverflowIcon(overFlowIcon);
        progressBar.setIndeterminate(true);
    }

    @Override
    public void onShowLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideLoading() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void setImage(String imageUrl) {
        imageLoader.setImageWithFit(backgroundImage, imageUrl);
    }

    @Override
    public void setName(String name) {
        nameView.setText(name);
        toolbar.setTitle(name);
    }


    @Override
    public void setSecondName(String jpOrEngName) {
        secondName.setText(jpOrEngName);
    }

    @Override
    public void setSeason(String season) {
        seasonView.addSlice(getSliceContent(season));
        seasonView.display();
    }

    @Override
    public void setType(String animeType) {
        typeView.addSlice(getSliceContent(animeType));
        typeView.display();
    }

    @Override
    public void setStatus(String animeStatus) {
        statusView.addSlice(getSliceContent(animeStatus));
        statusView.display();
    }

    @Override
    public void setGenres(List<AnimeGenre> genres) {
        genreView.display();
        adapter.bindItems(genres);
    }

    @Override
    public void setScore(float score) {
        ratingBar.setRating(score / 2);
        ratingValue.setText(String.valueOf(score));
    }

    @Override
    public void setDescription(String description) {
        desctiptionTitleView.display();
        descriptionView.setText(description);
    }
}
