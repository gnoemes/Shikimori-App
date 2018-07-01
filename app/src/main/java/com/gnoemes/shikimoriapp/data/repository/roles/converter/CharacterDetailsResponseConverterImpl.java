package com.gnoemes.shikimoriapp.data.repository.roles.converter;

import android.text.TextUtils;

import com.gnoemes.shikimoriapp.data.repository.anime.converter.AnimeListResponseConverter;
import com.gnoemes.shikimoriapp.data.repository.manga.MangaResponseConverter;
import com.gnoemes.shikimoriapp.entity.roles.data.CharacterDetailsResponse;
import com.gnoemes.shikimoriapp.entity.roles.domain.CharacterDetails;
import com.gnoemes.shikimoriapp.utils.Utils;

import javax.inject.Inject;

public class CharacterDetailsResponseConverterImpl implements CharacterDetailsResponseConverter {

    private AnimeListResponseConverter animeResponseConverter;
    private MangaResponseConverter mangaResponseConverter;
    private SeyuResponseConverter seyuResponseConverter;

    @Inject
    public CharacterDetailsResponseConverterImpl(AnimeListResponseConverter animeResponseConverter,
                                                 MangaResponseConverter mangaResponseConverter,
                                                 SeyuResponseConverter seyuResponseConverter) {
        this.animeResponseConverter = animeResponseConverter;
        this.mangaResponseConverter = mangaResponseConverter;
        this.seyuResponseConverter = seyuResponseConverter;
    }

    @Override
    public CharacterDetails apply(CharacterDetailsResponse characterDetailsResponse) throws Exception {
        return new CharacterDetails(characterDetailsResponse.getId(),
                characterDetailsResponse.getName(),
                characterDetailsResponse.getRussianName(),
                seyuResponseConverter.convertImage(characterDetailsResponse.getAnimeImage()),
                Utils.appendHostIfNeed(characterDetailsResponse.getUrl()),
                characterDetailsResponse.getAlternativeName(),
                characterDetailsResponse.getJapaneseName(),
                convertDescription(characterDetailsResponse.getDescription()),
                characterDetailsResponse.getDescriptionSource(),
                seyuResponseConverter.apply(characterDetailsResponse.getSeyuResponses()),
                animeResponseConverter.apply(characterDetailsResponse.getAnimeResponses()),
                mangaResponseConverter.apply(characterDetailsResponse.getMangaResponses()));
    }

    private String convertDescription(String description) {
        if (TextUtils.isEmpty(description)) {
            return null;
        }

        return description.replaceAll("(\\[.+?])", "");
    }
}
