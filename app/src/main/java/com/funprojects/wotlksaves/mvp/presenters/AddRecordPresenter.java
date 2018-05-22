package com.funprojects.wotlksaves.mvp.presenters;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.funprojects.wotlksaves.mvp.models.BlacklistRecord;
import com.funprojects.wotlksaves.mvp.models.Instances;
import com.funprojects.wotlksaves.mvp.models.WhitelistRecord;
import com.funprojects.wotlksaves.mvp.views.AddRecordView;
import com.funprojects.wotlksaves.ui.activities.MainActivity;

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
        BlacklistRecord record = persistBlackIfNotExists(context, nickname, reason);
        getViewState().addToBlacklist(record);
    }

    public void addWhitelistRecord(Context context,
                                   String nickname,
                                   String... reason) {
        WhitelistRecord record = persistWhiteIfNotExists(context, nickname, reason);
        getViewState().addToWhitelist(record);
    }

    private WhitelistRecord persistWhiteIfNotExists(Context context,
                                                    String nickname,
                                                    String... reasonsRaw) {
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
            MainActivity activity = (MainActivity) context;
            result = new WhitelistRecord(nickname, reasons, instances, activity.getGameRealm().id);
            return result;
        } else {
            getViewState().warnExists(result.getName());
            return null;
        }
    }

    private BlacklistRecord persistBlackIfNotExists(Context context,
                                                    String nickname,
                                                    String... reasonsRaw) {
        RealmList<String> reasons = new RealmList<>();
        reasons.addAll(Arrays.asList(reasonsRaw));
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        BlacklistRecord result = realm.where(BlacklistRecord.class)
                .equalTo("mName", nickname).findFirst();

        realm.commitTransaction();
        //if not exists then create new
        if (result == null) {
            MainActivity activity = (MainActivity) context;
            result = new BlacklistRecord(nickname, reasons, 1, activity.getGameRealm().id);
            return result;
        } else {
            getViewState().warnExists(result.getName());
            return null;
        }
    }
}
