package com.gnoemes.shikimoriapp.data.repository.anime.converter;

import com.gnoemes.shikimoriapp.entity.screenshots.data.ScreenshotResponse;
import com.gnoemes.shikimoriapp.entity.screenshots.domain.Screenshot;

import java.util.List;

import io.reactivex.functions.Function;

public interface ScreenshotResponseConverter extends Function<List<ScreenshotResponse>, List<Screenshot>> {

}
