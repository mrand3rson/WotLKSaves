package com.funprojects.wotlksaves.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.funprojects.wotlksaves.mvp.models.Instances;
import com.funprojects.wotlksaves.mvp.models.ListRecord;
import com.funprojects.wotlksaves.mvp.views.InstancesView;

import io.realm.Realm;

/**
 * Created by Andrei on 02.08.2018.
 */
@InjectViewState
public class InstancesPresenter extends MvpPresenter<InstancesView> {

    public ListRecord getRecord(long id) {
        if (mRecord == null) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            mRecord = realm.where(ListRecord.class)
                    .equalTo("id", id)
                    .findFirst();

            realm.commitTransaction();
        }
        return mRecord;
    }
    private ListRecord mRecord;

    public void saveNewPlaces(Instances newInstances) {
        if (mRecord != null) {
            mRecord.setPlaces(newInstances.saves);
        } else {
            //TODO: log wrong id inside onCreate InstancesDialog
        }
    }
}
