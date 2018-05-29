package com.funprojects.wotlksaves.mvp.models;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Andrei on 15.05.2018.
 */

public class Note extends RealmObject {

    //TODO: uncomment when Notes are goin' to appear in the app
    private long setIdIncremented() {
        Realm realm = Realm.getDefaultInstance();
        boolean inOuterTransaction = realm.isInTransaction();
        if (!inOuterTransaction)
            realm.beginTransaction();

        Number maxId = realm.where(this.getClass())
                .max("id");

        if (!inOuterTransaction)
            realm.commitTransaction();

        return (maxId != null ?
                maxId.longValue() + 1 :
                1);
    }

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

    public Note(byte mType, String mText, boolean mDone, long mGameCharacterId) {
        this.id = setIdIncremented();
        this.mType = mType;
        this.mText = mText;
        this.mDone = mDone;
        this.mGameCharacterId = mGameCharacterId;
    }
}
