package com.gnoemes.shikimoriapp.di.topic;

import com.gnoemes.shikimoriapp.presentation.presenter.topic.list.converter.TopicViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.topic.list.converter.TopicViewModelConverterImpl;
import com.gnoemes.shikimoriapp.presentation.view.topic.provider.TopicResourceProvider;
import com.gnoemes.shikimoriapp.presentation.view.topic.provider.TopicResourceProviderImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface TopicUtilModule {

    @Binds
    TopicResourceProvider bindTopicResourceProvider(TopicResourceProviderImpl provider);

    @Binds
    TopicViewModelConverter bindTopicViewModelConverter(TopicViewModelConverterImpl converter);

}
