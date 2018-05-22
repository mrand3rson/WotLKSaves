package com.funprojects.wotlksaves.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.funprojects.wotlksaves.mvp.models.BlacklistRecord;

/**
 * Created by Andrei on 21.05.2018.
 */

@StateStrategyType(AddToEndSingleStrategy.class)
public interface AddBlacklistView extends MvpView {
    void updateBlacklist(BlacklistRecord record);
}
