package com.funprojects.wotlksaves.mvp.presenters;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.funprojects.wotlksaves.mvp.models.BlacklistRecord;
import com.funprojects.wotlksaves.mvp.models.Instances;
import com.funprojects.wotlksaves.mvp.models.WhitelistRecord;
import com.funprojects.wotlksaves.mvp.views.AddRecordView;

import java.util.Arrays;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Andrei on 21.05.2018.
 */

@InjectViewState
public class AddRecordPresenter extends MvpPresenter<AddRecordView> {

    public void addBlacklistRecord(Context context,
                                   String nickname,
                                   String... reason) {
        BlacklistRecord record = persistBlackIfNotExists(nickname, reason);
        getViewState().addToBlacklist(record);
    }

    public void addWhitelistRecord(Context context,
                                   String nickname,
                                   String... reason) {
        WhitelistRecord record = persistWhiteIfNotExists(nickname, reason);
        getViewState().addToWhitelist(record);
    }

    private WhitelistRecord persistWhiteIfNotExists(String nickname, String... reasonsRaw) {
        RealmList<String> reasons = new RealmList<>();
        reasons.addAll(Arrays.asList(reasonsRaw));
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        WhitelistRecord result = realm.where(WhitelistRecord.class)
                .equalTo("mName", nickname).findFirst();

        realm.commitTransaction();
        //if not exists then create new
        if (result == null) {
            Instances instances = new Instances();
            instances.saves = new RealmList<>();
            result = new WhitelistRecord(nickname, reasons, instances);
            return result;
        } else {
            getViewState().warnExists(result.getName());
            return null;
        }
    }

    private BlacklistRecord persistBlackIfNotExists(String nickname, String... reasonsRaw) {
        RealmList<String> reasons = new RealmList<>();
        reasons.addAll(Arrays.asList(reasonsRaw));
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        BlacklistRecord result = realm.where(BlacklistRecord.class)
                .equalTo("mName", nickname).findFirst();

        realm.commitTransaction();
        //if not exists then create new
        if (result == null) {
            result = new BlacklistRecord(nickname, reasons, 1);
            return result;
        } else {
            getViewState().warnExists(result.getName());
            return null;
        }
    }
}
