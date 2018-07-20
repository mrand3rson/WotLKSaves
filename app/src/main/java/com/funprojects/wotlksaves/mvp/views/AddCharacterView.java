package com.funprojects.wotlksaves.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.funprojects.wotlksaves.mvp.models.GameCharacter;

/**
 * Created by Andrei on 02.07.2018.
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface AddCharacterView extends MvpView {
    void onSuccess(GameCharacter character);
}
