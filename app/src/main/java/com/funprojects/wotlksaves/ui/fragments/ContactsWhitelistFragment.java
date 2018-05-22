package com.funprojects.wotlksaves.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.funprojects.wotlksaves.R;
import com.funprojects.wotlksaves.mvp.models.BlacklistRecord;
import com.funprojects.wotlksaves.mvp.models.WhitelistRecord;
import com.funprojects.wotlksaves.ui.activities.MainActivity;
import com.funprojects.wotlksaves.ui.adapters.recyclers.BlacklistAdapter;
import com.funprojects.wotlksaves.ui.adapters.recyclers.VerticalSpaceItemDecoration;
import com.funprojects.wotlksaves.ui.adapters.recyclers.WhitelistAdapter;

import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactsWhitelistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsWhitelistFragment extends TabFragment {

    @BindDimen(R.dimen.recycler_item_vertical_space)
    int mVerticalSpacing;

    @BindView(R.id.whitelist_recycler)
    RecyclerView mRecycler;
    private WhitelistAdapter mAdapter;


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

    private void initRecycler() {
        if (mAdapter == null) {
            MainActivity activity = (MainActivity) getActivity();
            List<WhitelistRecord> whitelist = activity.getGameRealm().getWhitelist();
            mAdapter = new WhitelistAdapter(activity, whitelist);
            mRecycler.setLayoutManager(new LinearLayoutManager(activity));
            mRecycler.addItemDecoration(new VerticalSpaceItemDecoration(mVerticalSpacing));
            mRecycler.setAdapter(mAdapter);
        }
    }

    @Override
    public void addRecord() {
        //TODO: add whitelist record logic
        Toast.makeText(getActivity(), "BLACK", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateList() {
        mAdapter.notifyDataSetChanged();
    }
}
