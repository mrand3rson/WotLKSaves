package com.funprojects.wotlksaves.ui.fragments;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.funprojects.wotlksaves.mvp.models.ListRecord;

import io.realm.RealmList;

/**
 * Created by Andrei on 15.05.2018.
 */

public abstract class TabFragment extends MvpAppCompatFragment {

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

    public abstract void updateList(RealmList<ListRecord> list);

    public abstract void clearFilter();

    public abstract void filterAdapterData(String text);

    public abstract void sortAdapterData(int sortType);
}
