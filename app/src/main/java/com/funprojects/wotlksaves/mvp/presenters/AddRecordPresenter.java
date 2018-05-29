package com.funprojects.wotlksaves.mvp.presenters;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.funprojects.wotlksaves.mvp.models.Instances;
import com.funprojects.wotlksaves.mvp.models.ListRecord;
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

    public void addListRecord(Context context,
                              byte listType,
                              String nickname,
                              String... reason) {
        ListRecord record = persistIfNotExists(context, listType, nickname, reason);
        getViewState().addToScreen(record, listType);
    }

    private ListRecord persistIfNotExists(Context context,
                                          byte listType,
                                          String nickname,
                                          String... reasonsRaw) {
        RealmList<String> reasons = new RealmList<>();
        reasons.addAll(Arrays.asList(reasonsRaw));
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        ListRecord result = realm.where(ListRecord.class)
                .equalTo("mName", nickname)
                .findFirst();

        realm.commitTransaction();
        //if not exists then create new
        if (result == null) {
            Instances instances = new Instances();
            instances.saves = new RealmList<>();
            MainActivity activity = (MainActivity) context;
            result = new ListRecord(
                    nickname,
                    reasons,
                    instances,
                    1,
                    listType,
                    activity.getGameRealm().id
            );
            return result;
        } else {
            getViewState().warnExists(result.getName());
            return null;
        }
    }
}
