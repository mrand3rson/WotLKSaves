package com.funprojects.wotlksaves.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.funprojects.wotlksaves.R;
import com.funprojects.wotlksaves.mvp.models.ListRecord;
import com.funprojects.wotlksaves.mvp.presenters.ContactsPresenter;
import com.funprojects.wotlksaves.mvp.views.tab.ITab;
import com.funprojects.wotlksaves.mvp.views.ContactsView;
import com.funprojects.wotlksaves.tools.ContactsSortEngine;
import com.funprojects.wotlksaves.tools.ListTypes;
import com.funprojects.wotlksaves.ui.activities.MainActivity;
import com.funprojects.wotlksaves.ui.adapters.recyclers.ListRecordsAdapter;
import com.funprojects.wotlksaves.ui.adapters.recyclers.VerticalSpaceItemDecoration;
import com.funprojects.wotlksaves.ui.dialogs.RecordContextMenuDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;


public class ContactsWhitelistFragment extends TabFragment
        implements ContactsView {

    @BindDimen(R.dimen.recycler_item_vertical_space)
    int mVerticalSpacing;

    @BindView(R.id.whitelist_recycler)
    RecyclerView mRecycler;


    public ContactsWhitelistFragment() {
        setListType(ListTypes.WHITE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = (MainActivity) getActivity();
        View v = inflater.inflate(R.layout.fragment_contacts_whitelist, container, false);
        ButterKnife.bind(this, v);
        initRecycler();
        return v;
    }

    private void initRecycler() {
        if (mAdapter == null) {
            ArrayList<ListRecord> whitelist = new ArrayList<>();
            whitelist.addAll(mPresenter.getList(mActivity, getListType()));

            mAdapter = new ListRecordsAdapter(this, mActivity, R.layout.recycler_whitelist_item, whitelist);
            mRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
            mRecycler.addItemDecoration(new VerticalSpaceItemDecoration(mVerticalSpacing));
            mRecycler.setAdapter(mAdapter);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TabFragment.REQUEST_MOVE: {
                moveToBlack(data);
                break;
            }
            case TabFragment.REQUEST_ADD_ITEM: {
                onItemAdded();
                break;
            }
        }
    }

    public void moveToBlack(Intent data) {
        int action = data.getIntExtra(RecordContextMenuDialog.ARG_ACTION, -1);
        int index = data.getIntExtra(RecordContextMenuDialog.ARG_INDEX, -1);
        int adapterIndex = data.getIntExtra(RecordContextMenuDialog.ARG_ADAPTER_INDEX, -1);
        switch (action) {
            case -1: {
                //TODO: log movement failed
                break;
            }
            default: {
                mPresenter.moveToBlacklist(index, adapterIndex, mActivity);
            }
        }
    }

    @Override
    public void onItemMoved(int adapterIndex) {
        Toast.makeText(mActivity, R.string.string_moved_to_blacklist, Toast.LENGTH_SHORT).show();
        mRecycler.removeViewAt(adapterIndex);
        mAdapter.data.remove(adapterIndex);
        mAdapter.notifyItemRemoved(adapterIndex);
    }

    @Override
    public void onItemAdded() {
        Toast.makeText(mActivity,R.string.string_added, Toast.LENGTH_SHORT).show();
    }
}
