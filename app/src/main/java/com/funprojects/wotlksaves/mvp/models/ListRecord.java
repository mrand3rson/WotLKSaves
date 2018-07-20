package com.funprojects.wotlksaves.mvp.models;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

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

    public boolean isHorde() {
        return mFaction;
    }

    public void setFaction(boolean faction) {
        mFaction = faction;
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

    public void incrementTimesSeen() {
        mTimesCaught++;
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
    private boolean mFaction;
    private String mName;
    private RealmList<String> mReasons;
    private Instances mWhereSeen;
    private int mTimesCaught;

    private long mGameRealmId;


    public ListRecord() {

    }

    public ListRecord(String name,
                      RealmList<String> reasons,
                      boolean faction,
                      Instances whereSeen,
                      int timesCaught,
                      byte listType,
                      long gameRealmId) {
        this.id = setIdIncremented();
        this.mName = name;
        this.mReasons = reasons;
        this.mFaction = faction;
        this.mWhereSeen = whereSeen;
        this.mTimesCaught = timesCaught;
        this.mListType = listType;
        this.mGameRealmId = gameRealmId;
    }

    public ListRecord(long id,
                      String name,
                      RealmList<String> reasons,
                      boolean faction,
                      Instances whereSeen,
                      int timesCaught,
                      byte listType,
                      long gameRealmId) {
        this.id = id;
        this.mName = name;
        this.mReasons = reasons;
        this.mFaction = faction;
        this.mWhereSeen = whereSeen;
        this.mTimesCaught = timesCaught;
        this.mListType = listType;
        this.mGameRealmId = gameRealmId;
    }
}