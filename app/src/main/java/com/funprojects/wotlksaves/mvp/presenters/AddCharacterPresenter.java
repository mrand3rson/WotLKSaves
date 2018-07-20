package com.funprojects.wotlksaves.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.funprojects.wotlksaves.mvp.models.Account;
import com.funprojects.wotlksaves.mvp.models.GameCharacter;
import com.funprojects.wotlksaves.mvp.models.GameRealm;
import com.funprojects.wotlksaves.mvp.models.Server;
import com.funprojects.wotlksaves.mvp.views.AddCharacterView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Andrei on 02.07.2018.
 */
@InjectViewState
public class AddCharacterPresenter extends MvpPresenter<AddCharacterView> {

    public ArrayList<Account> getAccountList(GameRealm gameRealm) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RealmResults<Account> results = realm.where(Account.class)
                //TODO: remake
                .equalTo("mGameRealmId", gameRealm.id).findAll();

        realm.commitTransaction();
        if (results != null)
            return (ArrayList<Account>) realm.copyFromRealm(results);
        return new ArrayList<>(0);
    }

    //we find characters by nickname, then check if
    // these characters belong to any of the
    // current realm's accounts
    public boolean findCharacter(String nickname, GameRealm gameRealm) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RealmResults<GameCharacter> results = realm.where(GameCharacter.class)
                .equalTo("mNickname", nickname)
                .findAll();

        realm.commitTransaction();

        for (GameCharacter character : results) {
            for (Account acc : gameRealm.getAccounts()) {
                if (character.getAccountId() == acc.id) {
                    return true;
                }
            }
        }
        return false;
    }

    public void saveCharacter(GameCharacter character) {
        getViewState().onSuccess(character);
    }

    public void saveAccount(Account account, GameRealm gameRealm) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        gameRealm.getAccounts().add(account);

        realm.commitTransaction();
    }
}
