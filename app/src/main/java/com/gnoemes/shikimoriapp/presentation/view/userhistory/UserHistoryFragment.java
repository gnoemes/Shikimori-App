package com.gnoemes.shikimoriapp.presentation.view.userhistory;

import android.graphics.drawable.Drawable;
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
import com.gnoemes.shikimoriapp.presentation.presenter.userhistory.UserHistoryPresenter;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.presentation.view.userhistory.adapter.UserHistoryAdapter;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;

import java.util.List;

import butterknife.BindView;

public class UserHistoryFragment extends BaseFragment<UserHistoryPresenter, UserHistoryView>
        implements UserHistoryView {

    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    @InjectPresenter
    UserHistoryPresenter presenter;
    private UserHistoryAdapter adapter;

    public static UserHistoryFragment newInstance(long id) {
        UserHistoryFragment fragment = new UserHistoryFragment();
        Bundle args = new Bundle();
        args.putLong(AppExtras.ARGUMENT_USER_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    UserHistoryPresenter providePresenter() {
        presenter = presenterProvider.get();

        if (getParentFragment() != null) {
            presenter.setLocalRouter(((RouterProvider) getParentFragment()).getLocalRouter());
        }

        if (getArguments() != null) {
            presenter.setId(getArguments().getLong(AppExtras.ARGUMENT_USER_ID));
        }
        return presenter;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {

        Drawable navigationIcon = DrawableHelper.withContext(getContext())
                .withDrawable(R.drawable.ic_arrow_back)
                .withAttributeColor(R.attr.colorText)
                .tint()
                .get();

        toolbar.setNavigationIcon(navigationIcon);
        toolbar.setNavigationOnClickListener(v -> getPresenter().onBackPressed());


        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        adapter = new UserHistoryAdapter(id -> getPresenter().onItemClicked(id));
        list.setLayoutManager(manager);
        list.setAdapter(adapter);
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
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.red));
        refreshLayout.setOnRefreshListener(() -> getPresenter().onRefresh());
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected UserHistoryPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_user_history;
    }

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void showList(List<BaseItem> items) {
        adapter.bindItems(items);
    }

    @Override
    public void insertMore(List<BaseItem> items) {
        adapter.insertMore(items);
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
    public void clearList() {
        adapter.clearItems();
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
