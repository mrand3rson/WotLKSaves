package com.funprojects.wotlksaves.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.funprojects.wotlksaves.mvp.models.WhitelistRecord;
import com.funprojects.wotlksaves.mvp.views.ContactsWhiteView;
import com.funprojects.wotlksaves.ui.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by Andrei on 22.05.2018.
 */
@InjectViewState
public class ContactsWhitePresenter extends MvpPresenter<ContactsWhiteView> {

    public RealmList<WhitelistRecord> data;


    public List<WhitelistRecord> getWhitelist(MainActivity activity) {
        if (data == null) {
            data = activity.getGameRealm().getWhitelist();
        }
        return data;
    }

    public ArrayList<WhitelistRecord> filterRecyclerItems(String text) {
        ArrayList<WhitelistRecord> filtered = new ArrayList<>();
        for (WhitelistRecord record: data) {
            if (record.getName().toLowerCase()
                    .contains(text.toLowerCase())) {
                filtered.add(record);
            }
        }
        return filtered;
    }
}
