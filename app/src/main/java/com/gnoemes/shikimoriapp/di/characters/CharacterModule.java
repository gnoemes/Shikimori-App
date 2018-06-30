package com.gnoemes.shikimoriapp.di.characters;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.base.modules.BaseChildFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomChildScope;
import com.gnoemes.shikimoriapp.domain.roles.CharacterInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.characters.CharacterDetailsPresenter;
import com.gnoemes.shikimoriapp.presentation.view.characters.CharacterDetailsFragment;
import com.gnoemes.shikimoriapp.presentation.view.characters.converter.CharacterViewModelConverter;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {BaseChildFragmentModule.class, CharacterInteractorModule.class,
        CharacterRepositoryModule.class, CharacterUtilModule.class})
public interface CharacterModule {

    @Provides
    static CharacterDetailsPresenter provideCharacterDetailsPresenter(CharacterInteractor interactor,
                                                                      CharacterViewModelConverter converter) {
        return new CharacterDetailsPresenter(interactor, converter);
    }

    @Binds
    @Named(BaseChildFragmentModule.CHILD_FRAGMENT)
    @BottomChildScope
    Fragment bindFragment(CharacterDetailsFragment characterDetailsFragment);
}
