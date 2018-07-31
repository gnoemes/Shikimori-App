package com.gnoemes.shikimoriapp.di.forums;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.base.modules.BaseChildFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomScope;
import com.gnoemes.shikimoriapp.di.topic.TopicInteractorModule;
import com.gnoemes.shikimoriapp.di.topic.TopicRepositoryModule;
import com.gnoemes.shikimoriapp.domain.social.TopicInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.forums.ForumsPresenter;
import com.gnoemes.shikimoriapp.presentation.view.forums.ForumsFragment;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {BaseChildFragmentModule.class,
        TopicInteractorModule.class,
        TopicRepositoryModule.class
})
public interface ForumsModule {

    @Provides
    static ForumsPresenter provideTopicListPresenter(TopicInteractor interactor) {
        return new ForumsPresenter(interactor);
    }

    @Binds
    @Named(BaseChildFragmentModule.CHILD_FRAGMENT)
    @BottomScope
    Fragment bindFragment(ForumsFragment forumsFragment);
}
