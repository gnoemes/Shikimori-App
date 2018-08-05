package com.gnoemes.shikimoriapp.data.repository.app.converter;

import com.gnoemes.shikimoriapp.entity.app.data.LinkedContentResponse;
import com.gnoemes.shikimoriapp.entity.app.domain.LinkedContent;

public interface LinkedContentResponseConverter {

    LinkedContent convertResponse(LinkedContentResponse linkedContentResponse);
}
