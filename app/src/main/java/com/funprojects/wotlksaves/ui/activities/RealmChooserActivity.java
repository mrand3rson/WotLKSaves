package com.funprojects.wotlksaves.ui.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.arellomobile.mvp.MvpActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.funprojects.wotlksaves.R;
import com.funprojects.wotlksaves.mvp.models.GameRealm;
import com.funprojects.wotlksaves.mvp.models.Server;
import com.funprojects.wotlksaves.mvp.presenters.RealmChooserPresenter;
import com.funprojects.wotlksaves.mvp.views.ServerChooserView;
import com.funprojects.wotlksaves.ui.adapters.spinners.RealmAdapter;
import com.funprojects.wotlksaves.ui.dialogs.AddServerDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.funprojects.wotlksaves.tools.Constraints.ARG_REALM;


public class RealmChooserActivity extends MvpActivity implements ServerChooserView {

    @BindView(R.id.go)
    Button mButtonGo;

    @BindView(R.id.game_realms)
    Spinner mGameRealmsSpinner;
    ArrayAdapter<GameRealm> mAdapter;
    ArrayList<GameRealm> mRealmList;

    @InjectPresenter
    RealmChooserPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm_chooser);

        ButterKnife.bind(this);

        mRealmList = mPresenter.getGameRealms();
        mAdapter = new RealmAdapter(this,
                android.R.layout.simple_list_item_1,
                mRealmList);
        mGameRealmsSpinner.setAdapter(mAdapter);
        if (mRealmList.isEmpty()) {
            mButtonGo.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.go)
    public void confirmServer() {
        GameRealm selectedRealm = mAdapter.getItem(mGameRealmsSpinner.getSelectedItemPosition());
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(ARG_REALM, selectedRealm.id);
        startActivity(intent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            overridePendingTransition(R.anim.enter_right_in, R.anim.exit_left_out);
        }
        this.finish();
    }

    @OnClick(R.id.fab_add_realm)
    public void viewAddRealmDialog() {
        AddServerDialog dialog = new AddServerDialog();
        dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_Dialog_Light);
        getFragmentManager().beginTransaction()
                .add(dialog, String.valueOf(dialog.getId()))
                .commit();
    }

    public void addGameRealm(Server server, GameRealm realm) {
        mPresenter.addGameRealm(server, realm);
        updateRealmList(realm);
    }

    private void updateRealmList(GameRealm realm) {
        mRealmList.add(realm);
        mAdapter.notifyDataSetChanged();
        mButtonGo.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAddExistingRealm() {
        Toast.makeText(this, R.string.warning_existing_realm, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddSuccessful() {
        Toast.makeText(this, R.string.warning_added_realm, Toast.LENGTH_SHORT).show();
    }
}
