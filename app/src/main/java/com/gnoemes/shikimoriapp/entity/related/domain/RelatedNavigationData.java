package com.gnoemes.shikimoriapp.entity.related.domain;

import com.gnoemes.shikimoriapp.entity.app.domain.Type;

import java.io.Serializable;

public class RelatedNavigationData implements Serializable {
    private long id;
    private Type type;

    public RelatedNavigationData(long id, Type type) {
        this.id = id;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public Type getType() {
        return type;
    }
}
