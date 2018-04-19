package com.gnoemes.shikimoriapp.utils.date;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

import org.joda.time.DateTime;

public interface DateTimeResponseConverter extends JsonSerializer<DateTime>, JsonDeserializer<DateTime> {
}
