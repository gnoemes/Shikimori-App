package com.gnoemes.shikimoriapp.presentation.view.common.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BasePresenter;
import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseNetworkView;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

/**
 * Base MVP fragment for all fragments
 *
 * @param <Presenter>
 * @param <View>
 */
public abstract class BaseFragment<Presenter extends BasePresenter, View extends BaseNetworkView>
        extends MvpAppCompatFragment implements BaseFragmentView {

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @Inject
    protected Provider<Presenter> presenterProvider;

    private ActivityCallback activityCallback;

    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        try {
            activityCallback = (ActivityCallback) context;
        } catch (ClassCastException exception) {
            throw new ClassCastException(context.toString()
                    + " must implement " + activityCallback.getClass());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        android.view.View view = inflater.inflate(R.layout.fragment_base, container, false);
        if (getFragmentLayout() != android.view.View.NO_ID) {
            inflater.inflate(getFragmentLayout(), view.findViewById(R.id.fragment_content), true);
        }
        return view;
    }

    /**
     * Init views
     */
    @Override
    public void onViewCreated(@NonNull android.view.View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getView() != null) {
            unbinder = ButterKnife.bind(this, getView());
        }
        toolbar.setVisibility(android.view.View.VISIBLE);
    }

    /**
     * Unbind ButterKnife views and hide keyboard
     */
    @Override
    public void onDestroyView() {
        unbinder.unbind();
        hideSoftInput();
        super.onDestroyView();
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Get current presenter
     */
    protected abstract Presenter getPresenter();

    protected ActivityCallback getActivityCallback() {
        return activityCallback;
    }

    @LayoutRes
    protected abstract int getFragmentLayout();

    ///////////////////////////////////////////////////////////////////////////
    // UI METHODS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getPresenter().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void hideSoftInput() {
        if (getActivity() != null) {
            android.view.View focus = getActivity().getCurrentFocus();
            if (focus != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(focus.getWindowToken(), 0);
                }
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////


    @Override
    public void onBackPressed() {
        getPresenter().onBackPressed();
    }

    @Override
    public void onShowError(String error) {
        if (hasCallback()) {
            activityCallback.onShowError(error);
        }
    }

    @Override
    public void onShowLoading() {
        if (hasCallback()) {
            activityCallback.onShowLoading();
        }
    }

    @Override
    public void onHideLoading() {
        if (hasCallback()) {
            activityCallback.onHideLoading();
        }
    }

    @Override
    public void setTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void setTitle(int stringRes) {
        toolbar.setTitle(stringRes);
    }

    @Override
    public void initToolbar() {

    }

    private boolean hasCallback() {
        return activityCallback != null;
    }
}
