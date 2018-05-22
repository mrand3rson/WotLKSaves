package com.funprojects.wotlksaves.ui.fragments;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.funprojects.wotlksaves.R;
import com.funprojects.wotlksaves.mvp.models.BlacklistRecord;
import com.funprojects.wotlksaves.mvp.models.WhitelistRecord;
import com.funprojects.wotlksaves.mvp.presenters.ContactsWhitePresenter;
import com.funprojects.wotlksaves.mvp.views.ContactsWhiteView;
import com.funprojects.wotlksaves.ui.activities.MainActivity;
import com.funprojects.wotlksaves.ui.adapters.recyclers.VerticalSpaceItemDecoration;
import com.funprojects.wotlksaves.ui.adapters.recyclers.WhitelistAdapter;
import com.funprojects.wotlksaves.ui.dialogs.AddRecordDialog;
import com.funprojects.wotlksaves.ui.dialogs.SortTypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactsWhitelistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsWhitelistFragment extends TabFragment
        implements ContactsWhiteView {

    @BindDimen(R.dimen.recycler_item_vertical_space)
    int mVerticalSpacing;

    @BindView(R.id.whitelist_recycler)
    RecyclerView mRecycler;
    private WhitelistAdapter mAdapter;

    @InjectPresenter
    ContactsWhitePresenter mPresenter;


    public ContactsWhitelistFragment() {

    }

    public static ContactsWhitelistFragment newInstance() {
        ContactsWhitelistFragment fragment = new ContactsWhitelistFragment();
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

            ArrayList<WhitelistRecord> whitelist = new ArrayList<>();
            whitelist.addAll(mPresenter.getWhitelist(activity));

            mAdapter = new WhitelistAdapter(activity, whitelist);
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
        //TODO: add whitelist record logic
        Toast.makeText(getActivity(), "WHITE", Toast.LENGTH_SHORT).show();
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
        Comparator<WhitelistRecord> comparator = null;
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

    private Comparator<WhitelistRecord> buildAscNameComparator() {
        return (r1, r2) -> r1.getName().compareTo(r2.getName());
    }
    private Comparator<WhitelistRecord> buildDescNameComparator() {
        return (r1, r2) -> r2.getName().compareTo(r1.getName());
    }
    private Comparator<WhitelistRecord> buildAscIdComparator() {
        return (r1, r2) -> Long.compare(r1.id, r2.id);
    }
    private Comparator<WhitelistRecord> buildDescIdComparator() {
        return (r1, r2) -> Long.compare(r2.id, r1.id);
    }
    private Comparator<WhitelistRecord> buildTimesSeenComparator() {
        return (r1, r2) -> {
            int i1 = r1.getWhereSeen().countCheckedInstances();
            int i2 = r2.getWhereSeen().countCheckedInstances();
            return Integer.compare(i1, i2);
        };
    }
}
