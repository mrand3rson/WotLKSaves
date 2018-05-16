package com.funprojects.wotlksaves.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Andrei on 15.05.2018.
 */

public abstract class TabFragment extends Fragment {

    public int getTabPosition() {
        return mTabPosition;
    }

    private int mTabPosition;


    public TabFragment() {

    }

    public TabFragment(int position) {
        mTabPosition = position;
    }


    public abstract void addRecord();
}
