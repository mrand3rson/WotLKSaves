package com.funprojects.wotlksaves.ui.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatDialogFragment;
import com.arellomobile.mvp.MvpDialogFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.funprojects.wotlksaves.R;
import com.funprojects.wotlksaves.mvp.models.BlacklistRecord;
import com.funprojects.wotlksaves.mvp.models.GameRealm;
import com.funprojects.wotlksaves.mvp.models.Server;
import com.funprojects.wotlksaves.mvp.presenters.AddBlacklistPresenter;
import com.funprojects.wotlksaves.mvp.views.AddBlacklistView;
import com.funprojects.wotlksaves.ui.activities.MainActivity;
import com.funprojects.wotlksaves.ui.activities.RealmChooserActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Andrei on 21.05.2018.
 */

public class AddBlacklistDialog extends MvpAppCompatDialogFragment implements AddBlacklistView {

    private final static String TITLE = "Add a blacklist record...";

    @BindView(R.id.edit_nickname)
    EditText mNicknameView;

    @BindView(R.id.edit_reason)
    EditText mReasonView;

    @InjectPresenter
    AddBlacklistPresenter mPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_add_to_blacklist, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(TITLE);
        return dialog;
    }

    @OnClick(R.id.button_ok)
    public void confirmAdd() {
        String nickname = mNicknameView.getText().toString();
        String reason = mReasonView.getText().toString();
        mPresenter.addBlacklistRecord(getActivity(), nickname, reason);

        this.dismiss();
    }

    @Override
    public void updateBlacklist(BlacklistRecord record) {
        ((MainActivity)getActivity()).updateBlacklist(record);
    }
}
