package com.funprojects.wotlksaves.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.arellomobile.mvp.MvpAppCompatDialogFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.funprojects.wotlksaves.R;
import com.funprojects.wotlksaves.mvp.models.Instances;
import com.funprojects.wotlksaves.mvp.models.ListRecord;
import com.funprojects.wotlksaves.mvp.presenters.InstancesPresenter;
import com.funprojects.wotlksaves.mvp.views.InstancesView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Andrei on 02.08.2018.
 */

public class InstancesDialog extends MvpAppCompatDialogFragment implements InstancesView {

    private static final String ARG_RECORD_ID = "rec_id";


    @InjectPresenter
    InstancesPresenter mPresenter;

    private ArrayList<CheckBox> mInstancesViews;
    private ListRecord mRecord;


    public static InstancesDialog newInstance(long recordId) {
        Bundle args = new Bundle();
        args.putLong(ARG_RECORD_ID, recordId);
        InstancesDialog fragment = new InstancesDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long recordId = getArguments().getLong(ARG_RECORD_ID);
        mRecord = mPresenter.getRecord(recordId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_instances, container, false);
        ButterKnife.bind(this, v);
        mInstancesViews = makeInstancesViews(v);
        makeStartValuesForViews(mRecord);
        return v;
    }

    private ArrayList<CheckBox> makeInstancesViews(View baseView) {
        ArrayList<CheckBox> instancesViews = new ArrayList<>(Instances.WOTLK_RAIDS_COUNT);
        instancesViews.add(Instances.naxx10, baseView.findViewById(R.id.check_naxx10));
        instancesViews.add(Instances.naxx25, baseView.findViewById(R.id.check_naxx25));
        instancesViews.add(Instances.os10, baseView.findViewById(R.id.check_os10));
        instancesViews.add(Instances.os25, baseView.findViewById(R.id.check_os25));
        instancesViews.add(Instances.ulduar10, baseView.findViewById(R.id.check_ulduar10));
        instancesViews.add(Instances.ulduar25, baseView.findViewById(R.id.check_ulduar25));
        instancesViews.add(Instances.onyxia10, baseView.findViewById(R.id.check_onyxia10));
        instancesViews.add(Instances.onyxia25, baseView.findViewById(R.id.check_onyxia25));
        instancesViews.add(Instances.va10, baseView.findViewById(R.id.check_va10));
        instancesViews.add(Instances.va25, baseView.findViewById(R.id.check_va25));
        instancesViews.add(Instances.toc10, baseView.findViewById(R.id.check_toc10));
        instancesViews.add(Instances.togc10, baseView.findViewById(R.id.check_togc10));
        instancesViews.add(Instances.toc25, baseView.findViewById(R.id.check_toc25));
        instancesViews.add(Instances.togc25, baseView.findViewById(R.id.check_togc25));
        instancesViews.add(Instances.icc10, baseView.findViewById(R.id.check_icc10));
        instancesViews.add(Instances.icc10h, baseView.findViewById(R.id.check_icc10h));
        instancesViews.add(Instances.icc25, baseView.findViewById(R.id.check_icc25));
        instancesViews.add(Instances.icc25h, baseView.findViewById(R.id.check_icc25h));
        instancesViews.add(Instances.rs10, baseView.findViewById(R.id.check_rs10));
        instancesViews.add(Instances.rs10h, baseView.findViewById(R.id.check_rs10h));
        instancesViews.add(Instances.rs25, baseView.findViewById(R.id.check_rs25));
        instancesViews.add(Instances.rs25h, baseView.findViewById(R.id.check_rs25h));
        return instancesViews;
    }

    private void makeStartValuesForViews(ListRecord record) {
        if (mInstancesViews != null && mInstancesViews.size() > 0) {
            for (int placeCode = 0; placeCode < mInstancesViews.size(); placeCode++) {
                CheckBox view = mInstancesViews.get(placeCode);
                if (record.getPlaces() == null) { //TODO: move to migrations code
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();

                    record.setClearedPlaces();

                    realm.commitTransaction();
                }
                view.setChecked(record.getPlaces().saves.get(placeCode));
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(R.string.string_places);
        return dialog;
    }

    @Override
    public void onStop() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        Instances newPlaces = new Instances();
        for (int instanceCode = 0; instanceCode < Instances.WOTLK_RAIDS_COUNT; instanceCode++) {
            CheckBox instanceView = mInstancesViews.get(instanceCode);
            newPlaces.saves.set(instanceCode, instanceView.isChecked());
            mPresenter.saveNewPlaces(newPlaces);
        }

        realm.commitTransaction();
        super.onStop();
    }
}
