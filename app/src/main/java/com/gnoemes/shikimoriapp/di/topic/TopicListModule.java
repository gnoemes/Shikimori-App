package com.gnoemes.shikimoriapp.di.topic;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.base.modules.BaseChildFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomScope;
import com.gnoemes.shikimoriapp.domain.social.TopicInteractor;
import com.gnoemes.shikimoriapp.presentation.presenter.topic.list.TopicListPresenter;
import com.gnoemes.shikimoriapp.presentation.presenter.topic.list.converter.TopicViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.topic.list.TopicListFragment;
import com.gnoemes.shikimoriapp.presentation.view.topic.provider.TopicResourceProvider;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {BaseChildFragmentModule.class,
        TopicUtilModule.class,
        TopicRepositoryModule.class,
        TopicInteractorModule.class
})
public interface TopicListModule {


    @Provides
    static TopicListPresenter provideTopicListPresenter(TopicInteractor interactor,
                                                        TopicViewModelConverter converter,
                                                        TopicResourceProvider resourceProvider) {
        return new TopicListPresenter(interactor, converter, resourceProvider);
    }


    @Binds
    @Named(BaseChildFragmentModule.CHILD_FRAGMENT)
    @BottomScope
    Fragment bindFragment(TopicListFragment topicListFragment);
}
