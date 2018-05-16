package com.funprojects.wotlksaves.mvp.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Andrei on 14.05.2018.
 */

public class WhitelistRecord extends RealmObject{
    public String getName() {
        return mName;
    }
    public String getReason() {
        return mReason;
    }
    public Instances getWhereSeen() {
        return mWhereSeen;
    }

    @PrimaryKey
    private String mName;
    private String mReason;
    private Instances mWhereSeen;


    public WhitelistRecord() {

    }

    public WhitelistRecord(String name, String reason, Instances where) {
        this.mName = name;
        this.mReason = reason;
        this.mWhereSeen = where;
    }
}
