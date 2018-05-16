package com.funprojects.wotlksaves.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.funprojects.wotlksaves.mvp.models.GameRealm;
import com.funprojects.wotlksaves.mvp.models.Server;
import com.funprojects.wotlksaves.mvp.views.ServerChooserView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Andrei on 17.04.2018.
 */
@InjectViewState
public class RealmChooserPresenter extends MvpPresenter<ServerChooserView> {

    public void addGameRealm(Server server, GameRealm realm) {
        saveToRealm(server);
        saveToRealm(realm);
    }

    private void saveToRealm(Server server) {
        final String field = "mName";

        Realm realm = Realm.getDefaultInstance();
        Server dbRecord = realm.where(Server.class)
                .equalTo(field, server.getName())
                .findFirst();
        if (dbRecord == null) {
            realm.beginTransaction();
            realm.copyToRealm(server);
            realm.commitTransaction();
        }
    }

    private void saveToRealm(GameRealm gameRealm) {
        final String field = "mName";

        Realm realm = Realm.getDefaultInstance();
        GameRealm dbRecord = realm.where(GameRealm.class)
                .equalTo(field, gameRealm.getName())
                .findFirst();
        if (dbRecord == null) {
            realm.beginTransaction();
            realm.copyToRealm(gameRealm);
            realm.commitTransaction();
            getViewState().onAddSuccessful();
        } else {
            getViewState().onAddExistingRealm();
        }
    }

    public ArrayList<GameRealm> getGameRealms() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<GameRealm> results = realm.where(GameRealm.class).findAll();
        realm.commitTransaction();
        return (ArrayList<GameRealm>) realm.copyFromRealm(results);
    }
}
