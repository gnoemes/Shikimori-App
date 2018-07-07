package com.gnoemes.shikimoriapp.presentation.view.anime;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.transition.Fade;
import android.support.transition.TransitionManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeFranchiseNode;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeAction;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeDetailsPage;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeDetailsViewModel;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeLinkViewModel;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.BaseEpisodeItem;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.rates.domain.UserRate;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.AnimePresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.converter.AnimeDetailsViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.anime.adapter.anime.AnimeAdapter;
import com.gnoemes.shikimoriapp.presentation.view.anime.adapter.anime.AnimeCharacterAdapter;
import com.gnoemes.shikimoriapp.presentation.view.anime.adapter.comments.CommentsAdapter;
import com.gnoemes.shikimoriapp.presentation.view.anime.adapter.episodes.EpisodeAdapter;
import com.gnoemes.shikimoriapp.presentation.view.anime.converter.AnimeFranchiseNodeToStringConverter;
import com.gnoemes.shikimoriapp.presentation.view.anime.provider.RateResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.utils.Utils;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.AttributesHelper;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.gnoemes.shikimoriapp.utils.view.LinearStickyHead;
import com.gnoemes.shikimoriapp.utils.view.VerticalSpaceItemDecoration;
import com.gnoemes.shikimoriapp.utils.view.ViewStatePagerAdapter;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class AnimeFragment extends BaseFragment<AnimePresenter, AnimeView>
        implements AnimeView {

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout layout;

    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.image_background)
    ImageView backgroundImage;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.subtitle)
    TextView subtitleView;

    @BindView(R.id.progress_loading)
    ProgressBar progressBar;

    @InjectPresenter
    AnimePresenter presenter;

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

    @Inject
    ImageLoader imageLoader;

    @Inject
    AnimeDetailsViewModelConverter converter;

    @Inject
    AnimeFranchiseNodeToStringConverter franchiseConverter;

    @Inject
    RateResourceProvider rateResourceProvider;

    private MaterialDialog translationTypeDialog;
    private AnimePagerAdapter pagerAdapter;
    private boolean isCommentsPage;

    public static AnimeFragment newInstance(long animeId) {
        Bundle args = new Bundle();
        AnimeFragment fragment = new AnimeFragment();
        args.putLong(AppExtras.ARGUMENT_ANIME_ID, animeId);
        fragment.setArguments(args);
        return fragment;
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

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            isCommentsPage = AnimeDetailsPage.COMMENTS.isEqualPage(position);
        }
    };
    private AppBarLayout.OnOffsetChangedListener onOffsetChangedListener = new AppBarLayout.OnOffsetChangedListener() {
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            subtitleView.setAlpha((float) (1 - Math.abs(verticalOffset / 1.5 / 100)));
        }
    };

    @Override
    public void onDestroyView() {
        viewPager.setAdapter(null);
        viewPager.removeOnPageChangeListener(pageChangeListener);
        appBarLayout.removeOnOffsetChangedListener(onOffsetChangedListener);
        backgroundImage.setOnClickListener(null);
        super.onDestroyView();
    }

    private void initViews() {
        AnimeCharacterAdapter characterAdapter = new AnimeCharacterAdapter(imageLoader, id -> getPresenter().onAction(AnimeAction.CHARACTER, id));
        EpisodeAdapter episodeAdapter = new EpisodeAdapter(item -> getPresenter().onEpisodeClicked(item),
                (action, item) -> getPresenter().onEpisodeOptionAction(action, item));
        AnimeAdapter animeAdapter = new AnimeAdapter(rateResourceProvider, characterAdapter, (action, data) -> getPresenter().onAction(action, data));
        CommentsAdapter commentsAdapter = new CommentsAdapter(imageLoader,
                id -> getPresenter().onUserClicked(id));

        pagerAdapter = new AnimePagerAdapter(commentsAdapter, animeAdapter, episodeAdapter);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(pageChangeListener);

        int textColor = AttributesHelper.withContext(getContext())
                .getColor(R.attr.colorText);

        collapsingToolbarLayout.setCollapsedTitleTextColor(textColor);
        Drawable navigationIcon = DrawableHelper.withContext(getContext())
                .withDrawable(R.drawable.ic_arrow_back)
                .withAttributeColor(R.attr.colorText)
                .tint()
                .get();

        toolbar.setNavigationIcon(navigationIcon);
        toolbar.setNavigationOnClickListener(v -> getPresenter().onBackPressed());

        backgroundImage.setOnClickListener(v -> getPresenter().onBackgroundImageClicked());
        appBarLayout.addOnOffsetChangedListener(onOffsetChangedListener);
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected AnimePresenter getPresenter() {
        return presenter;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_details_page;
    }

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onShowLoading() {
        TransitionManager.beginDelayedTransition(layout, new Fade());
        progressBar.setVisibility(View.VISIBLE);
        appBarLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);
    }

    @Override
    public void onHideLoading() {
        TransitionManager.beginDelayedTransition(layout, new Fade());
        progressBar.setVisibility(View.GONE);
        appBarLayout.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);
    }

    @Override
    public void setAnimeData(AnimeDetailsViewModel model) {
        imageLoader.setImageWithFit(backgroundImage, model.getImageUrl());
        collapsingToolbarLayout.setTitle(model.getName());
        subtitleView.setText(model.getJpOrEngName());
        pagerAdapter.setData(converter.convertFromViewModel(model));
    }

    @Override
    public void setPage(int position) {
        viewPager.setCurrentItem(position, true);
    }

    @Override
    public void showEpisodeList(List<BaseEpisodeItem> episodes) {
        pagerAdapter.showEpisodeList(episodes);
    }

    @Override
    public void showComments(List<BaseItem> baseCommentItems) {
        pagerAdapter.showComments(baseCommentItems);
    }

    @Override
    public void insetMoreComments(List<BaseItem> baseCommentItems) {
        pagerAdapter.insertMoreComments(baseCommentItems);
    }

    @Override
    public void onShowPageLoading() {
        pagerAdapter.showPageLoading();
    }

    @Override
    public void onHidePageLoading() {
        pagerAdapter.hidePageLoading();
    }

    @Override
    public void showErrorView() {
        viewPager.setVisibility(View.GONE);
    }

    @Override
    public void hideErrorView() {
        viewPager.setVisibility(View.VISIBLE);
    }

    @Override
    public void onShowRefresh() {
        pagerAdapter.onShowRefresh();
    }

    @Override
    public void onHideRefresh() {
        pagerAdapter.onHideRefresh();
    }

    @Override
    public void showPlayWizard(List<TranslationType> types) {

        if (translationTypeDialog == null || !translationTypeDialog.isShowing()) {
            String[] translationTypes = new String[types.size()];
            for (int i = 0; i < translationTypes.length; i++) {
                translationTypes[i] = Utils.firstUpperCase(types.get(i).getType());
            }

            translationTypeDialog = new MaterialDialog.Builder(getContext())
                    .dividerColor(R.attr.colorAccent)
                    .items(translationTypes)
                    .itemsColorAttr(R.attr.colorText)
                    .backgroundColorAttr(R.attr.colorBackgroundWindow)
                    .buttonRippleColorAttr(R.attr.colorAccentTransparent)
                    .autoDismiss(true)
                    .canceledOnTouchOutside(true)
                    .itemsCallback((dialog, itemView, position, text) -> {
                        getPresenter().onTranslationTypeChoosed(types.get(position));
                    })
                    .build();

            translationTypeDialog.show();
        }
    }

    @Override
    public void showLinksDialog(List<AnimeLinkViewModel> animeLinkViewModels) {

        List<String> titles = new ArrayList<>();

        for (AnimeLinkViewModel model : animeLinkViewModels) {
            titles.add(model.getName());
        }

        new MaterialDialog.Builder(getContext())
                .title(R.string.common_links)
                .items(titles.toArray(new CharSequence[titles.size()]))
                .itemsCallback((dialog, itemView, position, text) -> {
                    dialog.dismiss();
                    AnimeFragment.this.getPresenter().onLinkPressed(animeLinkViewModels.get(position));
                })
                .autoDismiss(true)
                .titleColorAttr(R.attr.colorText)
                .contentColorAttr(R.attr.colorText)
                .alwaysCallSingleChoiceCallback()
                .backgroundColorAttr(R.attr.colorBackgroundWindow)
                .autoDismiss(false)
                .negativeColorAttr(R.attr.colorAction)
                .negativeText(R.string.close)
                .onNegative((dialog, which) -> dialog.dismiss())
                .canceledOnTouchOutside(true)
                .build()
                .show();
    }

    @Override
    public void showChronologyDialog(List<AnimeFranchiseNode> nodes) {

        List<String> items = franchiseConverter.convertList(nodes);

        new MaterialDialog.Builder(getContext())
                .title(R.string.chronology)
                .items(items.toArray(new CharSequence[items.size()]))
                .itemsCallback((dialog, itemView, position, text) -> {
                    dialog.dismiss();
                    if (nodes != null && !nodes.isEmpty()) {
                        getPresenter().onAnimeClicked(nodes.get(position).getId());
                    }
                })
                .autoDismiss(true)
                .titleColorAttr(R.attr.colorText)
                .contentColorAttr(R.attr.colorText)
                .alwaysCallSingleChoiceCallback()
                .backgroundColorAttr(R.attr.colorBackgroundWindow)
                .negativeText(R.string.close)
                .negativeColorAttr(R.attr.colorAction)
                .canceledOnTouchOutside(true)
                .build()
                .show();
    }

    @Override
    public void showRatesDialog(UserRate data) {
        RateDialogFragment dialog = RateDialogFragment.newInstance(data);
        dialog.setCallback(new RateDialogFragment.RateDialogCallback() {
            @Override
            public void onSaveAnimeRate(UserRate rate) {
                getPresenter().onSaveRate(rate);
            }

            @Override
            public void onDeleteAnimeRate(long id) {
                getPresenter().onDeleteRate(id);
            }
        });
        dialog.show(getChildFragmentManager(), "RATES");
    }

    @Override
    public void showClearHistoryDialog() {
        new MaterialDialog.Builder(getContext())
                .content(R.string.clear_episodes_content)
                .autoDismiss(true)
                .titleColorAttr(R.attr.colorText)
                .contentColorAttr(R.attr.colorText)
                .alwaysCallSingleChoiceCallback()
                .backgroundColorAttr(R.attr.colorBackgroundWindow)
                .negativeColorAttr(R.attr.colorAction)
                .negativeText(R.string.common_cancel)
                .onNegative((dialog, which) -> dialog.dismiss())
                .positiveColorAttr(R.attr.colorAction)
                .positiveText(R.string.yes)
                .onPositive((dialog, which) -> getPresenter().onClearHistory())
                .canceledOnTouchOutside(true)
                .build()
                .show();
    }

    ///////////////////////////////////////////////////////////////////////////
    // INNER CLASS
    ///////////////////////////////////////////////////////////////////////////

    public class AnimePagerAdapter extends ViewStatePagerAdapter {


        private final List<Integer> screens = Arrays.asList(
                R.layout.fragment_comments,
                R.layout.fragment_anime,
                R.layout.fragment_series);
        private CommentsAdapter commentsAdapter;
        private AnimeAdapter animeAdapter;
        private EpisodeAdapter episodeAdapter;

        private RecyclerView animeDetailsList;
        private RecyclerView seriesList;
        private RecyclerView commentsList;

        private SwipeRefreshLayout refreshLayout;

        AnimePagerAdapter(CommentsAdapter commentsAdapter, AnimeAdapter animeAdapter, EpisodeAdapter episodeAdapter) {
            this.commentsAdapter = commentsAdapter;
            this.animeAdapter = animeAdapter;
            this.episodeAdapter = episodeAdapter;
        }

        @Override
        public int getCount() {
            return screens.size();
        }

        @Override
        protected View createView(ViewGroup container, int position) {
            LayoutInflater inflater = LayoutInflater.from(container.getContext());
            ViewGroup layout = (ViewGroup) inflater.inflate(screens.get(position), container, false);
            switch (position) {
                case 0:
                    createCommentsPage(layout);
                    break;
                case 1:
                    createAnimePage(layout);
                    break;
                case 2:
                    createSeriesPage(layout);
                    break;
            }
            return layout;
        }

        private void createCommentsPage(ViewGroup layout) {
            commentsList = layout.findViewById(R.id.list_comments);
            LinearLayoutManager layoutManager = new LinearLayoutManager(layout.getContext());
            commentsList.setLayoutManager(layoutManager);
            commentsList.setAdapter(commentsAdapter);
            commentsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    //TODO fix endless scroll listener (layoutManager.findLastCompletelyVisibleItemPosition returns -1)
                    int visibleItemPosition = layoutManager.findLastVisibleItemPosition();
//                    int visibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                    int itemCount = layoutManager.getItemCount() - 1;

                    if (visibleItemPosition >= itemCount && isCommentsPage) {
                        getPresenter().loadNextPage();
                    }

                }
            });
        }


        private void createAnimePage(ViewGroup layout) {
            animeDetailsList = layout.findViewById(R.id.anime_details_list);
            animeDetailsList.setItemAnimator(new DefaultItemAnimator());
            animeDetailsList.setAdapter(animeAdapter);
            animeDetailsList.setLayoutManager(new LinearLayoutManager(layout.getContext()));
        }

        private void createSeriesPage(ViewGroup layout) {
            refreshLayout = layout.findViewById(R.id.refresh_layout);
            seriesList = layout.findViewById(R.id.list);
            refreshLayout.setOnRefreshListener(() -> getPresenter().onEpisodesRefresh());
            seriesList.setLayoutManager(new LinearStickyHead<EpisodeAdapter>(layout.getContext()));
            int margin = (int) layout.getResources().getDimension(R.dimen.margin_small);
            seriesList.addItemDecoration(new VerticalSpaceItemDecoration(margin));
            seriesList.setAdapter(episodeAdapter);
            if (episodeAdapter.getItems().isEmpty()) {
                refreshLayout.setRefreshing(true);
            }
        }

        public void setData(List<BaseItem> animeItems) {
            animeAdapter.bindItems(animeItems);
        }

        void showEpisodeList(List<BaseEpisodeItem> episodes) {
            episodeAdapter.bindItems(episodes);
        }


        void onHideRefresh() {
            if (refreshLayout != null) {
                refreshLayout.setRefreshing(false);
            }
        }

        void onShowRefresh() {
            if (refreshLayout != null) {
                refreshLayout.setRefreshing(true);
            }
        }

        void showComments(List<BaseItem> baseCommentItems) {
            commentsAdapter.bindItems(baseCommentItems);
        }

        void insertMoreComments(List<BaseItem> baseCommentItems) {
            commentsAdapter.insertMore(baseCommentItems);
        }

        void hidePageLoading() {
            commentsAdapter.hideProgress();
        }

        void showPageLoading() {
            commentsAdapter.showProgress();
        }
    }
}
