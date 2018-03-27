package com.example.nagys.chatty.Classes;

import android.widget.ImageView;

/**
 * Created by szilardnagy on 07/03/2018.
 */

public class Contact {

    private String displayName;
    private String lastMessage;
    private int profilePictureId;
    private String uid;

    public Contact(String displayName, String lastMessage, int profilePictureId, String uid) {
        this.displayName = displayName;
        this.lastMessage = lastMessage;
        this.profilePictureId = profilePictureId;
        this.uid = uid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public int getProfilePictureId() {
        return profilePictureId;
    }

    public String getUid() {
        return uid;
    }
}
