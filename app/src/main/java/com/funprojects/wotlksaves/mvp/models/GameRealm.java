package com.funprojects.wotlksaves.mvp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.funprojects.wotlksaves.tools.ListTypes;

import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Andrei on 29.04.2018.
 */

public class GameRealm extends RealmObject implements Parcelable {

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

    public String getName() {
        return mName;
    }

    public String getServerName() {
        return mServerName;
    }
    public void setServerName(String serverName) {
        this.mServerName = serverName;
    }

    public void setBlacklistFromOld(RealmList<BlacklistRecord> blacklistOld) {
        Realm realm = Realm.getDefaultInstance();
        if (mBlacklist == null) {
            mBlacklist = new RealmList<>();
        }
        for (BlacklistRecord oldRecord : blacklistOld) {
            ListRecord newRecord = new ListRecord(
                    oldRecord.id,
                    oldRecord.getName(),
                    oldRecord.getReasons(),
                    getFactionFromReasons(oldRecord.getReasons()),
                    oldRecord.getWhereSeen(),
                    oldRecord.getTimesCaught(),
                    ListTypes.BLACK,
                    oldRecord.getGameRealmId()
            );

            mBlacklist.add(newRecord);
        }
        realm.copyToRealm(mBlacklist);
    }

    private boolean getFactionFromReasons(RealmList<String> reasons) {
        return reasons.get(0).toLowerCase().startsWith("о ");
    }

    public RealmList<ListRecord> getBlacklist() {
        if (mBlacklist == null || mBlacklist.isEmpty()) {
            mBlacklist = new RealmList<>();
            Realm realm = Realm.getDefaultInstance();
            boolean inOuterTransaction = realm.isInTransaction();
            if (!inOuterTransaction)
                realm.beginTransaction();

            mBlacklist.where()
                    .equalTo("mGameRealmId", this.id)
                    .equalTo("mListType", ListTypes.BLACK)
                    .findAll();

            if (!inOuterTransaction)
                realm.commitTransaction();
        }
        return mBlacklist;
    }


    public RealmList<ListRecord> getWhitelist() {
        if (mWhitelist == null || mWhitelist.isEmpty()) {
            mWhitelist = new RealmList<>();
            Realm realm = Realm.getDefaultInstance();
            boolean inOuterTransaction = realm.isInTransaction();
            if (!inOuterTransaction) {
                realm.beginTransaction();
            }

            mWhitelist.where()
                    .equalTo("mGameRealmId", this.id)
                    .equalTo("mListType", ListTypes.WHITE)
                    .findAll();

            if (!inOuterTransaction) {
                realm.commitTransaction();
            }
        }
        return mWhitelist;
    }
    public RealmList<Account> getAccounts() {
        if (mAccounts == null || mAccounts.isEmpty()) {
            mAccounts = new RealmList<>();
            Realm realm = Realm.getDefaultInstance();
            boolean inOuterTransaction = realm.isInTransaction();
            if (!inOuterTransaction)
                realm.beginTransaction();

            mAccounts.where()
                    .equalTo("mGameRealmId", id)
                    .findAll();

            if (!inOuterTransaction)
                realm.commitTransaction();
        }
        return mAccounts;
    }

    @PrimaryKey @Index
    public long id;
    private String mName;
    private String mServerName;
    private RealmList<ListRecord> mBlacklist;
    private RealmList<ListRecord> mWhitelist;
    private RealmList<Account> mAccounts;


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
