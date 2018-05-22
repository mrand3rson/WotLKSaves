package com.funprojects.wotlksaves.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.funprojects.wotlksaves.mvp.models.Server;
import com.funprojects.wotlksaves.mvp.views.AddServerView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Andrei on 29.04.2018.
 */
@InjectViewState
public class AddServerPresenter extends MvpPresenter<AddServerView> {

    public ArrayList<Server> getServerList() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RealmResults<Server> results = realm.where(Server.class).findAll();

        realm.commitTransaction();
        if (results != null)
            return (ArrayList<Server>) realm.copyFromRealm(results);
        return new ArrayList<>(0);
    }
}
