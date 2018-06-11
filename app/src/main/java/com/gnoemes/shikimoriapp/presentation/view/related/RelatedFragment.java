package com.gnoemes.shikimoriapp.presentation.view.related;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.related.domain.RelatedNavigationData;
import com.gnoemes.shikimoriapp.presentation.presenter.related.RelatedPresenter;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.presentation.view.related.adapter.RelatedAdapter;
import com.gnoemes.shikimoriapp.presentation.view.related.provider.RelatedAnimeResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.related.provider.RelatedMangaResourceProvider;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class RelatedFragment extends BaseFragment<RelatedPresenter, RelatedView>
        implements RelatedView {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.list)
    RecyclerView listView;

    @InjectPresenter
    RelatedPresenter presenter;
    @Inject
    ImageLoader imageLoader;
    @Inject
    RelatedAnimeResourceProvider animeResourceProvider;
    @Inject
    RelatedMangaResourceProvider mangaResourceProvider;
    private RelatedAdapter adapter;

    public static RelatedFragment newInstance(RelatedNavigationData data) {
        Bundle args = new Bundle();
        RelatedFragment fragment = new RelatedFragment();
        args.putSerializable(AppExtras.ARGUMENT_RELATED_DATA, data);
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    RelatedPresenter providePresenter() {
        presenter = presenterProvider.get();

        if (getParentFragment() != null) {
            presenter.setLocalRouter(((RouterProvider) getParentFragment()).getLocalRouter());
        }

        if (getArguments() != null && getArguments().containsKey(AppExtras.ARGUMENT_RELATED_DATA)) {
            RelatedNavigationData data = (RelatedNavigationData) getArguments().getSerializable(AppExtras.ARGUMENT_RELATED_DATA);
            if (data != null) {
                presenter.setId(data.getId());
                presenter.setType(data.getType());
            }
        }

        return presenter;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new RelatedAdapter(imageLoader,
                animeResourceProvider,
                mangaResourceProvider,
                (type, id) -> getPresenter().onItemClicked(type, id));
        refreshLayout.setOnRefreshListener(() -> getPresenter().onRefresh());
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.red));
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setAdapter(adapter);
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected RelatedPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_related;
    }


    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////


    @Override
    public void showList(List<BaseItem> items) {
        adapter.bindItems(items);
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
