package com.funprojects.wotlksaves.mvp.models;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Andrei on 05.05.2018.
 */

public class Account extends RealmObject {

    private long setIdIncremented() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        Number maxId = realm.where(this.getClass())
                .max("id");

        realm.commitTransaction();
        return (maxId != null?
                maxId.longValue() + 1:
                1);
    }

    public String getName() {
        return mName;
    }
    public String getGameRealmName() {
        return mGameRealmName;
    }

    @PrimaryKey @Index
    public long id;
    private String mName;
    private String mGameRealmName;

    public Account() {
        id = setIdIncremented();
    }

    public Account(String name, String gameRealmName) {
        id = setIdIncremented();
        this.mName = name;
        this.mGameRealmName = gameRealmName;
    }

    public static List<Account> getAccounts(GameRealm gameRealm) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RealmResults<Account> accountsRealmResults =
                realm.where(Account.class)
                        .equalTo("mGameRealmName", gameRealm.getName())
                        .findAll();

        realm.commitTransaction();
        return realm.copyFromRealm(accountsRealmResults);
    }
}
