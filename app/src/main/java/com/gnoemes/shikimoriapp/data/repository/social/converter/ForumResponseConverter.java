package com.gnoemes.shikimoriapp.data.repository.social.converter;

import com.gnoemes.shikimoriapp.entity.forum.data.ForumResponse;
import com.gnoemes.shikimoriapp.entity.forum.domain.Forum;

import java.util.List;

import io.reactivex.functions.Function;

public interface ForumResponseConverter extends Function<List<ForumResponse>, List<Forum>> {
    Forum convertResponse(ForumResponse response);
}
