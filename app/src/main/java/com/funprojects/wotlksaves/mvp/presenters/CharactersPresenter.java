package com.funprojects.wotlksaves.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.funprojects.wotlksaves.mvp.models.Account;
import com.funprojects.wotlksaves.mvp.models.GameCharacter;
import com.funprojects.wotlksaves.mvp.models.GameRealm;
import com.funprojects.wotlksaves.mvp.views.CharactersView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrei on 05.05.2018.
 */
@InjectViewState
public class CharactersPresenter extends MvpPresenter<CharactersView> {
    public List<GameCharacter> getCharacters(GameRealm gameRealm) {
        List<Account> accounts = Account.getAccounts(gameRealm);

        List<GameCharacter> characters = new ArrayList<>();
        for (Account account : accounts) {
            characters.addAll(GameCharacter.getCharacters(account));
        }
        return characters;
    }
}
