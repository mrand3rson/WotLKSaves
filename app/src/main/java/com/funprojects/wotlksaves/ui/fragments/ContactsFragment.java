package com.funprojects.wotlksaves.ui.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.funprojects.wotlksaves.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ContactsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final int TABS_COUNT = 2;
    private String mParam1;

    @BindView(R.id.pager)
    ViewPager mViewPager;

    @BindView(R.id.contacts_tabs)
    TabLayout tabLayout;

    @BindView(R.id.toolbar2)
    Toolbar toolbar;

    SectionsPagerAdapter mSectionsPagerAdapter;


    public ContactsFragment() {

    }

    public static ContactsFragment newInstance(String param1, String param2) {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contacts, container, false);
        ButterKnife.bind(this, v);

//        ((MainActivity)getActivity()).setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.ViewPagerOnTabSelectedListener(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(onTabSelectedListener);
        return v;
    }

    public void addToList() {
        int position = tabLayout.getSelectedTabPosition();
        TabFragment tabFragment =
                (TabFragment) mSectionsPagerAdapter.getItem(position);
        tabFragment.addRecord();
    }


    class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private TabFragment[] fragments = new TabFragment[getCount()];
        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (fragments[position] == null) {
                switch (position) {
                    case 0: {
                        fragments[position] = new ContactsBlacklistFragment();
                        break;
                    }
                    case 1: {
                        fragments[position] = new ContactsWhitelistFragment();
                        break;
                    }
                }
            }
            return fragments[position];
        }

        @Override
        public int getCount() {
            return TABS_COUNT;
        }
    }
}
