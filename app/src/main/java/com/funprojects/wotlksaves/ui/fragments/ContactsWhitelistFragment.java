package com.funprojects.wotlksaves.ui.fragments;


import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.funprojects.wotlksaves.R;
import com.funprojects.wotlksaves.mvp.models.Instances;
import com.funprojects.wotlksaves.mvp.models.ListRecord;
import com.funprojects.wotlksaves.mvp.presenters.ContactsPresenter;
import com.funprojects.wotlksaves.mvp.views.ContactsView;
import com.funprojects.wotlksaves.tools.ContactsSortEngine;
import com.funprojects.wotlksaves.tools.ListTypes;
import com.funprojects.wotlksaves.ui.activities.MainActivity;
import com.funprojects.wotlksaves.ui.adapters.recyclers.ListRecordsAdapter;
import com.funprojects.wotlksaves.ui.adapters.recyclers.VerticalSpaceItemDecoration;
import com.funprojects.wotlksaves.ui.dialogs.AddRecordDialog;
import com.funprojects.wotlksaves.ui.dialogs.InstancesDialog;
import com.funprojects.wotlksaves.ui.dialogs.RecordContextMenuDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;


public class ContactsWhitelistFragment extends TabFragment
        implements ContactsView {

    public ContactsPresenter getPresenter() {
        return mPresenter;
    }

    @BindDimen(R.dimen.recycler_item_vertical_space)
    int mVerticalSpacing;

    @BindView(R.id.whitelist_recycler)
    RecyclerView mRecycler;
    private ListRecordsAdapter mAdapter;

    @InjectPresenter
    ContactsPresenter mPresenter;

    private MainActivity mActivity;


    public ContactsWhitelistFragment() {

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
            whitelist.addAll(mPresenter.getWhitelist(mActivity));

            mAdapter = new ListRecordsAdapter(this, mActivity, R.layout.recycler_whitelist_item, whitelist);
            mRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
            mRecycler.addItemDecoration(new VerticalSpaceItemDecoration(mVerticalSpacing));
            mRecycler.setAdapter(mAdapter);
        }
    }

    @Override
    public void addRecord() {
        AddRecordDialog dialog = new AddRecordDialog();
        dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_Dialog_Light);
        Bundle bundle = new Bundle();
        bundle.putInt(this.getClass().getSimpleName(), 0);
        dialog.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .add(dialog, String.valueOf(dialog.getId()))
                .commit();
    }

    @Override
    public void updateAdapterList(RealmList<ListRecord> list) {
        mPresenter.whiteData = list;

        refreshAdapterList();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshAdapterList() {
//        mAdapter.data.clear();
//        mAdapter.data.addAll(mPresenter.whiteData);
        if (mAdapter.data.size() < mPresenter.whiteData.size()) {
            int index = mPresenter.whiteData.size()-1;
            mAdapter.data.add(mPresenter.whiteData.get(index));

            if (!sortAdapterList(mPresenter.currentWhiteComparator))
                mAdapter.notifyItemInserted(index);
        }
    }

    @Override
    public void clearAdapterFilter() {
        mAdapter.data.clear();
        mAdapter.data.addAll(mPresenter.whiteData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void filterAdapterList(String text) {
        if (mAdapter.data != null) {
            mAdapter.data = mPresenter.filterRecyclerItems(text, ListTypes.WHITE);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void sortAdapterList(int sortType) {
        Comparator<ListRecord> comparator =
                ContactsSortEngine.getComparatorByType(sortType);

        if (comparator != null) {
            Collections.sort(mAdapter.data, comparator);
            mAdapter.notifyDataSetChanged();
        }
        mPresenter.currentWhiteComparator = comparator;
    }

    public boolean sortAdapterList(Comparator<ListRecord> comparator) {
        if (comparator != null) {
            Collections.sort(mAdapter.data, comparator);
            mAdapter.notifyDataSetChanged();

            mPresenter.currentWhiteComparator = comparator;
            return true;
        }

        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ListRecordsAdapter.CODE_MENU: {
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

                break;
            }
        }
    }

    @Override
    public void onItemMoved(int adapterIndex) {
        Toast.makeText(mActivity, "Moved to blacklist", Toast.LENGTH_SHORT).show();
        mRecycler.removeViewAt(adapterIndex);
        mAdapter.data.remove(adapterIndex);
        mAdapter.notifyItemRemoved(adapterIndex);
    }
}
