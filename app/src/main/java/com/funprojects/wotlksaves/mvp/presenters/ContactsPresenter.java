package com.funprojects.wotlksaves.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.funprojects.wotlksaves.mvp.models.ListRecord;
import com.funprojects.wotlksaves.mvp.views.ContactsView;
import com.funprojects.wotlksaves.tools.ListTypes;
import com.funprojects.wotlksaves.ui.activities.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.realm.RealmList;

/**
 * Created by Andrei on 27.05.2018.
 */
@InjectViewState
public class ContactsPresenter extends MvpPresenter<ContactsView> {

    public Comparator<ListRecord> currentBlackComparator;
    public Comparator<ListRecord> currentWhiteComparator;
    public RealmList<ListRecord> blackData;
    public RealmList<ListRecord> whiteData;


    public RealmList<ListRecord> getBlacklist(MainActivity activity) {
        if (blackData == null) {
            blackData = activity.getGameRealm().getBlacklist();
        }
        return blackData;
    }

    public RealmList<ListRecord> getWhitelist(MainActivity activity) {
        if (whiteData == null) {
            whiteData = activity.getGameRealm().getWhitelist();
        }
        return whiteData;
    }

    public ArrayList<ListRecord> filterRecyclerItems(String text, int type) {
        ArrayList<ListRecord> filtered = new ArrayList<>();
        if (type == ListTypes.BLACK) {
            for (ListRecord record : blackData) {
                if (isMatchingSearch(record, text)) {
                    filtered.add(record);
                }
            }
        } else {
            for (ListRecord record : whiteData) {
                if (isMatchingSearch(record, text)) {
                    filtered.add(record);
                }
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
        Comparator<ListRecord> comparator = null;
        switch (type) {
            case ListTypes.BLACK: {
                comparator = currentBlackComparator;
                break;
            }
            case ListTypes.WHITE: {
                comparator = currentWhiteComparator;
                break;
            }
        }

        if (comparator != null) {
            Collections.sort(filtered, comparator);
//            Observable.fromIterable(filtered)
//                    .sorted(comparator)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(listRecord -> {
//                        ((Activity)getViewState()).runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                sorted.add(listRecord);
//                            }
//                        });
//                    });
        }
        return filtered;
    }
}

