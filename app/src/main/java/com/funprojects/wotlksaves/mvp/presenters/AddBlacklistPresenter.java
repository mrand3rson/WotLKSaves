package com.funprojects.wotlksaves.mvp.presenters;

import android.app.Activity;
import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.funprojects.wotlksaves.mvp.models.BlacklistRecord;
import com.funprojects.wotlksaves.mvp.views.AddBlacklistView;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Andrei on 21.05.2018.
 */

@InjectViewState
public class AddBlacklistPresenter extends MvpPresenter<AddBlacklistView> {

    public void addBlacklistRecord(Context context,
                                   String nickname,
                                   String reason) {
        BlacklistRecord record = persistBlacklistRecord(nickname, reason);
        getViewState().updateBlacklist(record);
    }

    private BlacklistRecord persistBlacklistRecord(String nickname, String reason) {
        RealmList<String> reasons = new RealmList<>();
        reasons.add(reason);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        BlacklistRecord existingRecord = realm.where(BlacklistRecord.class)
                .equalTo("name", nickname).findFirst();
        BlacklistRecord record = null;
        if (existingRecord == null) {
            record = new BlacklistRecord(nickname, reasons, 1);
            realm.copyToRealm(record);
        }

        realm.commitTransaction();
        return record;
    }
}
