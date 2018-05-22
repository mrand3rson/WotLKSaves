package com.funprojects.wotlksaves.mvp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Andrei on 04.05.2018.
 */

public class GameCharacter extends RealmObject implements Parcelable, ICharacter {

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

    public String getNickname() {
        return mNickname;
    }

    public byte getGameRace() {
        return mGameRace;
    }

    public byte getGameClass() {
        return mGameClass;
    }

    public byte getLevel() {
        return mLevel;
    }

    public byte getSpec1() {
        return mSpec1;
    }

    public byte getSpec2() {
        return mSpec2;
    }

    public String getAccountName() {
        return mAccountName;
    }
    public void bindToAccount(String accountName) {
        this.mAccountName = accountName;
    }

    public Instances getSavedInstances() {
        return mSavedInstances;
    }
    public void bindSavedInstances(Instances newSavedInstances) {
        this.mSavedInstances = newSavedInstances;
    }

    @PrimaryKey @Index
    public long id;
    private String mNickname;
    private byte mGameRace;
    private byte mGameClass;
    private byte mLevel;
    private byte mSpec1;
    private byte mSpec2;

    private String mAccountName;
    private Instances mSavedInstances;


    public GameCharacter() {

    }

    public GameCharacter(String nickname,
                         byte gameRace,
                         byte gameClass,
                         byte level,
                         byte spec1,
                         byte spec2) {
        this.id = setIdIncremented();
        this.mNickname = nickname;
        this.mGameRace = gameRace;
        this.mGameClass = gameClass;
        this.mLevel = level;
        this.mSpec1 = spec1;
        this.mSpec2 = spec2;
    }

    public static List<GameCharacter> getCharacters(Account account) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RealmResults<GameCharacter> characterRealmResults =
                realm.where(GameCharacter.class)
                    .equalTo("mAccountName", account.getName())
                    .findAll();

        realm.commitTransaction();
        return realm.copyFromRealm(characterRealmResults);
    }



    protected GameCharacter(Parcel in) {
        id = in.readLong();
        mNickname = in.readString();
        mGameRace = in.readByte();
        mGameClass = in.readByte();
        mLevel = in.readByte();
        mSpec1 = in.readByte();
        mSpec2 = in.readByte();
        mAccountName = in.readString();
        mSavedInstances = in.readParcelable(Instances.class.getClassLoader());
    }

    public static final Creator<GameCharacter> CREATOR = new Creator<GameCharacter>() {
        @Override
        public GameCharacter createFromParcel(Parcel in) {
            return new GameCharacter(in);
        }

        @Override
        public GameCharacter[] newArray(int size) {
            return new GameCharacter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(mNickname);
        parcel.writeByte(mGameRace);
        parcel.writeByte(mGameClass);
        parcel.writeByte(mLevel);
        parcel.writeByte(mSpec1);
        parcel.writeByte(mSpec2);
        parcel.writeString(mAccountName);
        parcel.writeParcelable(mSavedInstances, 0);
    }
}
