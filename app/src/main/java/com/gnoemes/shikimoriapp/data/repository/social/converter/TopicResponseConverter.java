package com.gnoemes.shikimoriapp.data.repository.social.converter;

import com.gnoemes.shikimoriapp.entity.topic.data.TopicResponse;
import com.gnoemes.shikimoriapp.entity.topic.domain.Topic;

import java.util.List;

import io.reactivex.functions.Function;

public interface TopicResponseConverter extends Function<List<TopicResponse>, List<Topic>> {
    Topic convertResponse(TopicResponse response);
}
