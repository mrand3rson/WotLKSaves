package com.funprojects.wotlksaves.mvp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Andrei on 29.04.2018.
 */

public class GameRealm extends RealmObject implements Parcelable {

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

    public String getServerName() {
        return mServerName;
    }
    public void setServerName(String serverName) {
        this.mServerName = serverName;
    }

    public RealmList<BlacklistRecord> getBlacklist() {
        if (mBlacklist == null || mBlacklist.isEmpty()) {
            mBlacklist = new RealmList<>();
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            RealmResults<BlacklistRecord> realmResults =
                    realm.where(BlacklistRecord.class)
                            .equalTo("mGameRealmId", this.id).findAll();
            mBlacklist.addAll(realmResults);

            realm.commitTransaction();
        }
        return mBlacklist;
    }
    public RealmList<WhitelistRecord> getWhitelist() {
        if (mWhitelist == null | mBlacklist.isEmpty()) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

//            RealmResults<WhitelistRecord> realmResults =
//                    realm.where(WhitelistRecord.class)
//                            .equalTo("mGameRealmId", this.id).findAll();
//            mWhitelist.addAll(realmResults);
            mWhitelist.where().equalTo("mGameRealmId", this.id).findAll();

            realm.commitTransaction();
        }
        return mWhitelist;
    }


    @PrimaryKey @Index
    public long id;
    private String mName;
    private String mServerName;

    private RealmList<BlacklistRecord> mBlacklist;
    private RealmList<WhitelistRecord> mWhitelist;


    public GameRealm() {

    }


    public GameRealm(String name) {
        this.mName = name;
    }

    public GameRealm(String name, String serverName) {
        this.id = setIdIncremented();
        this.mName = name;
        this.mServerName = serverName;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s %s (%d)", mServerName, mName, id);
    }

    public static GameRealm byId(long id) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        GameRealm gameRealm = realm.where(GameRealm.class)
                .equalTo("id", id).findFirst();

        realm.commitTransaction();
        realm.close();
        return gameRealm;
    }

    //
    //PARCELABLE IMPLEMENTATION
    //
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeString(mServerName);
    }

    private GameRealm(Parcel in) {
        mName = in.readString();
        mServerName = in.readString();
    }

    public static final Creator<GameRealm> CREATOR = new Creator<GameRealm>() {
        @Override
        public GameRealm createFromParcel(Parcel in) {
            return new GameRealm(in);
        }

        @Override
        public GameRealm[] newArray(int size) {
            return new GameRealm[size];
        }
    };
}
