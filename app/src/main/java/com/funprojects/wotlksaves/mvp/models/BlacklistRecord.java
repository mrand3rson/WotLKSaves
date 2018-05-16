package com.funprojects.wotlksaves.mvp.models;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Andrei on 29.04.2018.
 */

public class BlacklistRecord extends RealmObject {

    //unused, but needed for transfering from WowSchedule
    public String getReason() {
        return reason;
    }
    private String reason;


    public String getName() {
        return name;
    }
    public RealmList<String> getReasons() {
        return reasons;
    }

    public int getTimesCaught() {
        return timesCaught;
    }

    public long getGameRealmId() {
        return mGameRealmId;
    }

    public void setGameRealmId(long mGameRealmId) {
        this.mGameRealmId = mGameRealmId;
    }

    @PrimaryKey @Index
    public long id;
    private String name;
    private RealmList<String> reasons;
    private int timesCaught;

    private long mGameRealmId;


    public BlacklistRecord() {

    }

    public BlacklistRecord(String name, List<String> reasons, int timesCaught) {
        this.name = name;
        this.reasons = (RealmList<String>) reasons;
        this.timesCaught = timesCaught;
    }
}
