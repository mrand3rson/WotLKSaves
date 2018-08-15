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

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Andrei on 27.05.2018.
 */
@InjectViewState
public class ContactsPresenter extends MvpPresenter<ContactsView> {

    private RealmList<ListRecord> getBlacklist(MainActivity activity) {
        if (blackData == null) {
            blackData = activity.getGameRealm().getBlacklist();
        }
        return blackData;
    }
    private RealmList<ListRecord> getWhitelist(MainActivity activity) {
        if (whiteData == null) {
            whiteData = activity.getGameRealm().getWhitelist();
        }
        return whiteData;
    }
    public void setBlackData(RealmList<ListRecord> blackData) {
        this.blackData = blackData;
    }
    public void setWhiteData(RealmList<ListRecord> whiteData) {
        this.whiteData = whiteData;
    }
    private RealmList<ListRecord> blackData;
    private RealmList<ListRecord> whiteData;

    private Comparator<ListRecord> getBlackComparator() {
        return blackComparator;
    }
    private Comparator<ListRecord> getWhiteComparator() {
        return whiteComparator;
    }
    public Comparator<ListRecord> getComparator(int type) {
        switch (type) {
            case ListTypes.BLACK: {
                return getBlackComparator();
            }
            case ListTypes.WHITE: {
                return getWhiteComparator();
            }
            default: {
                return null;
            }
        }
    }
    private void setBlackComparator(Comparator<ListRecord> blackComparator) {
        this.blackComparator = blackComparator;
    }
    private void setWhiteComparator(Comparator<ListRecord> whiteComparator) {
        this.whiteComparator = whiteComparator;
    }
    public void setComparator(Comparator<ListRecord> comparator, int type) {
        switch (type) {
            case ListTypes.BLACK: {
                setBlackComparator(comparator);
                break;
            }
            case ListTypes.WHITE: {
                setWhiteComparator(comparator);
                break;
            }
        }
    }
    private Comparator<ListRecord> blackComparator;
    private Comparator<ListRecord> whiteComparator;


    public RealmList<ListRecord> getList(MainActivity activity, int type) {
        switch (type) {
            case ListTypes.BLACK: {
                return getBlacklist(activity);
            }
            case ListTypes.WHITE: {
                return getWhitelist(activity);
            }
            default:
                return null;
        }
    }
    public void setList(RealmList<ListRecord> data, int type) {
        switch (type) {
            case ListTypes.BLACK: {
                this.blackData = data;
                break;
            }
            case ListTypes.WHITE: {
                this.whiteData = data;
                break;
            }
            default: {}
        }
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
        Comparator<ListRecord> comparator = getComparator(type);

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

    public void moveToBlacklist(int index, int adapterIndex, MainActivity activity) {
        ListRecord record = whiteData.get(index);
        if (record != null) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            record.setListType(ListTypes.BLACK);
            whiteData.remove(record);
            getBlacklist(activity).add(record);

            realm.commitTransaction();
            getViewState().onItemMoved(adapterIndex);
        }
    }

    public void moveToWhitelist(int index, int adapterIndex, MainActivity activity) {
        ListRecord record = blackData.get(index);
        if (record != null) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            record.setListType(ListTypes.WHITE);
            blackData.remove(record);
            getWhitelist(activity).add(record);

            realm.commitTransaction();
            getViewState().onItemMoved(adapterIndex);
        }
    }
}

