package com.funprojects.wotlksaves.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.funprojects.wotlksaves.R;
import com.funprojects.wotlksaves.mvp.models.GameCharacter;
import com.funprojects.wotlksaves.mvp.models.GameRealm;
import com.funprojects.wotlksaves.mvp.presenters.CharactersPresenter;
import com.funprojects.wotlksaves.mvp.views.CharactersView;
import com.funprojects.wotlksaves.ui.adapters.recyclers.CharacterAdapter;
import com.funprojects.wotlksaves.ui.adapters.recyclers.VerticalSpaceItemDecoration;

import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.funprojects.wotlksaves.tools.Constraints.ARG_REALM;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CharactersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CharactersFragment extends MvpAppCompatFragment implements CharactersView {

    @BindDimen(R.dimen.recycler_item_vertical_space)
    int mVerticalSpacing;

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
        View v = inflater.inflate(R.layout.fragment_characters, container, false);
        ButterKnife.bind(this, v);
        initRecycler();
        return v;
    }

    private void initRecycler() {
        if (mRecycler.getLayoutManager() == null) {
            mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            mRecycler.addItemDecoration(new VerticalSpaceItemDecoration(mVerticalSpacing));
        }

        final List<GameCharacter> data = mPresenter.getCharacters(mRealm);
        mAdapter = new CharacterAdapter(getActivity(), R.layout.recycler_characters_item, data);
        mRecycler.setAdapter(mAdapter);

        if (data.isEmpty()) {
            mWarning.setVisibility(View.VISIBLE);
        }
    }
}
