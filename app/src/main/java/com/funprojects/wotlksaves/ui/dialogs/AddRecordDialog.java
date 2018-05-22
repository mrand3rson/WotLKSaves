package com.funprojects.wotlksaves.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatDialogFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.funprojects.wotlksaves.R;
import com.funprojects.wotlksaves.mvp.models.BlacklistRecord;
import com.funprojects.wotlksaves.mvp.models.WhitelistRecord;
import com.funprojects.wotlksaves.mvp.presenters.AddRecordPresenter;
import com.funprojects.wotlksaves.mvp.views.AddRecordView;
import com.funprojects.wotlksaves.ui.activities.MainActivity;
import com.funprojects.wotlksaves.ui.fragments.ContactsBlacklistFragment;
import com.funprojects.wotlksaves.ui.fragments.ContactsWhitelistFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.funprojects.wotlksaves.ui.dialogs.ListTypes.BLACK;
import static com.funprojects.wotlksaves.ui.dialogs.ListTypes.WHITE;

/**
 * Created by Andrei on 21.05.2018.
 */

public class AddRecordDialog extends MvpAppCompatDialogFragment implements AddRecordView {

    private final static String TITLE = "Add a blacklist record...";

    @BindView(R.id.edit_nickname)
    EditText mNicknameView;

    @BindView(R.id.edit_reason)
    EditText mReasonView;

    @InjectPresenter
    AddRecordPresenter mPresenter;

    int type;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_add_to_blacklist, container, false);
        ButterKnife.bind(this, v);
        String whiteClassName = ContactsWhitelistFragment.class.getSimpleName();
        String blackClassName = ContactsBlacklistFragment.class.getSimpleName();

        if (getArguments().get(whiteClassName) != null) {
            type = WHITE;
        } else if (getArguments().get(blackClassName) != null) {
            type = BLACK;
        }
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
        if (nickname.equals("") || reason.equals("")) {
            return;
        }

        switch (type) {
            case BLACK: {
                mPresenter.addBlacklistRecord(getActivity(), nickname, reason);
                break;
            }
            case WHITE: {
                mPresenter.addWhitelistRecord(getActivity(), nickname, reason);
                break;
            }
        }

        this.dismiss();
    }

    @Override
    public void addToBlacklist(BlacklistRecord record) {
        ((MainActivity)getActivity()).addToBlacklist(record);
    }

    public void addToWhitelist(WhitelistRecord record) {
        ((MainActivity)getActivity()).addToWhitelist(record);
    }

    @Override
    public void warnExists(String name) {
        Toast.makeText(getActivity(), name+" already exists", Toast.LENGTH_SHORT).show();
    }
}
