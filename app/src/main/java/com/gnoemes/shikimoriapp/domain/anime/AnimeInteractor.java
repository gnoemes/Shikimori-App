package com.gnoemes.shikimoriapp.domain.anime;

import com.gnoemes.shikimoriapp.entity.anime.domain.AnimeDetails;
import com.gnoemes.shikimoriapp.entity.anime.domain.FranchiseNode;
import com.gnoemes.shikimoriapp.entity.anime.domain.Link;
import com.gnoemes.shikimoriapp.entity.screenshots.domain.Screenshot;

import java.util.List;

import io.reactivex.Single;

/**
 * Interface of interactor for anime
 */
public interface AnimeInteractor {

    Single<AnimeDetails> loadAnimeDetails(long animeId);

    Single<List<Link>> getAnimeLinks(long animeId);

    Single<List<FranchiseNode>> getFranchiseNodes(long animeId);

    Single<List<Screenshot>> getScreenshots(long animeId);
}
