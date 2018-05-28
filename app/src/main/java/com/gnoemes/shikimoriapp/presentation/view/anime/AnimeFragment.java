package com.gnoemes.shikimoriapp.presentation.view.anime;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.PagerAdapter;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeDetailsViewModel;
import com.gnoemes.shikimoriapp.entity.anime.presentation.AnimeLinkViewModel;
import com.gnoemes.shikimoriapp.entity.anime.presentation.delegate.BaseEpisodeItem;
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.rates.domain.UserRate;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.AnimePresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.anime.converter.AnimeDetailsViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.anime.adapter.anime.AnimeAdapter;
import com.gnoemes.shikimoriapp.presentation.view.anime.adapter.comments.CommentsAdapter;
import com.gnoemes.shikimoriapp.presentation.view.anime.adapter.episodes.EpisodeAdapter;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.AttributesHelper;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.gnoemes.shikimoriapp.utils.view.LinearStickyHead;
import com.gnoemes.shikimoriapp.utils.view.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class AnimeFragment extends BaseFragment<AnimePresenter, AnimeView>
        implements AnimeView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.image_background)
    ImageView backgroundImage;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

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

    private AnimePagerAdapter pagerAdapter;

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

    private void initViews() {
        EpisodeAdapter episodeAdapter = new EpisodeAdapter(item -> getPresenter().onEpisodeClicked(item),
                (action, item) -> getPresenter().onEpisodeOptionAction(action, item));
        AnimeAdapter animeAdapter = new AnimeAdapter((action, data) -> getPresenter().onAction(action, data));
        CommentsAdapter commentsAdapter = new CommentsAdapter(imageLoader,
                id -> getPresenter().onUserClicked(id));

        pagerAdapter = new AnimePagerAdapter(commentsAdapter, animeAdapter, episodeAdapter);
        viewPager.setAdapter(pagerAdapter);

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
        toolbar.inflateMenu(R.menu.menu_anime);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_settings:
                    showSettingsWizard(false);
                    break;
                case R.id.item_open:
                    getPresenter().onOpenBrowserClicked();
                    break;
            }
            return false;
        });

        Drawable overFlowIcon = toolbar.getOverflowIcon();
        overFlowIcon = DrawableHelper.withContext(getContext())
                .withDrawable(overFlowIcon)
                .withAttributeColor(R.attr.colorText)
                .tint()
                .get();

        toolbar.setOverflowIcon(overFlowIcon);
        progressBar.setSecondaryProgress(R.color.red);
        progressBar.setIndeterminate(true);
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
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setAnimeData(AnimeDetailsViewModel model) {
        imageLoader.setImageWithFit(backgroundImage, model.getImageUrl());
        toolbar.setTitle(model.getName());
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
    public void showSettingsWizard(boolean loadEpisode) {
        EpisodeWizardDialogFragment dialog = EpisodeWizardDialogFragment.newInstance();
        dialog.setCallback((type, chooseSettings, playerType, isAlways) ->
                getPresenter().onSettingsSelected(loadEpisode, type, chooseSettings, playerType, isAlways));
        dialog.show(getChildFragmentManager(), "WIZARD");
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
                .negativeText(R.string.common_cancel)
                .onNegative((dialog, which) -> dialog.dismiss())
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

    ///////////////////////////////////////////////////////////////////////////
    // INNER CLASS
    ///////////////////////////////////////////////////////////////////////////

    class AnimePagerAdapter extends PagerAdapter {


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

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater inflater = LayoutInflater.from(container.getContext());
            ViewGroup layout = (ViewGroup) inflater.inflate(screens.get(position), container, false);
            container.addView(layout);
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


        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        private void createCommentsPage(ViewGroup layout) {
            commentsList = layout.findViewById(R.id.list_comments);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            commentsList.setLayoutManager(layoutManager);
            commentsList.setAdapter(commentsAdapter);
            commentsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    //TODO fix endless scroll listener (layoutManager.findLastCompletelyVisibleItemPosition returns -1)
//                    int visibleItemPosition = layoutManager.findLastVisibleItemPosition();
                    int visibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                    int itemCount = layoutManager.getItemCount() - 1;

                    if (visibleItemPosition >= itemCount) {
                        getPresenter().loadNextPage();
                    }

                }
            });
        }


        private void createAnimePage(ViewGroup layout) {
            animeDetailsList = layout.findViewById(R.id.anime_details_list);
            animeDetailsList.setItemAnimator(new DefaultItemAnimator());
            animeDetailsList.setAdapter(animeAdapter);
            animeDetailsList.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        private void createSeriesPage(ViewGroup layout) {
            refreshLayout = layout.findViewById(R.id.refresh_layout);
            seriesList = layout.findViewById(R.id.list);
            refreshLayout.setOnRefreshListener(() -> getPresenter().onEpisodesRefresh());
            seriesList.setLayoutManager(new LinearStickyHead<EpisodeAdapter>(getContext()));
            int margin = (int) getResources().getDimension(R.dimen.margin_small);
            seriesList.addItemDecoration(new VerticalSpaceItemDecoration(margin));
            seriesList.setAdapter(episodeAdapter);
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
