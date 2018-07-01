package com.gnoemes.shikimoriapp.data.repository.anime.converter;

import com.gnoemes.shikimoriapp.BuildConfig;
import com.gnoemes.shikimoriapp.entity.screenshots.data.ScreenshotResponse;
import com.gnoemes.shikimoriapp.entity.screenshots.domain.Screenshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ScreenshotResponseConverterImpl implements ScreenshotResponseConverter {

    @Inject
    public ScreenshotResponseConverterImpl() {
    }

    @Override
    public List<Screenshot> apply(List<ScreenshotResponse> screenshotResponses) {
        List<Screenshot> items = new ArrayList<>();

        for (ScreenshotResponse response : screenshotResponses) {
            items.add(convertResponse(response));
        }

        return items;
    }

    private Screenshot convertResponse(ScreenshotResponse response) {
        return new Screenshot(
                buildUrl(response.getOriginal()),
                buildUrl(response.getPreview()));
    }

    private String buildUrl(String url) {
        if (url.contains("http")) {
            return url;
        }
        return BuildConfig.ShikimoriBaseUrl.concat(url.replaceFirst("/", ""));
    }
}
