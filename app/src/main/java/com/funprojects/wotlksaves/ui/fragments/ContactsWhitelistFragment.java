package com.funprojects.wotlksaves.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.funprojects.wotlksaves.R;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactsWhitelistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsWhitelistFragment extends TabFragment {




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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contacts_whitelist, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void addRecord() {
        //TODO: add whitelist record logic
        Toast.makeText(getActivity(), "WHITE", Toast.LENGTH_SHORT).show();
    }
}
