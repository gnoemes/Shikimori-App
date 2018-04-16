package com.gnoemes.shikimoriapp.presentation.view.common.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.gnoemes.shikimoriapp.presentation.presenter.common.BasePresenter;
import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseNetworkView;

import javax.inject.Provider;

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

    //    @Inject
    protected Provider<Presenter> presenterProvider;

    private FragmentCallback fragmentCallback;

    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentCallback = (FragmentCallback) context;
        } catch (ClassCastException exception) {
            throw new ClassCastException(context.toString()
                    + " must implement " + fragmentCallback.getClass());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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

    ///////////////////////////////////////////////////////////////////////////
    // UI METHODS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
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
            fragmentCallback.onShowError(error);
        }
    }

    @Override
    public void onShowLoadingDialog() {
        if (hasCallback()) {
            fragmentCallback.onShowLoading();
        }
    }

    @Override
    public void onHideLoading() {
        if (hasCallback()) {
            fragmentCallback.onHideLoading();
        }
    }

    @Override
    public void setTitle(String title) {
        if (hasCallback()) {
            fragmentCallback.onSetTitle(title);
        }
    }

    @Override
    public void initToolbar() {

    }

    private boolean hasCallback() {
        return fragmentCallback != null;
    }
}
