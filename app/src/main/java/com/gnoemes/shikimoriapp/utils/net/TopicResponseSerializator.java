package com.gnoemes.shikimoriapp.utils.net;

import com.gnoemes.shikimoriapp.entity.anime.data.AnimeResponse;
import com.gnoemes.shikimoriapp.entity.app.data.LinkedContentResponse;
import com.gnoemes.shikimoriapp.entity.club.data.ClubResponse;
import com.gnoemes.shikimoriapp.entity.forum.data.ForumResponse;
import com.gnoemes.shikimoriapp.entity.manga.data.MangaResponse;
import com.gnoemes.shikimoriapp.entity.roles.data.CharacterResponse;
import com.gnoemes.shikimoriapp.entity.roles.data.PersonResponse;
import com.gnoemes.shikimoriapp.entity.topic.data.TopicResponse;
import com.gnoemes.shikimoriapp.entity.user.data.UserBriefResponse;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;

import java.lang.reflect.Type;

public class TopicResponseSerializator<T> implements JsonSerializer<TopicResponse>, JsonDeserializer<TopicResponse> {


    @Override
    public JsonElement serialize(TopicResponse src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject member = new JsonObject();

        return member;
    }

    @Override
    public TopicResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        long id = -1L;
        String title = null;
        String description = null;
        String descHtml = null;
        String footerHtml = null;
        DateTime createdDate = null;
        long commentsCount = 0L;
        String type = null;
        String linkedType = null;
        ForumResponse forumResponse = null;
        UserBriefResponse userBriefResponse = null;
        boolean isViewed = false;

        JsonElement idJson = json.getAsJsonObject().get("id");
        if (!idJson.isJsonNull()) {
            id = context.deserialize(idJson, new TypeToken<Long>() {
            }.getRawType());
        }

        JsonElement titleJson = json.getAsJsonObject().get("topic_title");
        if (!titleJson.isJsonNull()) {
            title = context.deserialize(titleJson, new TypeToken<String>() {
            }.getRawType());
        }

        JsonElement bodyJson = json.getAsJsonObject().get("body");
        if (!bodyJson.isJsonNull()) {
            description = context.deserialize(bodyJson, new TypeToken<String>() {
            }.getRawType());
        }

        JsonElement bodyHtmlJson = json.getAsJsonObject().get("html_body");
        if (!bodyJson.isJsonNull()) {
            descHtml = context.deserialize(bodyHtmlJson, new TypeToken<String>() {
            }.getRawType());
        }

        JsonElement footerJson = json.getAsJsonObject().get("html_footer");
        if (!bodyJson.isJsonNull()) {
            footerHtml = context.deserialize(footerJson, new TypeToken<String>() {
            }.getRawType());
        }

        JsonElement createdAtJson = json.getAsJsonObject().get("created_at");
        if (!createdAtJson.isJsonNull()) {
            createdDate = context.deserialize(createdAtJson, new TypeToken<DateTime>() {
            }.getRawType());
        }

        JsonElement countJson = json.getAsJsonObject().get("comments_count");
        if (!countJson.isJsonNull()) {
            commentsCount = context.deserialize(countJson, new TypeToken<Long>() {
            }.getRawType());
        }

        JsonElement forumJson = json.getAsJsonObject().get("forum");
        if (!forumJson.isJsonNull()) {
            forumResponse = context.deserialize(forumJson, new TypeToken<ForumResponse>() {
            }.getRawType());
        }

        JsonElement userJson = json.getAsJsonObject().get("user");
        if (!userJson.isJsonNull()) {
            userBriefResponse = context.deserialize(userJson, new TypeToken<UserBriefResponse>() {
            }.getRawType());
        }

        JsonElement isViewedJson = json.getAsJsonObject().get("viewed");
        if (!isViewedJson.isJsonNull()) {
            isViewed = context.deserialize(isViewedJson, new TypeToken<Boolean>() {
            }.getRawType());
        }

        JsonElement linkTypeJson = json.getAsJsonObject().get("linked_type");
        if (!linkTypeJson.isJsonNull()) {
            linkedType = context.deserialize(linkTypeJson, new TypeToken<String>() {
            }.getRawType());
        }

        JsonElement typeJson = json.getAsJsonObject().get("type");
        if (!typeJson.isJsonNull()) {
            type = context.deserialize(typeJson, new TypeToken<String>() {
            }.getRawType());
        }


        JsonElement linked = json.getAsJsonObject().get("linked");

//TODO refactor, add other types
        LinkedContentResponse response = null;
        if (linkedType != null)
            switch (linkedType) {
                case "Anime":
                    if (!linked.isJsonNull()) {
                        response = context.deserialize(linked, new TypeToken<AnimeResponse>() {
                        }.getRawType());
                    }
                    break;
                case "Manga":
                    if (!linked.isJsonNull()) {
                        response = context.deserialize(linked, new TypeToken<MangaResponse>() {
                        }.getRawType());
                    }
                    break;
                case "Character":
                    if (!linked.isJsonNull()) {
                        response = context.deserialize(linked, new TypeToken<CharacterResponse>() {
                        }.getRawType());
                    }
                    break;
                case "Person":
                    if (!linked.isJsonNull()) {
                        response = context.deserialize(linked, new TypeToken<PersonResponse>() {
                        }.getRawType());
                    }
                    break;
                case "Club":
                    if (!linked.isJsonNull()) {
                        response = context.deserialize(linked, new TypeToken<ClubResponse>() {
                        }.getRawType());
                    }
                    break;

            }

        return new TopicResponse(id, title, description, descHtml, footerHtml, createdDate, commentsCount, forumResponse, userBriefResponse, type, linkedType, response, isViewed);
    }
}
