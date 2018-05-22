package com.funprojects.wotlksaves.mvp.models;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Andrei on 14.05.2018.
 */

public class Instances extends RealmObject implements Parcelable {

    public final static byte naxx10 = 0;
    public final static byte naxx25 = 1;
    public final static byte os10 = 2;
    public final static byte os25 = 3;
    public final static byte ulduar10 = 4;
    public final static byte ulduar25 = 5;
    public final static byte onyxia10 = 6;
    public final static byte onyxia25 = 7;
    public final static byte va10 = 8;
    public final static byte va25 = 9;
    public final static byte toc10 = 10;
    public final static byte togc10 = 11;
    public final static byte toc25 = 12;
    public final static byte togc25 = 13;
    public final static byte icc10 = 14;
    public final static byte icc10h = 15;
    public final static byte icc25 = 16;
    public final static byte icc25h = 17;
    public final static byte rs10 = 18;
    public final static byte rs10h = 19;
    public final static byte rs25 = 20;
    public final static byte rs25h = 21;
    public RealmList<Boolean> saves;


    public Instances() {

    }

    public Instances(boolean naxx10, boolean naxx25,
                     boolean os10, boolean os25,
                     boolean ulduar10, boolean ulduar25,
                     boolean onyxia10, boolean onyxia25,
                     boolean va10, boolean va25,
                     boolean toc10, boolean togc10,
                     boolean toc25, boolean togc25,
                     boolean icc10, boolean icc10h,
                     boolean icc25, boolean icc25h,
                     boolean rs10, boolean rs10h,
                     boolean rs25, boolean rs25h) {
        saves.set(Instances.naxx10, naxx10);
        saves.set(Instances.naxx25, naxx25);
        saves.set(Instances.os10, os10);
        saves.set(Instances.os25, os25);
        saves.set(Instances.ulduar10, ulduar10);
        saves.set(Instances.ulduar25, ulduar25);
        saves.set(Instances.onyxia10, onyxia10);
        saves.set(Instances.onyxia25, onyxia25);
        saves.set(Instances.va10, va10);
        saves.set(Instances.va25, va25);
        saves.set(Instances.toc10, toc10);
        saves.set(Instances.togc10, togc10);
        saves.set(Instances.toc25, toc25);
        saves.set(Instances.togc25, togc25);
        saves.set(Instances.icc10, icc10);
        saves.set(Instances.icc10h, icc10h);
        saves.set(Instances.icc25, icc25);
        saves.set(Instances.icc25h, icc25h);
        saves.set(Instances.rs10, rs10);
        saves.set(Instances.rs10h, rs10h);
        saves.set(Instances.rs25, rs25);
        saves.set(Instances.rs25h, rs25h);
    }

    public int countCheckedInstances() {
        int counter = 0;
        for (boolean b : saves) {
            if (b) counter++;
        }
        return counter;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flag) {
        byte[] bytes = new byte[saves.size()];
        for (int i = 0; i < saves.size(); i++) {
            bytes[i] = (saves.get(i) ? (byte)1: (byte)0);
        }
        parcel.writeByteArray(bytes);

//        parcel.writeByte(naxx10? (byte)1: (byte)0);
//        parcel.writeByte(naxx25? (byte)1: (byte)0);
//        parcel.writeByte(os10? (byte)1: (byte)0);
//        parcel.writeByte(os25? (byte)1: (byte)0);
//        parcel.writeByte(ulduar10? (byte)1: (byte)0);
//        parcel.writeByte(ulduar25? (byte)1: (byte)0);
//        parcel.writeByte(onyxia10? (byte)1: (byte)0);
//        parcel.writeByte(onyxia25? (byte)1: (byte)0);
//        parcel.writeByte(va10? (byte)1: (byte)0);
//        parcel.writeByte(va25? (byte)1: (byte)0);
//        parcel.writeByte(toc10? (byte)1: (byte)0);
//        parcel.writeByte(togc10? (byte)1: (byte)0);
//        parcel.writeByte(toc25? (byte)1: (byte)0);
//        parcel.writeByte(togc25? (byte)1: (byte)0);
//        parcel.writeByte(icc10? (byte)1: (byte)0);
//        parcel.writeByte(icc10h? (byte)1: (byte)0);
//        parcel.writeByte(icc25? (byte)1: (byte)0);
//        parcel.writeByte(icc25h? (byte)1: (byte)0);
//        parcel.writeByte(rs10? (byte)1: (byte)0);
//        parcel.writeByte(rs10h? (byte)1: (byte)0);
//        parcel.writeByte(rs25? (byte)1: (byte)0);
//        parcel.writeByte(rs25h? (byte)1: (byte)0);
    }

    protected Instances(Parcel in) {
        byte[] bytes = new byte[saves.size()];
        in.readByteArray(bytes);
        for (int i = 0; i < saves.size(); i++) {
            saves.set(i, bytes[i] != 0);
        }
//        naxx10 = in.readByte() != 0;
//        naxx25 = in.readByte() != 0;
//        os10 = in.readByte() != 0;
//        os25 = in.readByte() != 0;
//        ulduar10 = in.readByte() != 0;
//        ulduar25 = in.readByte() != 0;
//        onyxia10 = in.readByte() != 0;
//        onyxia25 = in.readByte() != 0;
//        va10 = in.readByte() != 0;
//        va25 = in.readByte() != 0;
//        toc10 = in.readByte() != 0;
//        togc10 = in.readByte() != 0;
//        toc25 = in.readByte() != 0;
//        togc25 = in.readByte() != 0;
//        icc10 = in.readByte() != 0;
//        icc10h = in.readByte() != 0;
//        icc25 = in.readByte() != 0;
//        icc25h = in.readByte() != 0;
//        rs10 = in.readByte() != 0;
//        rs10h = in.readByte() != 0;
//        rs25 = in.readByte() != 0;
//        rs25h = in.readByte() != 0;
    }

    public static final Creator<Instances> CREATOR = new Creator<Instances>() {
        @Override
        public Instances createFromParcel(Parcel in) {
            return new Instances(in);
        }

        @Override
        public Instances[] newArray(int size) {
            return new Instances[size];
        }
    };
}
