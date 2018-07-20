package com.funprojects.wotlksaves.mvp.models;

import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Andrei on 05.05.2018.
 */

public class Account extends RealmObject {

    public static long setIdIncremented() {
        Realm realm = Realm.getDefaultInstance();
        boolean inOuterTransaction = realm.isInTransaction();
        if (!inOuterTransaction)
            realm.beginTransaction();

        Number maxId = realm.where(Account.class)
                .max("id");

        if (!inOuterTransaction)
            realm.commitTransaction();

        return (maxId != null ?
                maxId.longValue() + 1 :
                1);
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public long getGameRealmId() {
        return mGameRealmId;
    }

    public void setGameRealmId(long gameRealmId) {
        this.mGameRealmId = gameRealmId;
    }

    //    public String getGameRealmName() {
//        return mGameRealmName;
//    }

    @PrimaryKey @Index
    public long id;
    private String mName;
    private long mGameRealmId;
    private RealmList<GameCharacter> mCharacters;

    public Account() {
        id = setIdIncremented();
    }

    public Account(String name, long gameRealmId) {
        id = setIdIncremented();
        this.mName = name;
        this.mGameRealmId = gameRealmId;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s", mName);
    }

    public RealmList<GameCharacter> getCharacters() {
        if (mCharacters == null || mCharacters.isEmpty()) {
            mCharacters = new RealmList<>();
            Realm realm = Realm.getDefaultInstance();
            boolean inOuterTransaction = realm.isInTransaction();
            if (!inOuterTransaction)
                realm.beginTransaction();

            mCharacters.where()
                    .equalTo("mAccountId", this.id)
                    .findAll();
//            mCharacters.addAll(currentChars);

            if (!inOuterTransaction)
                realm.commitTransaction();
        }
        return mCharacters;
    }
}
