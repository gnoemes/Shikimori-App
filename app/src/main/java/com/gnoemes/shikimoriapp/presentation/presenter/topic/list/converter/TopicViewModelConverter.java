package com.gnoemes.shikimoriapp.presentation.presenter.topic.list.converter;

import com.gnoemes.shikimoriapp.entity.app.presentation.BaseItem;
import com.gnoemes.shikimoriapp.entity.topic.domain.Topic;

import java.util.List;

public interface TopicViewModelConverter {

    List<BaseItem> convertListFrom(List<Topic> list);
}
