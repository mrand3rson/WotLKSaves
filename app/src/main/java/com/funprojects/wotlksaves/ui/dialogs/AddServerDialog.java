package com.funprojects.wotlksaves.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.arellomobile.mvp.MvpDialogFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.funprojects.wotlksaves.R;
import com.funprojects.wotlksaves.mvp.models.GameRealm;
import com.funprojects.wotlksaves.mvp.models.Server;
import com.funprojects.wotlksaves.mvp.presenters.AddServerPresenter;
import com.funprojects.wotlksaves.mvp.views.AddServerView;
import com.funprojects.wotlksaves.ui.activities.RealmChooserActivity;
import com.funprojects.wotlksaves.ui.adapters.spinners.ServerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Andrei on 17.04.2018.
 */

public class AddServerDialog extends MvpDialogFragment implements AddServerView {

    private final static String TITLE = "Add a server...";

    @BindView(R.id.toggle_server_choose)
    ToggleButton mToggle;

    @BindView(R.id.servers)
    Spinner mServersSpinner;
    ArrayAdapter<Server> mAdapter;
    ArrayList<Server> mServerList;

    @BindView(R.id.edit_server)
    EditText mServerEdit;

    @BindView(R.id.edit_realm)
    EditText mRealmEdit;

    @InjectPresenter
    AddServerPresenter mPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_add_server, container, false);
        ButterKnife.bind(this, v);

        mServerList = mPresenter.getServerList();

        toggleFieldsVisibility(mServerList.isEmpty());
        if (mServerList.isEmpty()) {
            mToggle.setEnabled(false);
            mToggle.setChecked(true);
        } else {
            mAdapter = new ServerAdapter(getActivity(),
                    android.R.layout.simple_list_item_1,
                    mServerList);
            mServersSpinner.setAdapter(mAdapter);
        }

        mToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                toggleFieldsVisibility(compoundButton.isChecked());
            }
        });
        return v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(TITLE);
        return dialog;
    }

    private void toggleFieldsVisibility(boolean addServer) {
        if (addServer) {
            mServersSpinner.setVisibility(View.GONE);
            mServerEdit.setVisibility(View.VISIBLE);
        } else {
            mServersSpinner.setVisibility(View.VISIBLE);
            mServerEdit.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.button_ok)
    public void confirmAdd() {
        Server server = (mToggle.isChecked()? new Server(mServerEdit.getText().toString()):
                (Server) mServersSpinner.getSelectedItem());
        GameRealm realm = new GameRealm(mRealmEdit.getText().toString(), server.getName());
        ((RealmChooserActivity)getActivity()).addGameRealm(server, realm);

        this.dismiss();
    }
}