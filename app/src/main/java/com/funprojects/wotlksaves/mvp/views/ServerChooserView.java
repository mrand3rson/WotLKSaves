package com.funprojects.wotlksaves.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Andrei on 17.04.2018.
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface ServerChooserView extends MvpView {
    void onAddExistingRealm();
    void onAddSuccessful();
}
