package com.gnoemes.shikimoriapp.presentation.view.calendar;

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
import com.gnoemes.shikimoriapp.entity.calendar.presentation.CalendarItemViewModel;
import com.gnoemes.shikimoriapp.presentation.presenter.calendar.CalendarPresenter;
import com.gnoemes.shikimoriapp.presentation.view.calendar.adapter.CalendarAdapter;
import com.gnoemes.shikimoriapp.presentation.view.calendar.adapter.provider.CalendarAnimeResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.BaseFragment;
import com.gnoemes.shikimoriapp.presentation.view.common.fragment.RouterProvider;
import com.gnoemes.shikimoriapp.presentation.view.common.widget.NetworkErrorView;
import com.gnoemes.shikimoriapp.utils.date.converter.DateTimeConverter;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class CalendarFragment extends BaseFragment<CalendarPresenter, CalendarView>
        implements CalendarView {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.list)
    RecyclerView recyclerView;

    @BindView(R.id.view_network_error)
    NetworkErrorView networkErrorView;

    @InjectPresenter
    CalendarPresenter presenter;
    @Inject
    DateTimeConverter dateTimeConverter;
    @Inject
    CalendarAnimeResourceProvider resourceProvider;
    @Inject
    ImageLoader imageLoader;
    private CalendarAdapter calendarAdapter;

    @ProvidePresenter
    CalendarPresenter providePresenter() {
        presenter = presenterProvider.get();
        if (getParentFragment() != null) {
            presenter.setLocalRouter(((RouterProvider) getParentFragment()).getLocalRouter());
        }
        return presenter;
    }

    public static CalendarFragment newInstance() {
        Bundle args = new Bundle();
        CalendarFragment fragment = new CalendarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList();
    }

    private void initList() {
        calendarAdapter = new CalendarAdapter(dateTimeConverter,
                resourceProvider,
                imageLoader,
                id -> getPresenter().onAnimeClicked(id));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(calendarAdapter);

        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.red));
        refreshLayout.setOnRefreshListener(() -> getPresenter().loadCalendarData());
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Returns presenter
     */
    @Override
    protected CalendarPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_calendar;
    }

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void showCalendarData(List<CalendarItemViewModel> items) {
        recyclerView.setVisibility(View.VISIBLE);
        calendarAdapter.addNewItems(items);
    }

    @Override
    public void hideList() {
        recyclerView.setVisibility(View.GONE);
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
