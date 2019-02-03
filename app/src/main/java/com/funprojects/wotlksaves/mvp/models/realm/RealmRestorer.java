package com.funprojects.wotlksaves.mvp.models.realm;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.funprojects.wotlksaves.mvp.models.Account;
import com.funprojects.wotlksaves.mvp.models.BlacklistRecord;
import com.funprojects.wotlksaves.mvp.models.GameCharacter;
import com.funprojects.wotlksaves.mvp.models.GameRealm;
import com.funprojects.wotlksaves.mvp.models.ListRecord;
import com.funprojects.wotlksaves.mvp.models.Server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

import static com.funprojects.wotlksaves.App.DB_VERSION;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.FACTION_HORDE;

/**
 * Created by Andrei on 16.05.2018.
 */

public class RealmRestorer {
    public final static int REQUEST_CODE_PERMISSION_STORAGE = 10;
    private final static String EXPORT_REALM_PATH =
            Environment.getExternalStorageDirectory().getPath().concat("/sample.realm");
    private final static String IMPORT_REALM_NAME = "default.realm";
    private final static String FIRST_LAUNCH = "FIRST_LAUNCH";
    private Activity mActivity;


    public RealmRestorer(Activity activity) {
        this.mActivity = activity;
    }


    public void restoreSchemaVersion() {
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .migration(new RealmMigrations())
                .schemaVersion(DB_VERSION)
                .build();
        if (!isInitCompleted()) {
            SharedPreferences preferences = mActivity.getSharedPreferences("com.funprojects.wotlksaves", Context.MODE_PRIVATE);
            preferences.edit()
                    .putBoolean(FIRST_LAUNCH, true)
                    .apply();
            try {
                this.restore();
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

    public boolean isInitCompleted() {
        SharedPreferences preferences = mActivity.getSharedPreferences("com.funprojects.wotlksaves", Context.MODE_PRIVATE);
        return preferences.getBoolean(FIRST_LAUNCH, false);
    }

    private static void removeAccountsAndCharacters() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        realm.delete(Account.class);
        realm.delete(GameCharacter.class);

        realm.commitTransaction();
    }

    private static void makeFactionForHordeRecords() {
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

    private static void initGameRealm() {
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



    private void restore() throws IOException {
        Context applicationContext = mActivity.getApplicationContext();
        copyRealmFromBackup(applicationContext, EXPORT_REALM_PATH, IMPORT_REALM_NAME);
    }

    private static void copyRealmFromBackup(Context applicationContext, String oldFilePath, String outFileName) throws IOException {
        File file = new File(applicationContext.getFilesDir(), outFileName);

        FileOutputStream outputStream = new FileOutputStream(file);
        FileInputStream inputStream = new FileInputStream(new File(oldFilePath));

        byte[] buf = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buf)) > 0) {
            outputStream.write(buf, 0, bytesRead);
        }
        outputStream.close();
    }
}
