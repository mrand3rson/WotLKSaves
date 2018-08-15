package com.funprojects.wotlksaves.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.funprojects.wotlksaves.R;
import com.funprojects.wotlksaves.mvp.models.ListRecord;
import com.funprojects.wotlksaves.tools.SortTypes;
import com.funprojects.wotlksaves.ui.activities.MainActivity;
import com.funprojects.wotlksaves.ui.dialogs.SortContactsDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmList;


public class ContactsFragment extends Fragment {
    private static final int TABS_COUNT = 2;
    private TabFragment[] fragments = new TabFragment[TABS_COUNT];

    @BindView(R.id.pager)
    ViewPager mViewPager;

    @BindView(R.id.contacts_tabs)
    TabLayout tabLayout;

    @BindView(R.id.toolbar2)
    Toolbar toolbar;

    public SearchView getSearchView() {
        return mSearchView;
    }
    SearchView mSearchView;

    SectionsPagerAdapter mSectionsPagerAdapter;


    public ContactsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.app_bar_contacts, container, false);

        ButterKnife.bind(this, v);

        setHasOptionsMenu(true);
        MainActivity activity = (MainActivity) getActivity();
        toolbar = v.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);

        DrawerLayout drawer = activity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.ViewPagerOnTabSelectedListener(mViewPager);
        tabLayout.addOnTabSelectedListener(onTabSelectedListener);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout) {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                ((MainActivity)getActivity()).finishActionMode();


                TabFragment currentFragment = fragments[position];
                currentFragment.refreshAdapterList();
            }
        });
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.main_contacts, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        final MenuItem menuItem = menu.findItem(R.id.action_search);
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                getCurrentFragment().clearAdapterFilter();
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }
        });

        final SearchView searchView = menuItem.getActionView().findViewById(R.id.action_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                if (!searchView.isIconified()) {
//                    searchView.setIconified(true);
//                }
//                menuItem.collapseActionView();

                //A.S. visual pause
//                new Handler().postDelayed(() -> {
                    getCurrentFragment().filterAdapterList(query);
//                }, 500);

//                getCurrentFragment().clearAdapterFilter();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_sort: {
                //starting dialog, processing result in "onActivityResult"
                SortContactsDialog dialog = new SortContactsDialog();
                dialog.setTargetFragment(this, SortTypes.REQUEST_SORT_TYPE);
                getFragmentManager().beginTransaction()
                        .add(dialog, null)
                        .commit();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int sortType = data.getIntExtra(SortTypes.ARG_SORT_TYPE, -1);
        getCurrentFragment().sortAdapterList(sortType);
    }

    @OnClick(R.id.fab_add_contact)
    public void addToList() {
        int position = tabLayout.getSelectedTabPosition();
        TabFragment tabFragment =
                (TabFragment) mSectionsPagerAdapter.getItem(position);
        tabFragment.addRecord();
    }


    public void updateViewList(RealmList<ListRecord> list) {
        getCurrentFragment().updateAdapterList(list);
    }


    private TabFragment getCurrentFragment() {
        int position = tabLayout.getSelectedTabPosition();
        return (TabFragment) mSectionsPagerAdapter.getItem(position);
    }


    class SectionsPagerAdapter extends FragmentStatePagerAdapter {

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
