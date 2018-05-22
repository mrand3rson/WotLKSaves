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
import com.funprojects.wotlksaves.mvp.presenters.ContactsBlackPresenter;
import com.funprojects.wotlksaves.mvp.views.ContactsBlackView;
import com.funprojects.wotlksaves.ui.activities.MainActivity;
import com.funprojects.wotlksaves.ui.adapters.recyclers.BlacklistAdapter;
import com.funprojects.wotlksaves.ui.adapters.recyclers.VerticalSpaceItemDecoration;
import com.funprojects.wotlksaves.ui.dialogs.AddRecordDialog;
import com.funprojects.wotlksaves.ui.dialogs.SortTypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;


public class ContactsBlacklistFragment extends TabFragment
        implements ContactsBlackView {

    @BindDimen(R.dimen.recycler_item_vertical_space)
    int mVerticalSpacing;

    @BindView(R.id.blacklist_recycler)
    RecyclerView mRecycler;
    BlacklistAdapter mAdapter;

    @InjectPresenter
    ContactsBlackPresenter mPresenter;


    public ContactsBlacklistFragment() {

    }

    public static ContactsBlacklistFragment newInstance() {
        ContactsBlacklistFragment fragment = new ContactsBlacklistFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//        }
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

            ArrayList<BlacklistRecord> blacklist = new ArrayList<>();
            blacklist.addAll(mPresenter.getBlacklist(activity));

            mAdapter = new BlacklistAdapter(activity, blacklist);
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
    public void updateList(RealmList list) {
        mPresenter.data = list;

        mAdapter.data.clear();
        mAdapter.data.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void clearFilter() {
        mAdapter.data.clear();
        mAdapter.data.addAll(mPresenter.data);
    }

    @Override
    public void filterAdapterData(String text) {
        mAdapter.data = mPresenter.filterRecyclerItems(text);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void sortAdapterData(int sortType) {
        Comparator<BlacklistRecord> comparator = null;
        switch (sortType) {
            case SortTypes.SORT_BY_NAME_ASC: {
                comparator = buildAscNameComparator();
                break;
            }
            case SortTypes.SORT_BY_NAME_DESC: {
                comparator = buildDescNameComparator();
                break;
            }
            case SortTypes.SORT_BY_DATE_ASC: {
                comparator = buildAscIdComparator();
                break;
            }
            case SortTypes.SORT_BY_DATE_DESC: {
                comparator = buildDescIdComparator();
                break;
            }
            case SortTypes.SORT_BY_TIMES_SEEN: {
                comparator = buildTimesSeenComparator();
                break;
            }
        }

        if (comparator != null) {
            Collections.sort(mAdapter.data, comparator);
            mAdapter.notifyDataSetChanged();
        }
    }

    private Comparator<BlacklistRecord> buildAscNameComparator() {
        return (r1, r2) -> r1.getName().toLowerCase()
                .compareTo(r2.getName().toLowerCase());
    }
    private Comparator<BlacklistRecord> buildDescNameComparator() {
        return (r1, r2) -> r2.getName().toLowerCase()
                .compareTo(r1.getName().toLowerCase());
    }
    private Comparator<BlacklistRecord> buildAscIdComparator() {
        return (r1, r2) -> Long.compare(r1.id, r2.id);
    }
    private Comparator<BlacklistRecord> buildDescIdComparator() {
        return (r1, r2) -> Long.compare(r2.id, r1.id);
    }
    private Comparator<BlacklistRecord> buildTimesSeenComparator() {
        return (r1, r2) -> {
            int i1 = r1.getTimesCaught();
            int i2 = r2.getTimesCaught();
            return Integer.compare(i1, i2);
        };
    }
}
