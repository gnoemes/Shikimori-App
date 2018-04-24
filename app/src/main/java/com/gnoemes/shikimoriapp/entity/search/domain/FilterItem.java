package com.gnoemes.shikimoriapp.entity.search.domain;

public class FilterItem {

    private String value;

    private String localizedText;

    private String action;

    public FilterItem(String action, String value, String localizedText) {
        this.action = action;
        this.value = value;
        this.localizedText = localizedText;
    }

    public String getValue() {
        return value;
    }

    public String getLocalizedText() {
        return localizedText;
    }

    public String getAction() {
        return action;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FilterItem) {
            FilterItem filterItem = (FilterItem) obj;
            if (filterItem.action != null &&
                    filterItem.value != null &&
                    filterItem.localizedText != null &&
                    action != null &&
                    value != null &&
                    localizedText != null) {
                return action.equals(filterItem.action)
                        && value.equals(filterItem.value)
                        && localizedText.equals(filterItem.localizedText);
            }
        }
        return super.equals(obj);
    }
}
