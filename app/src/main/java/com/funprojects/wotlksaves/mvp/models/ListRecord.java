package com.funprojects.wotlksaves.mvp.models;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Andrei on 29.04.2018.
 */

public class ListRecord extends RealmObject {

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

    public byte getListType() {
        return mListType;
    }

    public String getName() {
        return mName;
    }

    public RealmList<String> getReasons() {
        return mReasons;
    }

    public Instances getWhereSeen() {
        return mWhereSeen;
    }

    public int getTimesSeen() {
        return mTimesCaught;
    }

    public long getGameRealmId() {
        return mGameRealmId;
    }
    public void setGameRealmId(long mGameRealmId) {
        this.mGameRealmId = mGameRealmId;
    }

    @PrimaryKey @Index
    public long id;
    private byte mListType;
    private String mName;
    private RealmList<String> mReasons;
    private Instances mWhereSeen;
    private int mTimesCaught;

    private long mGameRealmId;


    public ListRecord() {

    }

    public ListRecord(String name,
                      RealmList<String> reasons,
                      Instances whereSeen,
                      int timesCaught,
                      byte listType,
                      long gameRealmId) {
        this.id = setIdIncremented();
        this.mName = name;
        this.mReasons = reasons;
        this.mWhereSeen = whereSeen;
        this.mTimesCaught = timesCaught;
        this.mListType = listType;
        this.mGameRealmId = gameRealmId;
    }

    public ListRecord(long id,
                      String name,
                      RealmList<String> reasons,
                      Instances whereSeen,
                      int timesCaught,
                      byte listType,
                      long gameRealmId) {
        this.id = id;
        this.mName = name;
        this.mReasons = reasons;
        this.mWhereSeen = whereSeen;
        this.mTimesCaught = timesCaught;
        this.mListType = listType;
        this.mGameRealmId = gameRealmId;
    }
}