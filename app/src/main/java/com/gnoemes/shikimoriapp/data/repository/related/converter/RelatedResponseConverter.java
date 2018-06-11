package com.gnoemes.shikimoriapp.data.repository.related.converter;

import com.gnoemes.shikimoriapp.entity.related.data.RelatedResponse;
import com.gnoemes.shikimoriapp.entity.related.domain.Related;

import java.util.List;

import io.reactivex.functions.Function;

public interface RelatedResponseConverter extends Function<List<RelatedResponse>, List<Related>> {
}
