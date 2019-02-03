package com.funprojects.wotlksaves;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.funprojects.wotlksaves.mvp.models.Account;
import com.funprojects.wotlksaves.mvp.models.BlacklistRecord;
import com.funprojects.wotlksaves.mvp.models.GameCharacter;
import com.funprojects.wotlksaves.mvp.models.GameRealm;
import com.funprojects.wotlksaves.mvp.models.ListRecord;
import com.funprojects.wotlksaves.mvp.models.realm.RealmMigrations;
import com.funprojects.wotlksaves.mvp.models.realm.RealmRestorer;
import com.funprojects.wotlksaves.mvp.models.Server;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

import static com.funprojects.wotlksaves.tools.CharacterConstraints.FACTION_HORDE;

/**
 * Created by Andrei on 17.04.2018.
 */

public class App extends Application {

    public final static int DB_VERSION = 17;


    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        //restoreSchemaVersion();
    }
}
