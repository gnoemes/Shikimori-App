package com.gnoemes.shikimoriapp.entity.user.domain;

import com.gnoemes.shikimoriapp.entity.app.domain.Type;
import com.gnoemes.shikimoriapp.entity.rates.domain.RateStatus;

public class Status {
    private long id;
    private RateStatus status;
    private int size;
    private Type type;

    public Status(long id, RateStatus status, int size, Type type) {
        this.id = id;
        this.status = status;
        this.size = size;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public RateStatus getStatus() {
        return status;
    }

    public int getSize() {
        return size;
    }

    public Type getType() {
        return type;
    }
}
