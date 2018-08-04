package com.gnoemes.shikimoriapp.di.person;

import com.gnoemes.shikimoriapp.data.repository.roles.converter.CharacterResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.roles.converter.CharacterResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.roles.converter.PersonDetailsResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.roles.converter.PersonDetailsResponseConverterImpl;
import com.gnoemes.shikimoriapp.presentation.presenter.person.converter.PersonDetailsViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.presenter.person.converter.PersonDetailsViewModelConverterImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface PersonUtilModule {

    @Binds
    PersonDetailsResponseConverter bindPersonDetailsResponseConverter(PersonDetailsResponseConverterImpl converter);

    @Binds
    PersonDetailsViewModelConverter bindPersonDetailsViewModelConverter(PersonDetailsViewModelConverterImpl converter);

    @Binds
    CharacterResponseConverter bindCharacterResponseConverter(CharacterResponseConverterImpl converter);
}
