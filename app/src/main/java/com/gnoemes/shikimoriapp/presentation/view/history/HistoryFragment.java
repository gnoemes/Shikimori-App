package com.gnoemes.shikimoriapp.presentation.view.history;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.presentation.presenter.history.HistoryPresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.history.provider.HistoryResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.presentation.view.common.widget.NetworkErrorView;
import com.gnoemes.shikimoriapp.presentation.view.history.adapter.HistoryAdapter;
import com.gnoemes.shikimoriapp.utils.date.converter.DateTimeConverter;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.lapism.searchview.SearchView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class HistoryFragment extends BaseFragment<HistoryPresenter, HistoryView> implements HistoryView {

    @BindView(R.id.list)
    RecyclerView historyList;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.view_network_error)
    NetworkErrorView networkErrorView;

    @InjectPresenter
    HistoryPresenter presenter;
    @Inject
    ImageLoader imageLoader;
    @Inject
    DateTimeConverter dateTimeConverter;
    @Inject
    HistoryResourceProvider resourceProvider;

    private SearchView searchView;
    private HistoryAdapter adapter;

    public static HistoryFragment newInstance() {
        Bundle args = new Bundle();
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    HistoryPresenter provideHistoryPresenter() {
        presenter = presenterProvider.get();
        if (getParentFragment() != null) {
            presenter.setLocalRouter(((RouterProvider) getParentFragment()).getLocalRouter());
        }

        return presenter;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList();
        initSearchView();
    }

    private void initList() {
        adapter = new HistoryAdapter(imageLoader, dateTimeConverter, resourceProvider, id -> getPresenter().onItemClicked(id));
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        historyList.setLayoutManager(layout);
        historyList.setAdapter(adapter);
        historyList.setItemAnimator(new DefaultItemAnimator());
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.red));
        refreshLayout.setOnRefreshListener(() -> getPresenter().onRefresh());
    }

    private void initSearchView() {
        searchView = new com.lapism.searchview.SearchView(toolbar.getContext());
        toolbar.addView(searchView);

        searchView.setNavigationIcon(R.drawable.ic_arrow_back);
        searchView.setHint(R.string.common_search);
        searchView.setVoice(false);
        searchView.setShadow(false);
        searchView.setNavigationIcon(R.drawable.ic_close);
        searchView.setVersion(com.lapism.searchview.SearchView.VERSION_MENU_ITEM);
        searchView.setVersionMargins(com.lapism.searchview.SearchView.VERSION_MARGINS_MENU_ITEM);
        searchView.setShouldHideOnKeyboardClose(true);
        searchView.setOnOpenCloseListener(new com.lapism.searchview.SearchView.OnOpenCloseListener() {
            @Override
            public boolean onClose() {
                if (toolbar != null) {
                    toolbar.setTitle(R.string.common_history);
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

        Drawable navigationIcon = DrawableHelper.withContext(getContext())
                .withDrawable(R.drawable.ic_arrow_back)
                .withAttributeColor(R.attr.colorText)
                .tint()
                .get();

        toolbar.setNavigationIcon(navigationIcon);
        toolbar.setNavigationOnClickListener(v -> getPresenter().onBackPressed());
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
    protected HistoryPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_history;
    }

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void showList(List<BaseItem> animes) {
        adapter.bindItems(animes);
    }

    @Override
    public void showNetworkErrorView() {
        networkErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNetworkErrorView() {
        networkErrorView.setVisibility(View.GONE);
    }

    @Override
    public void onShowLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void onHideLoading() {
        refreshLayout.setRefreshing(false);
    }
}
