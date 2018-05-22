package com.funprojects.wotlksaves.ui.fragments;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.funprojects.wotlksaves.R;
import com.funprojects.wotlksaves.mvp.models.BlacklistRecord;
import com.funprojects.wotlksaves.ui.activities.MainActivity;
import com.funprojects.wotlksaves.ui.adapters.recyclers.BlacklistAdapter;
import com.funprojects.wotlksaves.ui.adapters.recyclers.VerticalSpaceItemDecoration;
import com.funprojects.wotlksaves.ui.dialogs.AddBlacklistDialog;

import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ContactsBlacklistFragment extends TabFragment {

    @BindDimen(R.dimen.recycler_item_vertical_space)
    int mVerticalSpacing;

    @BindView(R.id.blacklist_recycler)
    RecyclerView mRecycler;
    BlacklistAdapter mAdapter;


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
            List<BlacklistRecord> blacklist = activity.getGameRealm().getBlacklist();
            mAdapter = new BlacklistAdapter(activity, blacklist);
            mRecycler.setLayoutManager(new LinearLayoutManager(activity));
            mRecycler.addItemDecoration(new VerticalSpaceItemDecoration(mVerticalSpacing));
            mRecycler.setAdapter(mAdapter);
        }
    }

    @Override
    public void addRecord() {
        AddBlacklistDialog dialog = new AddBlacklistDialog();
        dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_Dialog_Light);
        getFragmentManager().beginTransaction()
                .add(dialog, String.valueOf(dialog.getId()))
                .commit();
        //TODO: add blacklist record logic
        Toast.makeText(getActivity(), "BLACK", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateList() {
        mAdapter.notifyDataSetChanged();
    }
}
