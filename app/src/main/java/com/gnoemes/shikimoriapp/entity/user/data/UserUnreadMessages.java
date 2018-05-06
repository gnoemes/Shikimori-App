package com.gnoemes.shikimoriapp.entity.user.data;

import com.google.gson.annotations.SerializedName;

public class UserUnreadMessages {

    @SerializedName("messages")
    private int messagesCount;

    @SerializedName("news")
    private int newsCount;

    @SerializedName("notifications")
    private int notificationCount;

    public int getMessagesCount() {
        return messagesCount;
    }

    public int getNewsCount() {
        return newsCount;
    }

    public int getNotificationCount() {
        return notificationCount;
    }
}
