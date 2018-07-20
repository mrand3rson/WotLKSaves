package com.funprojects.wotlksaves.mvp.views;

import android.support.annotation.Nullable;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.funprojects.wotlksaves.mvp.models.ListRecord;

/**
 * Created by Andrei on 21.05.2018.
 */

@StateStrategyType(AddToEndSingleStrategy.class)
public interface AddRecordView extends MvpView {
    void warnExists(ListRecord name);
    void addToScreen(@Nullable ListRecord record, byte listType);
}
