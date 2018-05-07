package com.gnoemes.shikimoriapp.di.anime;

import com.gnoemes.shikimoriapp.data.repository.comments.CommentsRepository;
import com.gnoemes.shikimoriapp.data.repository.comments.CommentsRepositoryImpl;
import com.gnoemes.shikimoriapp.data.repository.comments.converter.CommentsResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.comments.converter.CommentsResponseConverterImpl;
import com.gnoemes.shikimoriapp.domain.comments.CommentsInteractor;
import com.gnoemes.shikimoriapp.domain.comments.CommentsInteractorImpl;
import com.gnoemes.shikimoriapp.presentation.presenter.comments.converter.CommentsViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.comments.converter.CommentsViewModelConverterImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface CommentsModule {

    @Binds
    CommentsResponseConverter bindCommentsResponseConverter(CommentsResponseConverterImpl converter);

    @Binds
    CommentsRepository bindCommentsRepository(CommentsRepositoryImpl repository);

    @Binds
    CommentsInteractor bindCommentsInteractor(CommentsInteractorImpl interactor);

    @Binds
    CommentsViewModelConverter bindCommentsViewModelConverter(CommentsViewModelConverterImpl converter);
}
