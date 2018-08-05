package com.gnoemes.shikimoriapp.di.person;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.base.modules.BaseChildFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomScope;
import com.gnoemes.shikimoriapp.domain.roles.PersonInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.person.PersonPresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.person.converter.PersonDetailsViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.person.PersonFragment;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {BaseChildFragmentModule.class,
        PersonInteractorModule.class,
        PersonRepositoryModule.class,
        PersonUtilModule.class
})
public interface PersonModule {

    @Provides
    static PersonPresenter bindPersonPresenter(PersonInteractor interactor,
                                               PersonDetailsViewModelConverter converter) {
        return new PersonPresenter(interactor, converter);
    }

    @Binds
    @Named(BaseChildFragmentModule.CHILD_FRAGMENT)
    @BottomScope
    Fragment bindFragment(PersonFragment personFragment);
}
