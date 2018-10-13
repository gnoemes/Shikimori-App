package com.gnoemes.shikimoriapp.data.repository.series.converters;

import com.gnoemes.shikimoriapp.entity.anime.series.domain.Translation;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;

import org.jsoup.nodes.Document;

import java.util.List;

public interface TranslationResponseConverter {

    List<Translation> convert(long animeId, int episodeId, TranslationType type, Document document);
}
