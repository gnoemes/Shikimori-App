package com.gnoemes.shikimoriapp.utils.view;

import com.gnoemes.shikimoriapp.entity.app.domain.LinkedType;

public interface LinkedItemCallback {

    void onLinkedContentClicked(long id, LinkedType linkedType);
}
