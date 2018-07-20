package com.funprojects.wotlksaves.ui.fragments;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.funprojects.wotlksaves.R;
import com.funprojects.wotlksaves.mvp.models.BlacklistRecord;
import com.funprojects.wotlksaves.mvp.models.ListRecord;
import com.funprojects.wotlksaves.mvp.presenters.ContactsPresenter;
import com.funprojects.wotlksaves.mvp.views.ContactsBlackView;
import com.funprojects.wotlksaves.mvp.views.ContactsView;
import com.funprojects.wotlksaves.tools.ContactsSortEngine;
import com.funprojects.wotlksaves.tools.ListTypes;
import com.funprojects.wotlksaves.ui.activities.MainActivity;
import com.funprojects.wotlksaves.ui.adapters.recyclers.ListRecordsAdapter;
import com.funprojects.wotlksaves.ui.adapters.recyclers.VerticalSpaceItemDecoration;
import com.funprojects.wotlksaves.ui.dialogs.AddRecordDialog;
import com.funprojects.wotlksaves.tools.SortTypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;


public class ContactsBlacklistFragment extends TabFragment
        implements ContactsView {

    @BindDimen(R.dimen.recycler_item_vertical_space)
    int mVerticalSpacing;

    @BindView(R.id.blacklist_recycler)
    RecyclerView mRecycler;
    ListRecordsAdapter mAdapter;

    @InjectPresenter
    ContactsPresenter mPresenter;


    public ContactsBlacklistFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contacts_blacklist, container, false);
        ButterKnife.bind(this, v);
        initRecycler();
        return v;
    }

    private void initRecycler() {
        if (mAdapter == null) {
            MainActivity activity = (MainActivity) getActivity();

            ArrayList<ListRecord> blacklist = new ArrayList<>();
            blacklist.addAll(mPresenter.getBlacklist(activity));

            mAdapter = new ListRecordsAdapter(activity, R.layout.recycler_blacklist_item, blacklist);
            mRecycler.setLayoutManager(new LinearLayoutManager(activity));
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
    public void updateList(RealmList<ListRecord> list) {
        mPresenter.blackData = list;

        mAdapter.data.clear();
        mAdapter.data.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void clearFilter() {
        mAdapter.data.clear();
        mAdapter.data.addAll(mPresenter.blackData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void filterAdapterData(String text) {
        if (mAdapter.data != null) {
            mAdapter.data = mPresenter.filterRecyclerItems(text, ListTypes.BLACK);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void sortAdapterData(int sortType) {
        Comparator<ListRecord> comparator =
                ContactsSortEngine.getComparatorByType(sortType);

        if (comparator != null) {
            Collections.sort(mAdapter.data, comparator);
            mAdapter.notifyDataSetChanged();
        }
        mPresenter.currentBlackComparator = comparator;
    }
}
