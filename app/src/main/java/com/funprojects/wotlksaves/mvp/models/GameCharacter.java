package com.funprojects.wotlksaves.mvp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

import static com.funprojects.wotlksaves.tools.CharacterConstraints.DRAENEI;

/**
 * Created by Andrei on 04.05.2018.
 */

public class GameCharacter extends RealmObject implements Parcelable, ICharacter {

    public static long setIdIncremented() {
        Realm realm = Realm.getDefaultInstance();
        boolean inOuterTransaction = realm.isInTransaction();
        if (!inOuterTransaction)
            realm.beginTransaction();

        Number maxId = realm.where(GameCharacter.class)
                .max("id");

        if (!inOuterTransaction)
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


    public void setNickname(String mNickname) {
        this.mNickname = mNickname;
    }

    public void setRace(byte mGameRace) {
        this.mGameRace = mGameRace;
    }

    public void setClass(byte mGameClass) {
        this.mGameClass = mGameClass;
    }

    public void setLevel(byte mLevel) {
        this.mLevel = mLevel;
    }

    public void setSpec1(byte mSpec1) {
        this.mSpec1 = mSpec1;
    }

    public void setSpec2(byte mSpec2) {
        this.mSpec2 = mSpec2;
    }

//    public String getAccountName() {
//        return mAccountName;
//    }
//    public void bindToAccount(String accountName) {
//        this.mAccountName = accountName;
//    }

    public long getAccountId() {
        return mAccountId;
    }

    public void setAccountId(long accountId) {
        this.mAccountId = accountId;
    }

    public Instances getSavedInstances() {
        return mSavedInstances;
    }
    public void bindSavedInstances(Instances newSavedInstances) {
        this.mSavedInstances = newSavedInstances;
    }

    public boolean isHordeFaction() {
        return mGameRace > DRAENEI;
    }

    @PrimaryKey @Index
    public long id;
    private String mNickname;
    private byte mGameRace;
    private byte mGameClass;
    private byte mLevel;
    private byte mSpec1;
    private byte mSpec2;

    private long mAccountId;
    private Instances mSavedInstances;


    public GameCharacter() {

    }

    public GameCharacter(String nickname,
                         byte gameRace,
                         byte gameClass,
                         byte level,
                         byte spec1,
                         byte spec2,
                         long accountId) {
        this.id = setIdIncremented();
        this.mNickname = nickname;
        this.mGameRace = gameRace;
        this.mGameClass = gameClass;
        this.mLevel = level;
        this.mSpec1 = spec1;
        this.mSpec2 = spec2;
        this.mAccountId = accountId;
//        this.mAccountName = accountName;
    }

//    public static RealmList<GameCharacter> getCharacters(Account account) {
//        RealmList<GameCharacter> result = new RealmList<>();
//        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//
//        result.where()
//                .equalTo("mAccountName", account.getName())
//                .findAll();
//
//        realm.commitTransaction();
//        return result;
//    }



    private GameCharacter(Parcel in) {
        id = in.readLong();
        mNickname = in.readString();
        mGameRace = in.readByte();
        mGameClass = in.readByte();
        mLevel = in.readByte();
        mSpec1 = in.readByte();
        mSpec2 = in.readByte();
        mAccountId = in.readLong();
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
        parcel.writeLong(mAccountId);
        parcel.writeParcelable(mSavedInstances, 0);
    }
}
