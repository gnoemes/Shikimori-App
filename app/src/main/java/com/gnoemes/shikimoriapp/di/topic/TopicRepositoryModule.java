package com.gnoemes.shikimoriapp.di.topic;

import com.gnoemes.shikimoriapp.data.repository.social.TopicRepository;
import com.gnoemes.shikimoriapp.data.repository.social.TopicRepositoryImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface TopicRepositoryModule {

    @Binds
    TopicRepository bindTopicRepository(TopicRepositoryImpl repository);

}
