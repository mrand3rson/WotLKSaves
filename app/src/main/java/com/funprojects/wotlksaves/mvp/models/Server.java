package com.funprojects.wotlksaves.mvp.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Andrei on 29.04.2018.
 */

public class Server extends RealmObject {

    public String getName() {
        return mName;
    }

    @PrimaryKey
    private String mName;


    public Server() {

    }

    public Server(String name) {
        this.mName = name;
    }

    @Override
    public String toString() {
        return mName;
    }
}
