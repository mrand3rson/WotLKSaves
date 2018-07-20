package com.funprojects.wotlksaves.ui.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.funprojects.wotlksaves.R;
import com.funprojects.wotlksaves.mvp.models.Account;
import com.funprojects.wotlksaves.mvp.models.GameCharacter;
import com.funprojects.wotlksaves.mvp.models.GameRealm;
import com.funprojects.wotlksaves.mvp.presenters.CharactersPresenter;
import com.funprojects.wotlksaves.mvp.views.CharactersView;
import com.funprojects.wotlksaves.ui.activities.MainActivity;
import com.funprojects.wotlksaves.ui.adapters.recyclers.CharacterAdapter;
import com.funprojects.wotlksaves.ui.adapters.recyclers.VerticalSpaceItemDecoration;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmList;

import static com.funprojects.wotlksaves.tools.Constraints.ARG_REALM;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CharactersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CharactersFragment extends MvpAppCompatFragment implements CharactersView {

    @BindDimen(R.dimen.recycler_item_vertical_space)
    int mVerticalSpacing;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    @BindView(R.id.warning_empty)
    TextView mWarning;

    @InjectPresenter
    CharactersPresenter mPresenter;

    private CharacterAdapter mAdapter;
    private GameRealm mRealm;


    public CharactersFragment() {

    }

    public static CharactersFragment newInstance(GameRealm outerRealm) {
        CharactersFragment fragment = new CharactersFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_REALM, outerRealm);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRealm = getArguments().getParcelable(ARG_REALM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.app_bar_characters, container, false);

        ButterKnife.bind(this, v);


        MainActivity activity = (MainActivity) getActivity();
        toolbar = v.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);

        DrawerLayout drawer = activity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        initRecycler();
        return v;
    }

    private void initRecycler() {
        if (mRecycler.getLayoutManager() == null) {
            mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecycler.addItemDecoration(new VerticalSpaceItemDecoration(mVerticalSpacing));
        }

        final RealmList<Account> data = mPresenter.getAccounts(mRealm);
        mAdapter = new CharacterAdapter(getActivity(), R.layout.recycler_characters_item, data);
        mRecycler.setAdapter(mAdapter);

        if (data.isEmpty()) {
            mWarning.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.fab_add_character)
    public void fillAddCharacterForm() {
        ((MainActivity)getActivity()).openAddCharacterDialog();
    }

    public void updateCharactersList(GameCharacter character) {
        mAdapter.addCharacter(character);
        mWarning.setVisibility(View.INVISIBLE);
    }
}
