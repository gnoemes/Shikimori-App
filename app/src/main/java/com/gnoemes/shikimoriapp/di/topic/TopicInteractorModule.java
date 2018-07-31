package com.gnoemes.shikimoriapp.di.topic;

import com.gnoemes.shikimoriapp.domain.social.TopicInteractor;
import com.gnoemes.shikimoriapp.domain.social.TopicInteractorImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface TopicInteractorModule {

    @Binds
    TopicInteractor bindTopicInteractor(TopicInteractorImpl topicInteractor);
}
