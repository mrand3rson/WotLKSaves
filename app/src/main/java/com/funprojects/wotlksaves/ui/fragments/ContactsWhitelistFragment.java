package com.funprojects.wotlksaves.ui.fragments;


import android.app.DialogFragment;
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
import com.funprojects.wotlksaves.mvp.views.ContactsView;
import com.funprojects.wotlksaves.tools.ContactsSortEngine;
import com.funprojects.wotlksaves.tools.ListTypes;
import com.funprojects.wotlksaves.ui.activities.MainActivity;
import com.funprojects.wotlksaves.ui.adapters.recyclers.ListRecordsAdapter;
import com.funprojects.wotlksaves.ui.adapters.recyclers.VerticalSpaceItemDecoration;
import com.funprojects.wotlksaves.ui.dialogs.AddRecordDialog;

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
    private ListRecordsAdapter mAdapter;

    @InjectPresenter
    ContactsPresenter mPresenter;


    public ContactsWhitelistFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contacts_whitelist, container, false);
        ButterKnife.bind(this, v);
        initRecycler();
        return v;
    }

    private void initRecycler() {
        if (mAdapter == null) {
            MainActivity activity = (MainActivity) getActivity();

            ArrayList<ListRecord> whitelist = new ArrayList<>();
            whitelist.addAll(mPresenter.getWhitelist(activity));

            mAdapter = new ListRecordsAdapter(activity, R.layout.recycler_whitelist_item, whitelist);
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
        mPresenter.whiteData = list;

        mAdapter.data.clear();
        mAdapter.data.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void clearFilter() {
        mAdapter.data.clear();
        mAdapter.data.addAll(mPresenter.whiteData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void filterAdapterData(String text) {
        if (mAdapter.data != null) {
            mAdapter.data = mPresenter.filterRecyclerItems(text, ListTypes.WHITE);
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
        mPresenter.currentWhiteComparator = comparator;
    }
}
