package com.funprojects.wotlksaves.mvp.models;

import io.realm.annotations.PrimaryKey;

/**
 * Created by Andrei on 15.05.2018.
 */

public class Note {

    public byte getType() {
        return mType;
    }

    public String getText() {
        return mText;
    }

    public boolean isDone() {
        return mDone;
    }

    public long getGameCharacterId() {
        return mGameCharacterId;
    }
    public void bindToGameCharacter(long mGameCharacterId) {
        this.mGameCharacterId = mGameCharacterId;
    }

    @PrimaryKey
    public long id;
    private byte mType;
    private String mText;
    private boolean mDone;

    private long mGameCharacterId;


    public Note() {

    }

    public Note(long id, byte mType, String mText, boolean mDone, long mGameCharacterId) {
        this.id = id;
        this.mType = mType;
        this.mText = mText;
        this.mDone = mDone;
        this.mGameCharacterId = mGameCharacterId;
    }
}
