package com.funprojects.wotlksaves.ui.fragments;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.funprojects.wotlksaves.mvp.models.ListRecord;
import com.funprojects.wotlksaves.mvp.presenters.ContactsPresenter;

import io.realm.RealmList;

/**
 * Created by Andrei on 15.05.2018.
 */

public abstract class TabFragment extends MvpAppCompatFragment {

    public int getTabPosition() {
        return mTabPosition;
    }

    public ContactsPresenter getPresenter() {
        return mPresenter;
    }

    private int mTabPosition;
    ContactsPresenter mPresenter;


    public TabFragment() {

    }

    public TabFragment(int position) {
        mTabPosition = position;
    }


    public abstract void addRecord();
    public abstract void updateAdapterList(RealmList<ListRecord> list);
    public abstract void refreshAdapterList();
    public abstract void filterAdapterList(String text);
    public abstract void clearAdapterFilter();
    public abstract void sortAdapterList(int sortType);
}
