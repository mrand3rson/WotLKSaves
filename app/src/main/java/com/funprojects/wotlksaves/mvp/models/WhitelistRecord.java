package com.funprojects.wotlksaves.mvp.models;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Andrei on 14.05.2018.
 */

public class WhitelistRecord extends RealmObject{

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

    public String getName() {
        return mName;
    }
    public RealmList<String> getReasons() {
        return mReasons;
    }
    public Instances getWhereSeen() {
        return mWhereSeen;
    }

    @PrimaryKey @Index
    public long id;
    private String mName;
    private RealmList<String> mReasons;
    private Instances mWhereSeen;

    private long mGameRealmId;


    public WhitelistRecord() {

    }

    public WhitelistRecord(String name, List<String> reasons, Instances where, long gameRealmId) {
        this.id = setIdIncremented();
        this.mName = name;
        this.mReasons = (RealmList<String>) reasons;
        this.mWhereSeen = where;
        this.mGameRealmId = gameRealmId;
    }
}
