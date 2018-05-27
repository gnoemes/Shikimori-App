package com.gnoemes.shikimoriapp.entity.user.presentation.history;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.app.domain.Type;

public class HistoryItem extends BaseHistoryItem {

    private long id;
    private String actionDate;
    private String description;
    private boolean hasTarget;
    @Nullable
    private Long valueId;
    @Nullable
    private String valueName;
    @Nullable
    private String valueImage;
    @Nullable
    private Type valueType;

    public HistoryItem(long id, String actionDate, String description,
                       boolean hasTarget, @Nullable Long valueId, @Nullable String valueName,
                       @Nullable String valueImage, @Nullable Type valueType) {
        this.id = id;
        this.actionDate = actionDate;
        this.description = description;
        this.hasTarget = hasTarget;
        this.valueId = valueId;
        this.valueName = valueName;
        this.valueImage = valueImage;
        this.valueType = valueType;
    }

    public long getId() {
        return id;
    }

    public String getActionDate() {
        return actionDate;
    }

    public String getDescription() {
        return description;
    }

    public boolean isHasTarget() {
        return hasTarget;
    }

    @Nullable
    public Long getValueId() {
        return valueId;
    }

    @Nullable
    public String getValueName() {
        return valueName;
    }

    @Nullable
    public String getValueImage() {
        return valueImage;
    }

    @Nullable
    public Type getValueType() {
        return valueType;
    }
}
