package com.example.nagys.chatty.Classes;

/**
 * Created by szilardnagy on 21/03/2018.
 */

public class User {

    /* class used for creating new users in firebase database */

    private String displayName;
    private String emailAdress;
    private String uid;

    public User(String displayName, String emailAdress, String uid) {
        this.displayName = displayName;
        this.emailAdress = emailAdress;
        this.uid = uid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
