package com.gnoemes.shikimoriapp.presentation.view.topic.list;

import android.graphics.drawable.Drawable;
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
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.forum.domain.ForumType;
import com.gnoemes.shikimoriapp.presentation.presenter.topic.list.TopicListPresenter;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.presentation.view.common.widget.NetworkErrorView;
import com.gnoemes.shikimoriapp.presentation.view.social.SocialFragment;
import com.gnoemes.shikimoriapp.presentation.view.topic.list.adapter.TopicAdapter;
import com.gnoemes.shikimoriapp.presentation.view.topic.provider.TopicResourceProvider;
import com.gnoemes.shikimoriapp.utils.date.converter.DateTimeConverter;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class TopicListFragment extends BaseFragment<TopicListPresenter, TopicListView>
        implements TopicListView {

    @BindView(R.id.list)
    RecyclerView topicList;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.view_network_error)
    NetworkErrorView networkErrorView;

    @InjectPresenter
    TopicListPresenter presenter;
    @Inject
    ImageLoader imageLoader;
    @Inject
    DateTimeConverter dateTimeConverter;
    @Inject
    TopicResourceProvider topicResourceProvider;
    private TopicAdapter topicAdapter;

    public static TopicListFragment newInstance(ForumType type) {
        Bundle args = new Bundle();
        TopicListFragment fragment = new TopicListFragment();
        args.putSerializable(AppExtras.ARGUMENT_FORUM_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    TopicListPresenter providePresenter() {
        presenter = presenterProvider.get();

        if (getParentFragment() != null) {
            presenter.setLocalRouter(((RouterProvider) getParentFragment()).getLocalRouter());
        }

        if (getArguments() != null && getArguments().containsKey(AppExtras.ARGUMENT_FORUM_TYPE)) {
            presenter.setForumType((ForumType) getArguments().getSerializable(AppExtras.ARGUMENT_FORUM_TYPE));
        }

        return presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getParentFragment() instanceof SocialFragment) {
            return inflater.inflate(getFragmentLayout(), container, false);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        topicAdapter = new TopicAdapter(imageLoader,
                getPresenter()::onTopicClicked,
                getPresenter()::onLinkedContentClicked,
                getPresenter()::onUserClicked,
                dateTimeConverter,
                topicResourceProvider);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        topicList.setLayoutManager(layoutManager);
        topicList.setAdapter(topicAdapter);
        topicList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                int itemCount = layoutManager.getItemCount() - 1;

                if (visibleItemPosition >= itemCount) {
                    getPresenter().loadNextPage();
                }

            }
        });
        refreshLayout.setOnRefreshListener(() -> getPresenter().onRefresh());

        if (toolbar != null) {
            Drawable navigationIcon = DrawableHelper.withContext(getContext())
                    .withDrawable(R.drawable.ic_arrow_back)
                    .withAttributeColor(R.attr.colorText)
                    .tint()
                    .get();

            toolbar.setNavigationIcon(navigationIcon);
            toolbar.setNavigationOnClickListener(v -> getPresenter().onBackPressed());
        }

        networkErrorView.setVisibility(View.GONE);
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected TopicListPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_topics;
    }

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void showList(List<BaseItem> baseItems) {
        topicAdapter.bindItems(baseItems);
    }

    @Override
    public void insetMore(List<BaseItem> baseItems) {
        topicAdapter.insertMoreItems(baseItems);
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
    public void onShowPageLoading() {
        topicAdapter.showProgress();
    }

    @Override
    public void onHidePageLoading() {
        topicAdapter.hideProgress();
    }

    @Override
    public void clearList() {
        topicAdapter.clearItems();
    }

    @Override
    public void showNetworkError() {
        networkErrorView.setVisibility(View.VISIBLE);
        topicList.setVisibility(View.GONE);
    }

    @Override
    public void hideNetworkError() {
        networkErrorView.setVisibility(View.GONE);
        topicList.setVisibility(View.VISIBLE);
    }


}
