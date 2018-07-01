package com.gnoemes.shikimoriapp.di.characters;

import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeListResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeListResponseConverterImpl;
import com.gnoemes.shikimoriapp.data.repository.roles.converter.CharacterDetailsResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.roles.converter.CharacterDetailsResponseConverterImpl;
import com.gnoemes.shikimoriapp.presentation.view.characters.converter.CharacterViewModelConverter;
import com.gnoemes.shikimoriapp.presentation.view.characters.converter.CharacterViewModelConverterImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface CharacterUtilModule {

    @Binds
    AnimeListResponseConverter bindAnimeListResponseConverter(AnimeListResponseConverterImpl responseConverter);

    @Binds
    CharacterDetailsResponseConverter bindCharacterDetailsResponseConverter(CharacterDetailsResponseConverterImpl converter);

    @Binds
    CharacterViewModelConverter bindCharacterViewModelConverter(CharacterViewModelConverterImpl converter);
}
