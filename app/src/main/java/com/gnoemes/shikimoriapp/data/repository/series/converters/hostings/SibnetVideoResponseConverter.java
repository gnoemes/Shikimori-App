package com.gnoemes.shikimoriapp.data.repository.series.converters.hostings;

public interface SibnetVideoResponseConverter extends BaseVideoResponseConverter {

    String convertDashToMp4Link(String url);
}
