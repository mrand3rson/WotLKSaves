package com.funprojects.wotlksaves.mvp.views.tab;

import com.funprojects.wotlksaves.mvp.models.ListRecord;

import java.util.Comparator;

/**
 * Created by Andrei on 15.08.2018.
 */

public interface ITab extends IAdapterUpdater, IAdapterFilter {
    void sortAdapterList(int sortType);
    boolean sortAdapterList(Comparator<ListRecord> comparator);
}
