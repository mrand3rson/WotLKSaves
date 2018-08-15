package com.funprojects.wotlksaves.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Andrei on 15.05.2018.
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface ContactsView extends MvpView {
    void onItemMoved(int adapterIndex);
    void onItemAdded();
}
