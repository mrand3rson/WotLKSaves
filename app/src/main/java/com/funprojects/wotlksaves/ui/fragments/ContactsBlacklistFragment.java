package com.funprojects.wotlksaves.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.funprojects.wotlksaves.R;
import com.funprojects.wotlksaves.mvp.models.ListRecord;
import com.funprojects.wotlksaves.mvp.views.ContactsView;
import com.funprojects.wotlksaves.tools.ListTypes;
import com.funprojects.wotlksaves.ui.activities.MainActivity;
import com.funprojects.wotlksaves.ui.adapters.recyclers.ListRecordsAdapter;
import com.funprojects.wotlksaves.ui.adapters.recyclers.VerticalSpaceItemDecoration;
import com.funprojects.wotlksaves.ui.dialogs.RecordContextMenuDialog;

import java.util.ArrayList;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;


public class ContactsBlacklistFragment extends TabFragment
        implements ContactsView {

    @BindDimen(R.dimen.recycler_item_vertical_space)
    int mVerticalSpacing;

    @BindView(R.id.blacklist_recycler)
    RecyclerView mRecycler;


    public ContactsBlacklistFragment() {
        setParentFragment(this);
        setListType(ListTypes.BLACK);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity = (MainActivity) getActivity();
        View v = inflater.inflate(R.layout.fragment_contacts_blacklist, container, false);
        ButterKnife.bind(this, v);
        initRecycler();
        return v;
    }

    private void initRecycler() {
        if (mAdapter == null) {

            ArrayList<ListRecord> blacklist = new ArrayList<>();
            blacklist.addAll(mPresenter.getList(mActivity, getListType()));

            mAdapter = new ListRecordsAdapter(this, mActivity, R.layout.recycler_blacklist_item, blacklist);
            mRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
            mRecycler.addItemDecoration(new VerticalSpaceItemDecoration(mVerticalSpacing));
            mRecycler.setAdapter(mAdapter);
        }
    }

    @Override
    protected void moveTo(Intent data) {
        int action = data.getIntExtra(RecordContextMenuDialog.ARG_ACTION, -1);
        int index = data.getIntExtra(RecordContextMenuDialog.ARG_INDEX, -1);
        int adapterIndex = data.getIntExtra(RecordContextMenuDialog.ARG_ADAPTER_INDEX, -1);
        switch (action) {
            case -1: {
                //TODO: log movement failed
                break;
            }
            default: {
                mPresenter.moveToWhitelist(index, adapterIndex, mActivity);
            }
        }
    }

    @Override
    public void onItemMoved(int adapterIndex) {
        Toast.makeText(mActivity, R.string.string_moved_to_whitelist, Toast.LENGTH_SHORT).show();
        mRecycler.removeViewAt(adapterIndex);
        mAdapter.data.remove(adapterIndex);
        mAdapter.notifyItemRemoved(adapterIndex);
    }

    @Override
    public void onItemAdded() {
        Toast.makeText(mActivity,R.string.string_added, Toast.LENGTH_SHORT).show();
    }
}
