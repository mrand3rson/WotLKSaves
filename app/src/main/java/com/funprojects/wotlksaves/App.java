package com.funprojects.wotlksaves;

import android.app.Application;
import android.os.Environment;

import com.funprojects.wotlksaves.mvp.models.GameRealm;
import com.funprojects.wotlksaves.mvp.models.RealmMigrations;
import com.funprojects.wotlksaves.mvp.models.RealmRestorer;
import com.funprojects.wotlksaves.mvp.models.Server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static com.funprojects.wotlksaves.mvp.models.RealmRestorer.IMPORT_REALM_NAME;

/**
 * Created by Andrei on 17.04.2018.
 */

public class App extends Application {

    public final static int DB_VERSION = 7;


    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        restoreSchemaVersion();
    }

    public void restoreSchemaVersion() {
        if (isNeededInit()) { //add check if first launch
            RealmConfiguration configuration = new RealmConfiguration.Builder()
                    .migration(new RealmMigrations())
                    .schemaVersion(DB_VERSION)
                    .build();
            RealmRestorer.restore(getApplicationContext());

            Realm.setDefaultConfiguration(configuration);
            initGameRealm();
//            try {
//                Realm.migrateRealm(configuration);
//                initGameRealm();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
        }
    }

    private boolean isNeededInit() {
//        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//
//        GameRealm wowCircleX5 = realm.where(GameRealm.class)
//                .equalTo("id", 1)
//                .findFirst();
//        realm.commitTransaction();
//
//        return wowCircleX5 == null;
        return true;
    }

    private void initGameRealm() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        Server server = new Server("WoWCircle");
        GameRealm gameRealm = new GameRealm("x5", "WoWCircle");
        gameRealm.id = 1;

        if (realm.where(Server.class)
                .equalTo("mName", "WoWCircle")
                .findFirst() == null) {
            realm.copyToRealm(server);
        }
        realm.copyToRealm(gameRealm);

        realm.commitTransaction();
        realm.close();
    }
}
