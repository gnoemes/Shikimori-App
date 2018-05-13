package com.gnoemes.shikimoriapp.presentation.view.fav;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.rates.domain.RateStatus;
import com.gnoemes.shikimoriapp.entity.rates.presentation.BaseAnimeRateItem;
import com.gnoemes.shikimoriapp.presentation.presenter.fav.FavoritePresenter;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.presentation.view.fav.adapter.AnimeRateAdapter;
import com.gnoemes.shikimoriapp.presentation.view.fav.provider.UserRatesAnimeResourceProvider;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DefaultItemCallback;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.gnoemes.shikimoriapp.utils.view.VerticalSpaceItemDecoration;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class FavoriteFragment extends BaseFragment<FavoritePresenter, FavoriteView>
        implements FavoriteView, DefaultItemCallback {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @InjectPresenter
    FavoritePresenter presenter;
    @Inject
    ImageLoader imageLoader;
    @Inject
    UserRatesAnimeResourceProvider resourceProvider;
    private FavoritePagerAdapter adapter;

    @ProvidePresenter
    FavoritePresenter providePresenter() {
        presenter = presenterProvider.get();
        if (getParentFragment() != null) {
            presenter.setLocalRouter(((RouterProvider) getParentFragment()).getLocalRouter());
        }
        return presenter;
    }

    public static FavoriteFragment newInstance() {
        Bundle args = new Bundle();
        FavoriteFragment fragment = new FavoriteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initPages();
    }

    private void initPages() {

        Drawable watching = getTabDrawable(RateStatus.WATCHING, R.attr.colorAccent);
        Drawable planned = getTabDrawable(RateStatus.PLANNED, R.attr.colorPrimaryDark);
        Drawable reWatching = getTabDrawable(RateStatus.REWATCHING, R.attr.colorPrimaryDark);
        Drawable completed = getTabDrawable(RateStatus.COMPLETED, R.attr.colorPrimaryDark);
        Drawable onHold = getTabDrawable(RateStatus.ON_HOLD, R.attr.colorPrimaryDark);
        Drawable dropped = getTabDrawable(RateStatus.DROPPED, R.attr.colorPrimaryDark);
//        Drawable fav = getTabDrawable(RateStatus.FAVORITE, R.attr.colorPrimaryDark);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getTag() != null) {
                    tab.setIcon(getTabDrawable((RateStatus) tab.getTag(), R.attr.colorAccent));
                    getPresenter().onTabSelected((RateStatus) tab.getTag());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getTag() != null) {
                    tab.setIcon(getTabDrawable((RateStatus) tab.getTag(), R.attr.colorPrimaryDark));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getTag() != null) {
                    getPresenter().onTabSelected((RateStatus) tab.getTag());
                }
            }
        });


        AnimeRateAdapter watchinAdapter = new AnimeRateAdapter(imageLoader, resourceProvider, this);
        AnimeRateAdapter plannedAdapter = new AnimeRateAdapter(imageLoader, resourceProvider, this);
        AnimeRateAdapter reWatchingAdapter = new AnimeRateAdapter(imageLoader, resourceProvider, this);
        AnimeRateAdapter completedAdapter = new AnimeRateAdapter(imageLoader, resourceProvider, this);
        AnimeRateAdapter onHoldAdapter = new AnimeRateAdapter(imageLoader, resourceProvider, this);
        AnimeRateAdapter droppedAdapter = new AnimeRateAdapter(imageLoader, resourceProvider, this);

        adapter = new FavoritePagerAdapter(watchinAdapter, plannedAdapter, reWatchingAdapter,
                completedAdapter, onHoldAdapter, droppedAdapter);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(watching).setTag(RateStatus.WATCHING);
        tabLayout.getTabAt(1).setIcon(planned).setTag(RateStatus.PLANNED);
        tabLayout.getTabAt(2).setIcon(reWatching).setTag(RateStatus.REWATCHING);
        tabLayout.getTabAt(3).setIcon(completed).setTag(RateStatus.COMPLETED);
        tabLayout.getTabAt(4).setIcon(onHold).setTag(RateStatus.ON_HOLD);
        tabLayout.getTabAt(5).setIcon(dropped).setTag(RateStatus.DROPPED);
//        tabLayout.getTabAt(6).setIcon(fav).setTag(RateStatus.FAVORITE);


    }

    private Drawable getTabDrawable(RateStatus status, @AttrRes int color) {
        int drawableRes = 0;

        switch (status) {
            case WATCHING:
                drawableRes = R.drawable.ic_eye;
                break;
            case PLANNED:
                drawableRes = R.drawable.ic_eye_plus;
                break;
            case REWATCHING:
                drawableRes = R.drawable.ic_eye_restart;
                break;
            case COMPLETED:
                drawableRes = R.drawable.ic_eye_checked;
                break;
            case ON_HOLD:
                drawableRes = R.drawable.ic_eye_settings;
                break;
            case DROPPED:
                drawableRes = R.drawable.ic_eye_off;
                break;
            case FAVORITE:
                drawableRes = R.drawable.ic_star;
                break;
        }

        return DrawableHelper
                .withContext(getContext())
                .withDrawable(drawableRes)
                .withAttributeColor(color)
                .tint()
                .get();
    }

    @Override
    public void onItemClick(long id) {
        getPresenter().onItemClicked(id);
    }


    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected FavoritePresenter getPresenter() {
        return presenter;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_favorite;
    }

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void showList(RateStatus status, List<BaseAnimeRateItem> items) {
        adapter.showList(status, items);
    }

    @Override
    public void insertMore(RateStatus status, List<BaseAnimeRateItem> items) {
        adapter.insertMore(status, items);
    }

    @Override
    public void onShowPageLoading(RateStatus status) {
        adapter.showPageLoading(status);
    }

    @Override
    public void onHidePageLoading(RateStatus status) {
        adapter.hidePageLoading(status);
    }

    @Override
    public void showLoading(RateStatus status) {
        adapter.showLoading(status);
    }

    @Override
    public void hideLoading(RateStatus status) {
        adapter.hideLoading(status);
    }

    ///////////////////////////////////////////////////////////////////////////
    // INNER CLASS
    ///////////////////////////////////////////////////////////////////////////

    private class FavoritePagerAdapter extends PagerAdapter {

        private final List<Integer> screens = Arrays.asList(
                R.layout.fragment_fav_page,
                R.layout.fragment_fav_page,
                R.layout.fragment_fav_page,
                R.layout.fragment_fav_page,
                R.layout.fragment_fav_page,
//                R.layout.fragment_fav_page,
                R.layout.fragment_fav_page);
        private final AnimeRateAdapter watchingAdapter;
        private final AnimeRateAdapter plannedAdapter;
        private final AnimeRateAdapter reWatchingAdapter;
        private final AnimeRateAdapter completedAdapter;
        private final AnimeRateAdapter onHoldAdapter;
        private final AnimeRateAdapter droppedAdapter;
        private RecyclerView watchingList;
        private RecyclerView plannedList;
        private RecyclerView reWatchingList;
        private RecyclerView completedList;
        private RecyclerView ohHoldList;
        private RecyclerView droppedList;
        private RecyclerView favList;
        private SwipeRefreshLayout watchingRefresh;
        private SwipeRefreshLayout plannedRefresh;
        private SwipeRefreshLayout reWatchingRefresh;
        private SwipeRefreshLayout completedRefresh;
        private SwipeRefreshLayout onHoldRefresh;
        private SwipeRefreshLayout droppedRefresh;

        public FavoritePagerAdapter(@NonNull AnimeRateAdapter watchingAdapter,
                                    @NonNull AnimeRateAdapter plannedAdapter,
                                    @NonNull AnimeRateAdapter reWatchingAdapter,
                                    @NonNull AnimeRateAdapter completedAdapter,
                                    @NonNull AnimeRateAdapter onHoldAdapter,
                                    @NonNull AnimeRateAdapter droppedAdapter) {
            this.watchingAdapter = watchingAdapter;
            this.plannedAdapter = plannedAdapter;
            this.reWatchingAdapter = reWatchingAdapter;
            this.completedAdapter = completedAdapter;
            this.onHoldAdapter = onHoldAdapter;
            this.droppedAdapter = droppedAdapter;
        }

        @Override
        public int getCount() {
            return screens.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater inflater = LayoutInflater.from(container.getContext());
            ViewGroup layout = (ViewGroup) inflater.inflate(screens.get(position), container, false);
            container.addView(layout);
            switch (position) {
                case 0:
                    createRatePage(RateStatus.WATCHING, layout);
                    break;
                case 1:
                    createRatePage(RateStatus.PLANNED, layout);
                    break;
                case 2:
                    createRatePage(RateStatus.REWATCHING, layout);
                    break;
                case 3:
                    createRatePage(RateStatus.COMPLETED, layout);
                    break;
                case 4:
                    createRatePage(RateStatus.ON_HOLD, layout);
                    break;
                case 5:
                    createRatePage(RateStatus.DROPPED, layout);
                    break;
                case 6:
                    createRatePage(RateStatus.FAVORITE, layout);
                    break;

            }
            return layout;
        }

        private void createRatePage(RateStatus status, ViewGroup layout) {
            switch (status) {
                case WATCHING:
                    createWatchingPage(layout);
                    break;
                case PLANNED:
                    createPlannedPage(layout);
                    break;
                case REWATCHING:
                    createReWatchingPage(layout);
                    break;
                case COMPLETED:
                    createCompletedPage(layout);
                    break;
                case ON_HOLD:
                    createOnHoldPage(layout);
                    break;
                case DROPPED:
                    createDroppedPage(layout);
                    break;
                case FAVORITE:
                    createFavPage(layout);
                    break;
            }
        }

        private void createFavPage(ViewGroup layout) {

        }

        private void createDroppedPage(ViewGroup layout) {
            droppedList = layout.findViewById(R.id.list);
            droppedRefresh = layout.findViewById(R.id.refresh_layout);
            droppedList.setItemAnimator(new DefaultItemAnimator());
            int margin = (int) getResources().getDimension(R.dimen.margin_small);
            droppedList.addItemDecoration(new VerticalSpaceItemDecoration(margin));
            droppedList.setAdapter(droppedAdapter);
            LinearLayoutManager manager = new LinearLayoutManager(layout.getContext());
            droppedList.setLayoutManager(manager);
            droppedRefresh.setOnRefreshListener(() -> getPresenter().onDroppedRefresh());
            droppedRefresh.setColorSchemeColors(getResources().getColor(R.color.red));
            droppedList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);


                    int visibleItemPosition = manager.findLastCompletelyVisibleItemPosition();
                    int itemCount = manager.getItemCount() - 1;

                    if (visibleItemPosition >= itemCount) {
                        getPresenter().loadDroppedNextPage();
                    }
                }
            });
        }

        private void createOnHoldPage(ViewGroup layout) {
            ohHoldList = layout.findViewById(R.id.list);
            onHoldRefresh = layout.findViewById(R.id.refresh_layout);
            ohHoldList.setItemAnimator(new DefaultItemAnimator());
            int margin = (int) getResources().getDimension(R.dimen.margin_small);
            ohHoldList.addItemDecoration(new VerticalSpaceItemDecoration(margin));
            ohHoldList.setAdapter(onHoldAdapter);
            LinearLayoutManager manager = new LinearLayoutManager(layout.getContext());
            ohHoldList.setLayoutManager(manager);
            onHoldRefresh.setOnRefreshListener(() -> getPresenter().onHoldRefresh());
            onHoldRefresh.setColorSchemeColors(getResources().getColor(R.color.red));
            ohHoldList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);


                    int visibleItemPosition = manager.findLastCompletelyVisibleItemPosition();
                    int itemCount = manager.getItemCount() - 1;

                    if (visibleItemPosition >= itemCount) {
                        getPresenter().loadOnHoldNextPage();
                    }
                }
            });
        }

        private void createCompletedPage(ViewGroup layout) {
            completedList = layout.findViewById(R.id.list);
            completedRefresh = layout.findViewById(R.id.refresh_layout);
            completedList.setItemAnimator(new DefaultItemAnimator());
            int margin = (int) getResources().getDimension(R.dimen.margin_small);
            completedList.addItemDecoration(new VerticalSpaceItemDecoration(margin));
            completedList.setAdapter(completedAdapter);
            LinearLayoutManager manager = new LinearLayoutManager(layout.getContext());
            completedList.setLayoutManager(manager);
            completedRefresh.setOnRefreshListener(() -> getPresenter().onCompletedRefresh());
            completedRefresh.setColorSchemeColors(getResources().getColor(R.color.red));
            completedList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);


                    int visibleItemPosition = manager.findLastCompletelyVisibleItemPosition();
                    int itemCount = manager.getItemCount() - 1;

                    if (visibleItemPosition >= itemCount) {
                        getPresenter().loadCompletedNextPage();
                    }
                }
            });
        }

        private void createReWatchingPage(ViewGroup layout) {
            reWatchingList = layout.findViewById(R.id.list);
            reWatchingRefresh = layout.findViewById(R.id.refresh_layout);
            reWatchingList.setItemAnimator(new DefaultItemAnimator());
            int margin = (int) getResources().getDimension(R.dimen.margin_small);
            reWatchingList.addItemDecoration(new VerticalSpaceItemDecoration(margin));
            reWatchingList.setAdapter(reWatchingAdapter);
            LinearLayoutManager manager = new LinearLayoutManager(layout.getContext());
            reWatchingList.setLayoutManager(manager);
            reWatchingRefresh.setOnRefreshListener(() -> getPresenter().onReWatchingRefresh());
            reWatchingRefresh.setColorSchemeColors(getResources().getColor(R.color.red));
            reWatchingList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);


                    int visibleItemPosition = manager.findLastCompletelyVisibleItemPosition();
                    int itemCount = manager.getItemCount() - 1;

                    if (visibleItemPosition >= itemCount) {
                        getPresenter().loadReWatchingNextPage();
                    }
                }
            });
        }

        private void createPlannedPage(ViewGroup layout) {
            plannedList = layout.findViewById(R.id.list);
            plannedRefresh = layout.findViewById(R.id.refresh_layout);
            plannedList.setItemAnimator(new DefaultItemAnimator());
            int margin = (int) getResources().getDimension(R.dimen.margin_small);
            plannedList.addItemDecoration(new VerticalSpaceItemDecoration(margin));
            plannedList.setAdapter(plannedAdapter);
            LinearLayoutManager manager = new LinearLayoutManager(layout.getContext());
            plannedList.setLayoutManager(manager);
            plannedRefresh.setOnRefreshListener(() -> getPresenter().onPlannedRefresh());
            plannedRefresh.setColorSchemeColors(getResources().getColor(R.color.red));
            plannedList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);


                    int visibleItemPosition = manager.findLastCompletelyVisibleItemPosition();
                    int itemCount = manager.getItemCount() - 1;

                    if (visibleItemPosition >= itemCount) {
                        getPresenter().loadPlannedNextPage();
                    }
                }
            });
        }

        private void createWatchingPage(ViewGroup layout) {
            watchingList = layout.findViewById(R.id.list);
            watchingRefresh = layout.findViewById(R.id.refresh_layout);
            watchingList.setItemAnimator(new DefaultItemAnimator());
            int margin = (int) getResources().getDimension(R.dimen.margin_small);
            watchingList.addItemDecoration(new VerticalSpaceItemDecoration(margin));
            watchingList.setAdapter(watchingAdapter);
            LinearLayoutManager manager = new LinearLayoutManager(layout.getContext());
            watchingList.setLayoutManager(manager);
            watchingRefresh.setOnRefreshListener(() -> getPresenter().onWatchingRefresh());
            watchingRefresh.setColorSchemeColors(getResources().getColor(R.color.red));
            watchingList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);


                    int visibleItemPosition = manager.findLastCompletelyVisibleItemPosition();
                    int itemCount = manager.getItemCount() - 1;

                    if (visibleItemPosition >= itemCount) {
                        getPresenter().loadWatchingNextPage();
                    }
                }
            });
        }

        public void showList(RateStatus status, List<BaseAnimeRateItem> viewModels) {
            switch (status) {
                case WATCHING:
                    watchingAdapter.bindItems(viewModels);
                    break;
                case PLANNED:
                    plannedAdapter.bindItems(viewModels);
                    break;
                case REWATCHING:
                    reWatchingAdapter.bindItems(viewModels);
                    break;
                case COMPLETED:
                    completedAdapter.bindItems(viewModels);
                    break;
                case ON_HOLD:
                    onHoldAdapter.bindItems(viewModels);
                    break;
                case DROPPED:
                    droppedAdapter.bindItems(viewModels);
                    break;
            }
        }

        void insertMore(RateStatus status, List<BaseAnimeRateItem> viewModels) {
            switch (status) {
                case WATCHING:
                    watchingAdapter.insertMore(viewModels);
                    break;
                case PLANNED:
                    plannedAdapter.insertMore(viewModels);
                    break;
                case REWATCHING:
                    reWatchingAdapter.insertMore(viewModels);
                    break;
                case COMPLETED:
                    completedAdapter.insertMore(viewModels);
                    break;
                case ON_HOLD:
                    onHoldAdapter.insertMore(viewModels);
                    break;
                case DROPPED:
                    droppedAdapter.insertMore(viewModels);
                    break;
            }
        }

        void showPageLoading(RateStatus status) {
            switch (status) {
                case WATCHING:
                    if (watchingAdapter != null) {
                        watchingAdapter.showProgress();
                    }
                    break;
                case PLANNED:
                    if (plannedAdapter != null) {
                        plannedAdapter.showProgress();
                    }
                    break;
                case REWATCHING:
                    if (reWatchingAdapter != null) {
                        reWatchingAdapter.showProgress();
                    }
                    break;
                case COMPLETED:
                    if (completedAdapter != null) {
                        completedAdapter.showProgress();
                    }
                    break;
                case ON_HOLD:
                    if (onHoldAdapter != null) {
                        onHoldAdapter.showProgress();
                    }
                    break;
                case DROPPED:
                    if (droppedAdapter != null) {
                        droppedAdapter.showProgress();
                    }
                    break;
            }
        }

        void hidePageLoading(RateStatus status) {
            switch (status) {
                case WATCHING:
                    if (watchingAdapter != null) {
                        watchingAdapter.hideProgress();
                    }
                    break;
                case PLANNED:
                    if (plannedAdapter != null) {
                        plannedAdapter.hideProgress();
                    }
                    break;
                case REWATCHING:
                    if (reWatchingAdapter != null) {
                        reWatchingAdapter.hideProgress();
                    }
                    break;
                case COMPLETED:
                    if (completedAdapter != null) {
                        completedAdapter.hideProgress();
                    }
                    break;
                case ON_HOLD:
                    if (onHoldAdapter != null) {
                        onHoldAdapter.hideProgress();
                    }
                    break;
                case DROPPED:
                    if (droppedAdapter != null) {
                        droppedAdapter.hideProgress();
                    }
                    break;
            }
        }

        void hideLoading(RateStatus status) {
            switch (status) {
                case WATCHING:
                    if (watchingRefresh != null) {
                        watchingRefresh.setRefreshing(false);
                    }
                    break;
                case PLANNED:
                    if (plannedRefresh != null) {
                        plannedRefresh.setRefreshing(false);
                    }
                    break;
                case REWATCHING:
                    if (reWatchingRefresh != null) {
                        reWatchingRefresh.setRefreshing(false);
                    }
                    break;
                case COMPLETED:
                    if (completedRefresh != null) {
                        completedRefresh.setRefreshing(false);
                    }
                    break;
                case ON_HOLD:
                    if (onHoldRefresh != null) {
                        onHoldRefresh.setRefreshing(false);
                    }
                    break;
                case DROPPED:
                    if (droppedRefresh != null) {
                        droppedRefresh.setRefreshing(false);
                    }
                    break;
            }
        }

        void showLoading(RateStatus status) {
            switch (status) {
                case WATCHING:
                    if (watchingRefresh != null) {
                        watchingRefresh.setRefreshing(true);
                    }
                    break;
                case PLANNED:
                    if (plannedRefresh != null) {
                        plannedRefresh.setRefreshing(true);
                    }
                    break;
                case REWATCHING:
                    if (reWatchingRefresh != null) {
                        reWatchingRefresh.setRefreshing(true);
                    }
                    break;
                case COMPLETED:
                    if (completedRefresh != null) {
                        completedRefresh.setRefreshing(true);
                    }
                    break;
                case ON_HOLD:
                    if (onHoldRefresh != null) {
                        onHoldRefresh.setRefreshing(true);
                    }
                    break;
                case DROPPED:
                    if (droppedRefresh != null) {
                        droppedRefresh.setRefreshing(true);
                    }
                    break;
            }
        }
    }

}
