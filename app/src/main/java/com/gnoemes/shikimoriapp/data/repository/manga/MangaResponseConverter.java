package com.gnoemes.shikimoriapp.data.repository.manga;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.manga.data.MangaImageResponse;
import com.gnoemes.shikimoriapp.entity.manga.data.MangaResponse;
import com.gnoemes.shikimoriapp.entity.manga.domain.Manga;
import com.gnoemes.shikimoriapp.entity.manga.domain.MangaImage;

import java.util.List;

import io.reactivex.functions.Function;

public interface MangaResponseConverter extends Function<List<MangaResponse>, List<Manga>> {

    Manga convertResponse(@Nullable MangaResponse response);

    MangaImage convertMangaImage(MangaImageResponse image);
}
