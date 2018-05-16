package com.funprojects.wotlksaves.mvp.models;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * Created by Andrei on 14.05.2018.
 */

public class Instances extends RealmObject implements Parcelable {

    public boolean naxx10;
    public boolean naxx25;
    public boolean os10;
    public boolean os25;
    public boolean ulduar10;
    public boolean ulduar25;
    public boolean onyxia10;
    public boolean onyxia25;
    public boolean va10;
    public boolean va25;
    public boolean toc10;
    public boolean togc10;
    public boolean toc25;
    public boolean togc25;
    public boolean icc10;
    public boolean icc10h;
    public boolean icc25;
    public boolean icc25h;
    public boolean rs10;
    public boolean rs10h;
    public boolean rs25;
    public boolean rs25h;


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
        this.naxx10 = naxx10;
        this.naxx25 = naxx25;
        this.os10 = os10;
        this.os25 = os25;
        this.ulduar10 = ulduar10;
        this.ulduar25 = ulduar25;
        this.onyxia10 = onyxia10;
        this.onyxia25 = onyxia25;
        this.va10 = va10;
        this.va25 = va25;
        this.toc10 = toc10;
        this.togc10 = togc10;
        this.toc25 = toc25;
        this.togc25 = togc25;
        this.icc10 = icc10;
        this.icc10h = icc10h;
        this.icc25 = icc25;
        this.icc25h = icc25h;
        this.rs10 = rs10;
        this.rs10h = rs10h;
        this.rs25 = rs25;
        this.rs25h = rs25h;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(naxx10? (byte)1: (byte)0);
        parcel.writeByte(naxx25? (byte)1: (byte)0);
        parcel.writeByte(os10? (byte)1: (byte)0);
        parcel.writeByte(os25? (byte)1: (byte)0);
        parcel.writeByte(ulduar10? (byte)1: (byte)0);
        parcel.writeByte(ulduar25? (byte)1: (byte)0);
        parcel.writeByte(onyxia10? (byte)1: (byte)0);
        parcel.writeByte(onyxia25? (byte)1: (byte)0);
        parcel.writeByte(va10? (byte)1: (byte)0);
        parcel.writeByte(va25? (byte)1: (byte)0);
        parcel.writeByte(toc10? (byte)1: (byte)0);
        parcel.writeByte(togc10? (byte)1: (byte)0);
        parcel.writeByte(toc25? (byte)1: (byte)0);
        parcel.writeByte(togc25? (byte)1: (byte)0);
        parcel.writeByte(icc10? (byte)1: (byte)0);
        parcel.writeByte(icc10h? (byte)1: (byte)0);
        parcel.writeByte(icc25? (byte)1: (byte)0);
        parcel.writeByte(icc25h? (byte)1: (byte)0);
        parcel.writeByte(rs10? (byte)1: (byte)0);
        parcel.writeByte(rs10h? (byte)1: (byte)0);
        parcel.writeByte(rs25? (byte)1: (byte)0);
        parcel.writeByte(rs25h? (byte)1: (byte)0);
    }

    protected Instances(Parcel in) {
        naxx10 = in.readByte() != 0;
        naxx25 = in.readByte() != 0;
        os10 = in.readByte() != 0;
        os25 = in.readByte() != 0;
        ulduar10 = in.readByte() != 0;
        ulduar25 = in.readByte() != 0;
        onyxia10 = in.readByte() != 0;
        onyxia25 = in.readByte() != 0;
        va10 = in.readByte() != 0;
        va25 = in.readByte() != 0;
        toc10 = in.readByte() != 0;
        togc10 = in.readByte() != 0;
        toc25 = in.readByte() != 0;
        togc25 = in.readByte() != 0;
        icc10 = in.readByte() != 0;
        icc10h = in.readByte() != 0;
        icc25 = in.readByte() != 0;
        icc25h = in.readByte() != 0;
        rs10 = in.readByte() != 0;
        rs10h = in.readByte() != 0;
        rs25 = in.readByte() != 0;
        rs25h = in.readByte() != 0;
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
