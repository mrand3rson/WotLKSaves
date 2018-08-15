package com.funprojects.wotlksaves.mvp.views.tab;

import com.funprojects.wotlksaves.mvp.models.ListRecord;

import io.realm.RealmList;

/**
 * Created by Andrei on 15.08.2018.
 */

public interface IAdapterUpdater {
    void updateAdapterList(RealmList<ListRecord> list);
    void refreshAdapterList();
}
