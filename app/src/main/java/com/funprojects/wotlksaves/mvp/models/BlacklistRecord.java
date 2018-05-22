package com.funprojects.wotlksaves.mvp.models;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Andrei on 29.04.2018.
 */

public class BlacklistRecord extends RealmObject {

    private long setIdIncremented() {
        Realm realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction())
            realm.beginTransaction();

        Number maxId = realm.where(this.getClass())
                .max("id");

        realm.commitTransaction();

        return (maxId != null ?
                maxId.longValue() + 1 :
                1);
    }

    //unused, but needed for transfering from WowSchedule
    public String getReason() {
        return reason;
    }
    private String reason;


    public String getName() {
        return mName;
    }
    public RealmList<String> getReasons() {
        return mReasons;
    }

    public int getTimesCaught() {
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
    private String mName;
    private RealmList<String> mReasons;
    private int mTimesCaught;

    private long mGameRealmId;


    public BlacklistRecord() {

    }

    public BlacklistRecord(String name, List<String> reasons, int timesCaught, long gameRealmId) {
        this.id = setIdIncremented();
        this.mName = name;
        this.mReasons = (RealmList<String>) reasons;
        this.mTimesCaught = timesCaught;
        this.mGameRealmId = gameRealmId;
    }
}
