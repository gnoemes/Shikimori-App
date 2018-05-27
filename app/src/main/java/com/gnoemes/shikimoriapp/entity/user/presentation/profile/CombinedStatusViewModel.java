package com.gnoemes.shikimoriapp.entity.user.presentation.profile;

import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.presentation.view.common.widget.RateProgressView;

public class CombinedStatusViewModel {

    private int size;
    private RateProgressView.RateBarStatus status;
    private Type type;

    public CombinedStatusViewModel(int size, RateProgressView.RateBarStatus status, Type type) {
        this.size = size;
        this.status = status;
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public RateProgressView.RateBarStatus getStatus() {
        return status;
    }

    public Type getType() {
        return type;
    }
}
