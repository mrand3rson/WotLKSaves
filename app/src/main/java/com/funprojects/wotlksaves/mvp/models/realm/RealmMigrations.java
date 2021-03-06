package com.funprojects.wotlksaves.mvp.models.realm;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmResults;
import io.realm.RealmSchema;

/**
 * Created by Andrei on 29.04.2018.
 */

public class RealmMigrations implements RealmMigration {


    private static long initId = 1;
    private static boolean isInitializedFromOld;


    public RealmMigrations() {

    }

    @Override
    public void migrate(@NonNull DynamicRealm realm, long oldVersion, long newVersion) {
        final RealmSchema schema = realm.getSchema();

        long currentVersion = 0;

        //created BlacklistRecord
        if (currentVersion < 1) {

            isInitializedFromOld = true;
            currentVersion++;
        }

        //created GameRealm
        //created Server
        if (currentVersion < 2) {
            schema.create("GameRealm")
                    .addField("mName", String.class, FieldAttribute.PRIMARY_KEY)
                    .addField("mServerName", String.class);
            schema.create("Server")
                    .addField("mName", String.class, FieldAttribute.PRIMARY_KEY);
            currentVersion++;
        }

        //created Account
        //created GameCharacter
        if (currentVersion < 3) {
            schema.create("Account")
                    .addField("mName", String.class, FieldAttribute.PRIMARY_KEY);
            schema.create("GameCharacter")
                    .addField("id", long.class, FieldAttribute.PRIMARY_KEY)
                    .addField("mNickname", String.class)
                    .addField("mGameRace", byte.class)
                    .addField("mGameClass", byte.class)
                    .addField("mLevel", byte.class)
                    .addField("mSpec1", byte.class)
                    .addField("mSpec2", byte.class)
                    .addField("mAccountName", String.class);
            currentVersion++;
        }

        //re-created GameCharacter (new id)
        if (currentVersion < 4) {
            schema.remove("GameCharacter");
            schema.create("GameCharacter")
                    .addField("id", long.class, FieldAttribute.PRIMARY_KEY)
                    .addField("mNickname", String.class)
                    .addField("mGameRace", byte.class)
                    .addField("mGameClass", byte.class)
                    .addField("mLevel", byte.class)
                    .addField("mSpec1", byte.class)
                    .addField("mSpec2", byte.class)
                    .addField("mAccountName", String.class)
                    .addRealmObjectField("mGameRealm", schema.get("GameRealm"));
            currentVersion++;
        }

        //updated Account (FK gameCharacterName)

        //updated Blacklist (String reason -> List<String> reasons)
        //updated Blacklist (int timesCaught)

        //created Instances

        //updated GameCharacter (mGameRealm -> null, added mInstances)

        //created WhitelistRecord

        //updated Account (GameRealm gameRealm -> long gameRealmId)

        //created Note
        if (currentVersion < 5) {
            schema.get("Account")
                    .addField("mGameRealmName", String.class);

            schema.get("BlacklistRecord")
                    .addIndex("name")
                    .addPrimaryKey("name")
                    .addRealmListField("reasons", String.class)
                    .transform(obj -> {
                        String reasonText = obj.getString("reason");
                        RealmList<String> migratedReasons = obj.getList("reasons", String.class);
                        migratedReasons.add(reasonText);
                    });
            schema.get("BlacklistRecord")
                    .addField("timesCaught", int.class);

            schema.create("Instances")
                    .addField("naxx10", boolean.class)
                    .addField("naxx25", boolean.class)
                    .addField("os10", boolean.class)
                    .addField("os25", boolean.class)
                    .addField("ulduar10", boolean.class)
                    .addField("ulduar25", boolean.class)
                    .addField("onyxia10", boolean.class)
                    .addField("onyxia25", boolean.class)
                    .addField("va10", boolean.class)
                    .addField("va25", boolean.class)
                    .addField("toc10", boolean.class)
                    .addField("togc10", boolean.class)
                    .addField("toc25", boolean.class)
                    .addField("togc25", boolean.class)
                    .addField("icc10", boolean.class)
                    .addField("icc10h", boolean.class)
                    .addField("icc25", boolean.class)
                    .addField("icc25h", boolean.class)
                    .addField("rs10", boolean.class)
                    .addField("rs10h", boolean.class)
                    .addField("rs25", boolean.class)
                    .addField("rs25h", boolean.class);

            schema.get("GameCharacter")
                    .removeField("mGameRealm")
                    .addRealmObjectField("mSavedInstances", schema.get("Instances"));

            schema.create("WhitelistRecord")
                    .addField("mName", String.class, FieldAttribute.PRIMARY_KEY)
                    .addField("mReason", String.class)
                    .addRealmObjectField("mWhereSeen", schema.get("Instances"));

            schema.create("Note")
                    .addField("id", long.class, FieldAttribute.PRIMARY_KEY)
                    .addField("mType", byte.class)
                    .addField("mText", String.class)
                    .addField("mDone", boolean.class)
                    .addField("mGameCharacterId", long.class);

            currentVersion++;
        }

        //altered GameRealm (Blacklist+Whitelist)
        if (currentVersion < 6) {
            schema.get("GameRealm")
                    .addRealmListField("mBlacklist", schema.get("BlacklistRecord"))
                    .addRealmListField("mWhitelist", schema.get("WhitelistRecord"));

            currentVersion++;
        }

        //altered Account, GameRealm, BlacklistRecord (added id
        if (currentVersion < 7) {
            schema.get("Account")
                    .removePrimaryKey()
                    .addField("id", long.class, FieldAttribute.PRIMARY_KEY)
                    .transform(new RealmObjectSchema.Function() {
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            obj.setLong("id", initId);
                            initId++;
                        }
                    });
            initId = 2;
            schema.get("GameRealm")
                    .removePrimaryKey()
                    .addField("id", long.class, FieldAttribute.PRIMARY_KEY)
                    .transform(new RealmObjectSchema.Function() {
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            obj.setLong("id", initId);
                            initId++;
                        }
                    });
            initId = 1;
            schema.get("BlacklistRecord")
                    .removePrimaryKey()
                    .addField("id", long.class)
                    .addField("mGameRealmId", long.class)
                    .transform(new RealmObjectSchema.Function() {
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            obj.setLong("id", initId);
                            initId++;
                            obj.setLong("mGameRealmId", 1);
                        }
                    })
                    .addPrimaryKey("id");
            schema.get("WhitelistRecord")
                    .removePrimaryKey()
                    .addField("id", long.class)
                    .addField("mGameRealmId", long.class)
                    .addPrimaryKey("id");

            currentVersion++;
        }

        if (currentVersion < 8) {
            schema.get("WhitelistRecord")
                    .addRealmListField("mReasons", String.class)
                    .removeField("mReason");
        }

        if (currentVersion < 9) {
            schema.get("Instances")
                    .addRealmListField("saves", Boolean.class)
                    .removeField("naxx10")
                    .removeField("naxx25")
                    .removeField("os10")
                    .removeField("os25")
                    .removeField("ulduar10")
                    .removeField("ulduar25")
                    .removeField("onyxia10")
                    .removeField("onyxia25")
                    .removeField("va10")
                    .removeField("va25")
                    .removeField("toc10")
                    .removeField("togc10")
                    .removeField("toc25")
                    .removeField("togc25")
                    .removeField("icc10")
                    .removeField("icc10h")
                    .removeField("icc25")
                    .removeField("icc25h")
                    .removeField("rs10")
                    .removeField("rs10h")
                    .removeField("rs25")
                    .removeField("rs25h");
        }

        if (currentVersion < 10) {
            schema.get("BlacklistRecord")
                    .addField("mName", String.class)
                    .addRealmListField("mReasons", String.class)
                    .addField("mTimesCaught", int.class)
                    .transform(new RealmObjectSchema.Function() {
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            obj.setString("mName", obj.getString("name"));
                            obj.setList("mReasons", obj.getList("reasons", String.class));
                            obj.setInt("mTimesCaught", obj.getInt("timesCaught"));
                        }
                    })
                    .removeField("name")
                    .removeField("reasons")
                    .removeField("timesCaught");
        }
    }
}
