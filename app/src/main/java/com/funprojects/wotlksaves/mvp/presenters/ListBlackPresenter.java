package com.funprojects.wotlksaves.mvp.presenters;

import com.arellomobile.mvp.MvpPresenter;
import com.funprojects.wotlksaves.mvp.models.ListRecord;
import com.funprojects.wotlksaves.mvp.views.ContactsView;
import com.funprojects.wotlksaves.ui.activities.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.realm.RealmList;

/**
 * Created by Andrei on 15.08.2018.
 */

public class ListBlackPresenter extends MvpPresenter<ContactsView> {
    public Comparator<ListRecord> comparator;

    public RealmList<ListRecord> getList(MainActivity activity) {
        if (blackData == null) {
            blackData = activity.getGameRealm().getBlacklist();
        }
        return blackData;
    }
    public void setList(RealmList<ListRecord> newList) {
        blackData = newList;
    }
    public RealmList<ListRecord> blackData;


    public ArrayList<ListRecord> filterRecyclerItems(String text, int type) {
        ArrayList<ListRecord> filtered = new ArrayList<>();
        for (ListRecord record : blackData) {
            if (isMatchingSearch(record, text)) {
                filtered.add(record);
            }
        }
        //sort filtered list
        //TODO: delete if causes performance issues
        return sort(filtered, type);
    }

    private boolean isMatchingSearch(ListRecord record, String text) {
        String reasons = "";
        for (String reason : record.getReasons())
            reasons = reasons.concat(reason);
        String recordData = record.getName().concat("; " + reasons);
        return recordData.toLowerCase().contains(text.toLowerCase());
    }

    private ArrayList<ListRecord> sort(ArrayList<ListRecord> filtered, int type) {
        Comparator<ListRecord> comparator = this.comparator;
        if (comparator != null) {
            Collections.sort(filtered, comparator);
        }
        return filtered;
    }

//    public void moveToWhitelist(int index, int adapterIndex, MainActivity activity) {
//        ListRecord record = blackData.get(index);
//        if (record != null) {
//            Realm realm = Realm.getDefaultInstance();
//            realm.beginTransaction();
//
//            record.setListType(ListTypes.WHITE);
//            blackData.remove(record);
//            // FIXME: refactor. Maybe add "int type"
////            getWhitelist(activity).add(record);
//
//            realm.commitTransaction();
//            getViewState().onItemMoved(adapterIndex);
//        }
//    }
}
