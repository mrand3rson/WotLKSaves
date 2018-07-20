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
                long restoredVersion = Realm.getDefaultConfiguration().getSchemaVersion();
                Realm.setDefaultConfiguration(configuration);
                initGameRealm();
                if (DB_VERSION >= 13 && restoredVersion < 13) {
                    makeFactionForHordeRecords();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Realm.setDefaultConfiguration(configuration);
            }
        } else {
            Realm.setDefaultConfiguration(configuration);

//            removeAccountsAndCharacters();
        }
    }

    private void removeAccountsAndCharacters() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        realm.delete(Account.class);
        realm.delete(GameCharacter.class);

        realm.commitTransaction();
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

    private void makeFactionForHordeRecords() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RealmResults<ListRecord> records = realm.where(ListRecord.class).findAll();
        for (ListRecord record : records) {
            String reasons = "";
            for (String reason : record.getReasons()) {
                reasons = reasons.concat(reason);
            }
            if (reasons.contains("О ")) {
                record.setFaction(FACTION_HORDE);
            }
        }

        realm.commitTransaction();
    }

    private void initGameRealm() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        Server server = new Server("WoWCircle");
        GameRealm gameRealm = new GameRealm("x5", "WoWCircle");
        RealmList<BlacklistRecord> blacklistOld = new RealmList<>();
        RealmResults<BlacklistRecord> results = realm.where(BlacklistRecord.class)
                .equalTo("mGameRealmId", gameRealm.id)
                .findAll();
        blacklistOld.addAll(results);
        gameRealm.setBlacklistFromOld(blacklistOld);
        if (realm.where(Server.class)
                .equalTo("mName", "WoWCircle")
                .findFirst() == null) {
            realm.copyToRealm(server);
        }
        realm.copyToRealmOrUpdate(gameRealm);

        realm.commitTransaction();
    }
}
