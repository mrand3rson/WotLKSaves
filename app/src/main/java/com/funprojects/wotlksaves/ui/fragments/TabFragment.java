package com.funprojects.wotlksaves.ui.fragments;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.funprojects.wotlksaves.R;
import com.funprojects.wotlksaves.mvp.models.ListRecord;
import com.funprojects.wotlksaves.mvp.presenters.ContactsPresenter;
import com.funprojects.wotlksaves.mvp.views.ContactsView;
import com.funprojects.wotlksaves.mvp.views.tab.ITab;
import com.funprojects.wotlksaves.tools.ContactsSortEngine;
import com.funprojects.wotlksaves.ui.activities.MainActivity;
import com.funprojects.wotlksaves.ui.adapters.recyclers.ListRecordsAdapter;
import com.funprojects.wotlksaves.ui.dialogs.AddRecordDialog;

import java.util.Collections;
import java.util.Comparator;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.RealmList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Andrei on 15.05.2018.
 */

//created non-abstract because of moxy presenter (one presenter for all tabs)
public class TabFragment extends MvpAppCompatFragment implements ITab, ContactsView {

    public final static int REQUEST_MOVE = 1;
    public final static int REQUEST_ADD_ITEM = 2;

    public void setParentFragment(Fragment parentFragment) {
        this.parentFragment = parentFragment;
    }
    private Fragment parentFragment;

    public int getListType() {
        return mListType;
    }
    protected void setListType(int listType) {
        mListType = listType;
    }
    protected int mListType;

    @InjectPresenter
    public ContactsPresenter mPresenter;
    protected ListRecordsAdapter mAdapter;
    protected MainActivity mActivity;


    public void addRecord() {
        AddRecordDialog dialog = new AddRecordDialog();
        dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_Dialog_Light);
        Bundle bundle = new Bundle();
        bundle.putInt(this.getClass().getSimpleName(), 0);
        dialog.setArguments(bundle);
        dialog.setTargetFragment(parentFragment, REQUEST_ADD_ITEM);
        getFragmentManager().beginTransaction()
                .add(dialog, String.valueOf(dialog.getId()))
                .commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TabFragment.REQUEST_MOVE: {
                switch (resultCode) {
                    case RESULT_OK: {
                        moveTo(data);
                        break;
                    }
                }
                break;
            }
            case TabFragment.REQUEST_ADD_ITEM: {
                switch (resultCode) {
                    case RESULT_OK: {
                        onItemAdded();
                        break;
                    }
                }
                break;
            }
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void updateAdapterList(RealmList<ListRecord> list) {
        mPresenter.setList(list, getListType());
        refreshAdapterList();
    }
    @Override
    public void refreshAdapterList() {
        RealmList<ListRecord> list = mPresenter.getList(mActivity, getListType());

        if (mAdapter.data.size() < list.size()) {
            int index = list.size()-1;
            mAdapter.data.add(list.get(index));

            //A.S. sortAdapterList(...) has notifyDataSetChanged()
            if (!sortAdapterList(mPresenter.getComparator(getListType())))
                mAdapter.notifyItemInserted(index);
        }
    }

    protected void moveTo(Intent data) {
        //stub. Implement in child fragment
    }

    @Override
    public void filterAdapterList(String text) {
        if (mAdapter.data != null) {
//            Observable.fromIterable(mAdapter.data)
//                    .observeOn(Schedulers.newThread())
//                    .subscribeOn(AndroidSchedulers.mainThread())
//                    .filter(listRecord -> {
//                        String nameLower = listRecord.getName().toLowerCase();
//                        String reasonLower = listRecord.getReasons().toString().toLowerCase();
//                        return nameLower.contains(...) || reasonLower.contains(...)
//                    })
//                    .subscribe();
            mAdapter.data = mPresenter.filterRecyclerItems(text, getListType());
            mAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void clearAdapterFilter() {
        mAdapter.data.clear();
        mAdapter.data.addAll(mPresenter.getList(mActivity, getListType()));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void sortAdapterList(int sortType) {
        Comparator<ListRecord> comparator =
                ContactsSortEngine.getComparatorByType(sortType);

        if (comparator != null) {
            Collections.sort(mAdapter.data, comparator);
            mAdapter.notifyDataSetChanged();
        }
        mPresenter.setComparator(comparator, getListType());
    }
    private boolean sortAdapterList(Comparator<ListRecord> comparator) {
        if (comparator != null) {
            Collections.sort(mAdapter.data, comparator);
            mAdapter.notifyDataSetChanged();

            mPresenter.setComparator(comparator, getListType());
            return true;
        }

        return false;
    }

    //TODO: add itemchangelistener
    //TODO: rework moveTo

    @Override
    public void onItemMoved(int adapterIndex) {
        //stub. Implement in child fragment
    }
    @Override
    public void onItemAdded() {
        //stub. Implement in child fragment
    }
}