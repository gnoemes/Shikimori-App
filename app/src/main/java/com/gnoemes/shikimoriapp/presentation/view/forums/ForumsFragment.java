package com.gnoemes.shikimoriapp.presentation.view.forums;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.forum.domain.Forum;
import com.gnoemes.shikimoriapp.presentation.presenter.forums.ForumsPresenter;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.presentation.view.common.widget.NetworkErrorView;
import com.gnoemes.shikimoriapp.presentation.view.forums.adapter.ForumAdapter;

import java.util.List;

import butterknife.BindView;

public class ForumsFragment extends BaseFragment<ForumsPresenter, ForumsView> implements ForumsView {

    @BindView(R.id.list)
    RecyclerView forumList;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.view_network_error)
    NetworkErrorView networkErrorView;

    @InjectPresenter
    ForumsPresenter presenter;
    private ForumAdapter forumAdapter;

    public static ForumsFragment newInstance() {
        Bundle args = new Bundle();
        ForumsFragment fragment = new ForumsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    ForumsPresenter providePresenter() {
        presenter = presenterProvider.get();

        if (getParentFragment() != null) {
            presenter.setLocalRouter(((RouterProvider) getParentFragment()).getLocalRouter());
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
        forumAdapter = new ForumAdapter(getPresenter()::onForumClicked);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        forumList.setLayoutManager(layoutManager);
        forumList.setAdapter(forumAdapter);
        refreshLayout.setOnRefreshListener(() -> getPresenter().onRefresh());

        networkErrorView.setVisibility(View.GONE);
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected ForumsPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_forum_list;
    }

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void showForums(List<Forum> forums) {
        forumAdapter.bindItems(forums);
    }

    @Override
    public void showNetworkError() {
        forumList.setVisibility(View.GONE);
        networkErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNetworkError() {
        forumList.setVisibility(View.VISIBLE);
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
