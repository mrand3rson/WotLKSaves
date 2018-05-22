package com.funprojects.wotlksaves.mvp.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Andrei on 14.05.2018.
 */

public class WhitelistRecord extends RealmObject{
    public String getName() {
        return mName;
    }
    public RealmList<String> getReasons() {
        return mReasons;
    }
    public Instances getWhereSeen() {
        return mWhereSeen;
    }

    @PrimaryKey
    private String mName;
    private RealmList<String> mReasons;
    private Instances mWhereSeen;


    public WhitelistRecord() {

    }

    public WhitelistRecord(String name, RealmList<String> reasons, Instances where) {
        this.mName = name;
        this.mReasons = reasons;
        this.mWhereSeen = where;
    }
}
