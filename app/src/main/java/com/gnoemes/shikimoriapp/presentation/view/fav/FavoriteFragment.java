package com.gnoemes.shikimoriapp.presentation.view.fav;

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
import android.widget.ArrayAdapter;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
import com.gnoemes.shikimoriapp.entity.rates.domain.RateStatus;
import com.gnoemes.shikimoriapp.entity.rates.presentation.BaseAnimeRateItem;
import com.gnoemes.shikimoriapp.presentation.presenter.fav.FavoritePresenter;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.presentation.view.common.widget.NetworkErrorView;
import com.gnoemes.shikimoriapp.presentation.view.fav.adapter.AnimeRateAdapter;
import com.gnoemes.shikimoriapp.presentation.view.fav.provider.UserRatesAnimeResourceProvider;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DefaultItemCallback;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.gnoemes.shikimoriapp.utils.view.VerticalSpaceItemDecoration;
import com.santalu.respinner.ReSpinner;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class FavoriteFragment extends BaseFragment<FavoritePresenter, FavoriteView>
        implements FavoriteView, DefaultItemCallback {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.list)
    RecyclerView list;

    @BindView(R.id.view_network_error)
    NetworkErrorView networkErrorView;

    @InjectPresenter
    FavoritePresenter presenter;

    private ReSpinner spinner;

    public static FavoriteFragment newInstance(Long id) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        if (id != null) {
            args.putLong(AppExtras.ARGUMENT_USER_ID, id);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    ImageLoader imageLoader;

    @Inject
    UserRatesAnimeResourceProvider resourceProvider;

    private AnimeRateAdapter adapter;

    @ProvidePresenter
    FavoritePresenter providePresenter() {
        presenter = presenterProvider.get();
        if (getParentFragment() != null) {
            presenter.setLocalRouter(((RouterProvider) getParentFragment()).getLocalRouter());
        }

        if (getArguments() != null && getArguments().containsKey(AppExtras.ARGUMENT_USER_ID)) {
            presenter.setUserId(getArguments().getLong(AppExtras.ARGUMENT_USER_ID));
        }
        return presenter;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initList();
    }

    @Override
    public void initToolbar() {
        toolbar.setTitle(null);
        spinner = new ReSpinner(getContext());
        spinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.item_spinner_normal, resourceProvider.getRateStasuses()));
        spinner.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    getPresenter().onStatusChanged(RateStatus.WATCHING);
                    break;
                case 1:
                    getPresenter().onStatusChanged(RateStatus.PLANNED);
                    break;
                case 2:
                    getPresenter().onStatusChanged(RateStatus.REWATCHING);
                    break;
                case 3:
                    getPresenter().onStatusChanged(RateStatus.COMPLETED);
                    break;
                case 4:
                    getPresenter().onStatusChanged(RateStatus.ON_HOLD);
                    break;
                case 5:
                    getPresenter().onStatusChanged(RateStatus.DROPPED);
                    break;
            }
        });

        Drawable rateBackground = DrawableHelper
                .withContext(getContext())
                .withDrawable(spinner.getBackground())
                .withAttributeColor(R.attr.colorAccent)
                .tint()
                .get();
        spinner.setBackground(rateBackground);

        toolbar.addView(spinner);

        toolbar.inflateMenu(R.menu.menu_favorite);

        MenuItem shuffleItem = toolbar.getMenu().getItem(0);
        Drawable shuffleIcon = DrawableHelper
                .withContext(getContext())
                .withDrawable(shuffleItem.getIcon())
                .withAttributeColor(R.attr.colorText)
                .tint()
                .get();

        shuffleItem.setIcon(shuffleIcon);

        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_shuffle_play:
                    getPresenter().onRandomOpen();
                    break;
            }
            return true;
        });
    }

    private void initList() {
        adapter = new AnimeRateAdapter(imageLoader, resourceProvider, id -> getPresenter().onItemClicked(id));
        list.setItemAnimator(new DefaultItemAnimator());
        int margin = (int) getResources().getDimension(R.dimen.margin_small);
        list.addItemDecoration(new VerticalSpaceItemDecoration(margin));
        list.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        list.setLayoutManager(manager);
        refreshLayout.setOnRefreshListener(() -> getPresenter().onRefresh());
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.red));
        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemPosition = manager.findLastCompletelyVisibleItemPosition();
                int itemCount = manager.getItemCount() - 1;

                if (visibleItemPosition >= itemCount) {
                    getPresenter().loadNextPage();
                }
            }
        });

        getPresenter().onRefresh();
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
    public void showList(List<BaseAnimeRateItem> items) {
        adapter.bindItems(items);
    }

    @Override
    public void insertMore(List<BaseAnimeRateItem> items) {
        adapter.insertMore(items);
    }

    @Override
    public void clearList() {
        adapter.clearItems();
    }

    @Override
    public void onShowPageLoading() {
        adapter.showProgress();
    }

    @Override
    public void onHidePageLoading() {
        adapter.hideProgress();
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
    public void showNetworkErrorView() {
        networkErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNetworkErrorView() {
        networkErrorView.setVisibility(View.GONE);
    }

    @Override
    public void addBackArrow() {
        Drawable navigationIcon = DrawableHelper.withContext(getContext())
                .withDrawable(R.drawable.ic_arrow_back)
                .withAttributeColor(R.attr.colorText)
                .tint()
                .get();

        toolbar.setNavigationIcon(navigationIcon);
        toolbar.setNavigationOnClickListener(v -> getPresenter().onBackPressed());
    }

    @Override
    public void setSpinnerPosition(RateStatus status) {
        if (spinner != null) {
            int position = 0;
            switch (status) {
                case WATCHING:
                    position = 0;
                    break;
                case PLANNED:
                    position = 1;
                    break;
                case REWATCHING:
                    position = 2;
                    break;
                case COMPLETED:
                    position = 3;
                    break;
                case ON_HOLD:
                    position = 4;
                    break;
                case DROPPED:
                    position = 5;
                    break;
            }
            spinner.setSelection(position, false);
        }
    }
}
