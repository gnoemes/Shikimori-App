package com.gnoemes.shikimoriapp.entity.roles.data;

import android.support.annotation.Nullable;

import com.gnoemes.shikimoriapp.entity.common.data.ImageResponse;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.List;

public class PersonDetailsResponse {

    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("russian")
    private String russianName;
    @SerializedName("image")
    private ImageResponse imageResponse;
    @SerializedName("url")
    private String url;
    @Nullable
    @SerializedName("japanese")
    private String japaneseName;
    @Nullable
    @SerializedName("job_title")
    private String jobTitle;
    @Nullable
    @SerializedName("birthday")
    private DateTime birthDay;
    @Nullable
    @SerializedName("works")
    private List<WorkResponse> workResponses;
    @Nullable
    @SerializedName("roles")
    private List<SeyuRoleResponse> seyuRoleResponses;
    @SerializedName("groupped_roles")
    private List<List<String>> roleResponses;
    @SerializedName("topic_id")
    private long topicId;
    @SerializedName("person_favoured")
    private boolean isFavoritePerson;
    @SerializedName("producer")
    private boolean isProducer;
    @SerializedName("producer_favoured")
    private boolean isFavoriteProducer;
    @SerializedName("mangaka")
    private boolean isMangaka;
    @SerializedName("mangaka_favoured")
    private boolean isFavoriteMangaka;
    @SerializedName("seyu")
    private boolean isSeyu;
    @SerializedName("seyu_favoured")
    private boolean isFavoriteSeyu;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRussianName() {
        return russianName;
    }

    public ImageResponse getImageResponse() {
        return imageResponse;
    }

    public String getUrl() {
        return url;
    }

    @Nullable
    public String getJapaneseName() {
        return japaneseName;
    }

    @Nullable
    public String getJobTitle() {
        return jobTitle;
    }

    @Nullable
    public DateTime getBirthDay() {
        return birthDay;
    }

    @Nullable
    public List<SeyuRoleResponse> getSeyuRoleResponses() {
        return seyuRoleResponses;
    }

    @Nullable
    public List<WorkResponse> getWorkResponses() {
        return workResponses;
    }

    public List<List<String>> getRoleResponses() {
        return roleResponses;
    }

    public long getTopicId() {
        return topicId;
    }

    public boolean isFavoritePerson() {
        return isFavoritePerson;
    }

    public boolean isProducer() {
        return isProducer;
    }

    public boolean isFavoriteProducer() {
        return isFavoriteProducer;
    }

    public boolean isMangaka() {
        return isMangaka;
    }

    public boolean isFavoriteMangaka() {
        return isFavoriteMangaka;
    }

    public boolean isSeyu() {
        return isSeyu;
    }

    public boolean isFavoriteSeyu() {
        return isFavoriteSeyu;
    }
}
