package com.gnoemes.shikimoriapp.presentation.view.fav;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
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

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class FavoriteFragment extends BaseFragment<FavoritePresenter, FavoriteView>
        implements FavoriteView, DefaultItemCallback {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.list)
    RecyclerView list;

    @InjectPresenter
    FavoritePresenter presenter;

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
        Spinner spinner = new Spinner(getContext());
        spinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.item_spinner_normal, resourceProvider.getRateStasuses()));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
    public void addBackArrow() {
        Drawable navigationIcon = DrawableHelper.withContext(getContext())
                .withDrawable(R.drawable.ic_arrow_back)
                .withAttributeColor(R.attr.colorText)
                .tint()
                .get();

        toolbar.setNavigationIcon(navigationIcon);
        toolbar.setNavigationOnClickListener(v -> getPresenter().onBackPressed());
    }
}
