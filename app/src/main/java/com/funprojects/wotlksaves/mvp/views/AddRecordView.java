package com.funprojects.wotlksaves.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.funprojects.wotlksaves.mvp.models.ListRecord;

/**
 * Created by Andrei on 21.05.2018.
 */

@StateStrategyType(AddToEndSingleStrategy.class)
public interface AddRecordView extends MvpView {
    void warnExists(String name);
    void addToScreen(ListRecord record, byte listType);
}
