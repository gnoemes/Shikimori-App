package com.gnoemes.shikimoriapp.entity.user.domain;

import org.joda.time.DateTime;

public class GraphInterval {

    private DateTime start;
    private DateTime end;
    private int value;

    public GraphInterval(DateTime start, DateTime end, int value) {
        this.start = start;
        this.end = end;
        this.value = value;
    }

    public DateTime getStart() {
        return start;
    }

    public DateTime getEnd() {
        return end;
    }

    public int getValue() {
        return value;
    }
}
