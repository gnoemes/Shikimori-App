package com.gnoemes.shikimoriapp.di.search;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.base.modules.BaseChildFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomChildScope;
import com.gnoemes.shikimoriapp.presentation.view.search.filter.FilterDialogFragment;
import com.gnoemes.shikimoriapp.presentation.view.search.filter.FilterResourceConverter;
import com.gnoemes.shikimoriapp.presentation.view.search.filter.FilterResourceConverterImpl;
import com.gnoemes.shikimoriapp.presentation.view.search.filter.FilterResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.search.filter.FilterResourceProviderImpl;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;

@Module(includes = BaseChildFragmentModule.class)
public interface FilterModule {

    @Binds
    @BottomChildScope
    @Named(BaseChildFragmentModule.CHILD_FRAGMENT)
    Fragment bindFragment(FilterDialogFragment fragment);


    @Binds
    FilterResourceProvider bindFilterResourceProvider(FilterResourceProviderImpl provider);

    @Binds
    FilterResourceConverter bindFilterResourceConverter(FilterResourceConverterImpl resourceConverter);

}
