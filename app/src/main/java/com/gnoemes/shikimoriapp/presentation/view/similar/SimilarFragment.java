package com.gnoemes.shikimoriapp.presentation.view.similar;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.presentation.presenter.similar.SimilarPresenter;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.presentation.view.common.widget.EmptyContentView;
import com.gnoemes.shikimoriapp.presentation.view.common.widget.NetworkErrorView;
import com.gnoemes.shikimoriapp.presentation.view.search.adapter.SearchAnimeAdapter;
import com.gnoemes.shikimoriapp.presentation.view.search.provider.SearchAnimeResourceProvider;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class SimilarFragment extends BaseFragment<SimilarPresenter, SimilarView>
        implements SimilarView {

    @BindView(R.id.view_network_error)
    NetworkErrorView errorView;

    @BindView(R.id.view_empty)
    EmptyContentView emptyContentView;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.list)
    RecyclerView recyclerView;

    @InjectPresenter
    SimilarPresenter presenter;
    @Inject
    SearchAnimeResourceProvider resourceProvider;
    @Inject
    ImageLoader imageLoader;
    private SearchAnimeAdapter animeAdapter;

    public static SimilarFragment newInstance(Long anime) {
        Bundle args = new Bundle();
        SimilarFragment fragment = new SimilarFragment();
        args.putLong(AppExtras.ARGUMENT_ANIME_ID, anime);
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    SimilarPresenter providePresenter() {
        presenter = presenterProvider.get();

        if (getArguments() != null) {
            if (getParentFragment() != null) {
                presenter.setLocalRouter(((RouterProvider) getParentFragment()).getLocalRouter());
            }
            presenter.setAnimeId(getArguments().getLong(AppExtras.ARGUMENT_ANIME_ID));
        }
        return presenter;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
    }

    private void initViews() {
        animeAdapter = new SearchAnimeAdapter(resourceProvider, imageLoader,
                id -> getPresenter().onAnimeClicked(id));

        final FlexboxLayoutManager layout = new FlexboxLayoutManager(getContext());
        layout.setFlexWrap(FlexWrap.WRAP);
        layout.setJustifyContent(JustifyContent.CENTER);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(animeAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.red));
        refreshLayout.setOnRefreshListener(() -> getPresenter().onRefresh());
        errorView.setVisibility(View.GONE);
        emptyContentView.setVisibility(View.GONE);
        emptyContentView.setText(R.string.similar_empty);

        Drawable navigationIcon = DrawableHelper.withContext(getContext())
                .withDrawable(R.drawable.ic_arrow_back)
                .withAttributeColor(R.attr.colorText)
                .tint()
                .get();

        toolbar.setNavigationIcon(navigationIcon);
        toolbar.setNavigationOnClickListener(v -> getPresenter().onBackPressed());
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected SimilarPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_similar;
    }

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void showList(List<BaseItem> animes) {
        animeAdapter.bindItems(animes);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideList() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showNetworkError() {
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNetworkError() {
        errorView.setVisibility(View.GONE);
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
    public void showEmptyView() {
        emptyContentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {
        emptyContentView.setVisibility(View.GONE);
    }
}
