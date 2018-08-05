package com.gnoemes.shikimoriapp.presentation.view.search;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeGenre;
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.search.domain.FilterItem;
import com.gnoemes.shikimoriapp.presentation.presenter.search.SearchPresenter;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.presentation.view.common.widget.EmptyContentView;
import com.gnoemes.shikimoriapp.presentation.view.common.widget.NetworkErrorView;
import com.gnoemes.shikimoriapp.presentation.view.search.adapter.SearchAnimeAdapter;
import com.gnoemes.shikimoriapp.presentation.view.search.filter.FilterDialogFragment;
import com.gnoemes.shikimoriapp.presentation.view.search.provider.SearchAnimeResourceProvider;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchFragment extends BaseFragment<SearchPresenter, SearchView>
        implements SearchView, AAH_FabulousFragment.Callbacks, FilterDialogFragment.FilterProvider {

    @BindView(R.id.btn_filter)
    FloatingActionButton filterButton;

    @BindView(R.id.list)
    RecyclerView recyclerView;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.view_network_error)
    NetworkErrorView networkErrorView;

    @BindView(R.id.view_search_empty)
    EmptyContentView searchEmptyView;

    @InjectPresenter
    SearchPresenter presenter;
    @Inject
    SearchAnimeResourceProvider resourceProvider;
    @Inject
    ImageLoader imageLoader;
    private com.lapism.searchview.SearchView searchView;
    private SearchAnimeAdapter animeAdapter;
    private FilterDialogFragment dialogFragment;

    public static SearchFragment newInstance(@Nullable AnimeGenre data) {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        args.putSerializable(AppExtras.ARGUMENT_GENRE, data);
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    SearchPresenter provideSearchPresenter() {
        presenter = presenterProvider.get();
        if (getParentFragment() != null) {
            presenter.setLocalRouter(((RouterProvider) getParentFragment()).getLocalRouter());
        }

        if (getArguments() != null && getArguments().containsKey(AppExtras.ARGUMENT_GENRE)) {
            presenter.setGenre((AnimeGenre) getArguments().getSerializable(AppExtras.ARGUMENT_GENRE));
        }

        return presenter;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            boolean dialog = savedInstanceState.getBoolean(AppExtras.ARGUMENT_DIALOG);
            if (dialog) {
                dialogFragment = FilterDialogFragment.newInstance();
                dialogFragment.setParentFab(filterButton);
                dialogFragment.show(getChildFragmentManager(), dialogFragment.getTag());
            }
        }
        initList();
        initSearchView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (dialogFragment != null) {
            outState.putBoolean(AppExtras.ARGUMENT_DIALOG, dialogFragment.isAdded());
            dialogFragment.dismiss();
            dialogFragment = null;
        }
    }

    private void initList() {
        animeAdapter = new SearchAnimeAdapter(resourceProvider, imageLoader, id -> getPresenter().onItemClicked(id));
        final FlexboxLayoutManager layout = new FlexboxLayoutManager(getContext());
        layout.setFlexWrap(FlexWrap.WRAP);
        layout.setJustifyContent(JustifyContent.CENTER);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(animeAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemPosition = layout.findLastCompletelyVisibleItemPosition() + 6;
                int itemCount = layout.getItemCount() - 1;

                if (visibleItemPosition >= itemCount) {
                    getPresenter().loadNextPage();
                }

            }
        });
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.red));
        refreshLayout.setOnRefreshListener(() -> getPresenter().onRefresh());
    }

    private void initSearchView() {
        searchView = new com.lapism.searchview.SearchView(toolbar.getContext());
        toolbar.addView(searchView);

        searchEmptyView.setText(R.string.search_nothing);
        searchView.setNavigationIcon(R.drawable.ic_arrow_back);
        searchView.setHint(R.string.common_search);
        searchView.setVoice(false);
        searchView.setShadow(false);
        searchView.setVersion(com.lapism.searchview.SearchView.VERSION_MENU_ITEM);
        searchView.setVersionMargins(com.lapism.searchview.SearchView.VERSION_MARGINS_MENU_ITEM);
        searchView.setShouldHideOnKeyboardClose(true);
        searchView.setOnOpenCloseListener(new com.lapism.searchview.SearchView.OnOpenCloseListener() {
            @Override
            public boolean onClose() {
                if (toolbar != null) {
                    toolbar.setTitle(R.string.common_search);
                    toggleMenu(true)
                            .setOnMenuItemClickListener(item -> {
                                searchView.open(true);
                                return false;
                            });
                }
                return false;
            }

            @Override
            public boolean onOpen() {
                if (toolbar != null) {
                    toolbar.setTitle(null);
                    toggleMenu(false)
                            .setOnMenuItemClickListener(item -> {
                                getPresenter().setSearchQuery();
                                searchView.close(false);
                                return false;
                            });
                }
                return false;
            }
        });
        searchView.setOnQueryTextListener(new com.lapism.searchview.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getPresenter().setSearchQuery(query);
                getPresenter().onRefresh();
                searchView.close(true);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getPresenter().setSearchReactive(newText);
                return false;
            }
        });

        toolbar.inflateMenu(R.menu.menu_search);
        toggleMenu(true);
    }

    private MenuItem toggleMenu(boolean search) {
        MenuItem menuItem = toolbar.getMenu().getItem(0);
        DrawableHelper.withContext(getContext())
                .withDrawable(search ? R.drawable.ic_search : R.drawable.ic_check)
                .withAttributeColor(R.attr.colorText)
                .tint()
                .applyTo(menuItem);
        return menuItem;
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected SearchPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_search;
    }

    ///////////////////////////////////////////////////////////////////////////
    // CALLBACKS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onResult(Object resultObject) {
        if (resultObject.toString().equalsIgnoreCase("swiped_down")
                && dialogFragment != null) {
            dialogFragment.dismiss();
        }
        if (resultObject instanceof HashMap) {
            HashMap<String, List<FilterItem>> appliedFilters = (HashMap<String, List<FilterItem>>) resultObject;
            getPresenter().onFiltersSelected(appliedFilters);
        }
    }

    @Override
    public HashMap<String, List<FilterItem>> getAppliedFilters() {
        return getPresenter().getFilters();
    }

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    @OnClick(R.id.btn_filter)
    public void onFilterCLicked() {
        getPresenter().onFilterPressed();
    }

    @Override
    public void showFilterDialog(HashMap<String, List<FilterItem>> filters) {
        if (dialogFragment == null || !dialogFragment.isAdded()) {
            dialogFragment = FilterDialogFragment.newInstance();
            dialogFragment.setParentFab(filterButton);
            dialogFragment.show(getChildFragmentManager(), dialogFragment.getTag());
        }
    }

    @Override
    public void showList(List<BaseItem> animes) {
        recyclerView.setVisibility(View.VISIBLE);
        animeAdapter.bindItems(animes);
    }

    @Override
    public void insetMore(List<BaseItem> items) {
        animeAdapter.insertMore(items);
    }

    @Override
    public void hideList() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showNetworkError() {
        networkErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNetworkError() {
        networkErrorView.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyView() {
        searchEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {
        searchEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void clearList() {
        animeAdapter.clearList();
    }

    @Override
    public void onShowPageLoading() {
        animeAdapter.showPageLoading();
    }

    @Override
    public void onHidePageLoading() {
        animeAdapter.hidePageLoading();
    }

    @Override
    public void onShowLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void onHideLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void addBackButton() {
        Drawable navigationIcon = DrawableHelper.withContext(getContext())
                .withDrawable(R.drawable.ic_arrow_back)
                .withAttributeColor(R.attr.colorText)
                .tint()
                .get();

        toolbar.setNavigationIcon(navigationIcon);
        toolbar.setNavigationOnClickListener(v -> getPresenter().onBackPressed());
    }
}
