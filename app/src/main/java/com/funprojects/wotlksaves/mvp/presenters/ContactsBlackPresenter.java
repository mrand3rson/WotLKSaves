package com.funprojects.wotlksaves.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.funprojects.wotlksaves.mvp.models.BlacklistRecord;
import com.funprojects.wotlksaves.mvp.models.WhitelistRecord;
import com.funprojects.wotlksaves.mvp.views.ContactsBlackView;
import com.funprojects.wotlksaves.ui.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by Andrei on 22.05.2018.
 */
@InjectViewState
public class ContactsBlackPresenter extends MvpPresenter<ContactsBlackView> {

    public RealmList<BlacklistRecord> data;


    public RealmList<BlacklistRecord> getBlacklist(MainActivity activity) {
        if (data == null) {
            data = activity.getGameRealm().getBlacklist();
        }
        return data;
    }

    public ArrayList<BlacklistRecord> filterRecyclerItems(String text) {
        return filter(text);
    }

    private ArrayList<BlacklistRecord> filter(String text) {
        ArrayList<BlacklistRecord> filtered = new ArrayList<>();
        for (BlacklistRecord record: data) {
            if (record.getName().toLowerCase()
                    .contains(text.toLowerCase())) {
                filtered.add(record);
            }
        }
        return filtered;
    }
}
