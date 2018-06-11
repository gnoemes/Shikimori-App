package com.gnoemes.shikimoriapp.di.related;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.base.modules.BaseChildFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomChildScope;
import com.gnoemes.shikimoriapp.domain.related.RelatedInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.related.RelatedPresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.related.RelatedViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.related.RelatedFragment;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {BaseChildFragmentModule.class, RelatedRepositoryModule.class,
        RelatedInteractorModule.class, RelatedUtilsModule.class})
public interface RelatedModule {


    @Provides
    static RelatedPresenter providdeRelatedPresenter(RelatedInteractor relatedInteractor,
                                                     RelatedViewModelConverter converter) {
        return new RelatedPresenter(relatedInteractor, converter);
    }

    @Binds
    @Named(BaseChildFragmentModule.CHILD_FRAGMENT)
    @BottomChildScope
    Fragment bindFragment(RelatedFragment relatedFragment);

}
