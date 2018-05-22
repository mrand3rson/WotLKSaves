package com.funprojects.wotlksaves;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.funprojects.wotlksaves.mvp.models.GameRealm;
import com.funprojects.wotlksaves.mvp.models.realm.RealmMigrations;
import com.funprojects.wotlksaves.mvp.models.realm.RealmRestorer;
import com.funprojects.wotlksaves.mvp.models.Server;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Andrei on 17.04.2018.
 */

public class App extends Application {

    public final static int DB_VERSION = 8;
    private final static String FIRST_LAUNCH = "FIRST_LAUNCH";


    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        restoreSchemaVersion();
    }

    public void restoreSchemaVersion() {
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .migration(new RealmMigrations())
                .schemaVersion(DB_VERSION)
                .build();
        if (isNeededInit()) {
            try {
                RealmRestorer.restore(getApplicationContext());
                Realm.setDefaultConfiguration(configuration);
                initGameRealm();
            } catch (IOException e) {
                e.printStackTrace();
                Realm.setDefaultConfiguration(configuration);
            }
        } else {
            Realm.setDefaultConfiguration(configuration);
        }
    }

    private boolean isNeededInit() {
        SharedPreferences preferences = getSharedPreferences("com.funprojects.wotlksaves", Context.MODE_PRIVATE);
        if (!preferences.getBoolean(FIRST_LAUNCH, false)) {
            preferences.edit()
                    .putBoolean(FIRST_LAUNCH, true)
                    .apply();
            return true;
        }
        return false;
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
