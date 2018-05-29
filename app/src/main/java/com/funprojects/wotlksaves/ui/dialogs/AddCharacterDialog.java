package com.funprojects.wotlksaves.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.funprojects.wotlksaves.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Andrei on 13.05.2018.
 */

public class AddCharacterDialog extends DialogFragment {

    @BindArray(R.array.wow_races)
    String[] racesArray;

    @BindArray(R.array.wow_classes)
    String[] classesArray;

    @BindView(R.id.black_nickname)
    EditText mNicknameView;

    @BindView(R.id.char_race)
    Spinner mRacesView;

    @BindView(R.id.char_class)
    Spinner mClassView;

    @BindView(R.id.level)
    SeekBar mLevelView;

    @BindView(R.id.spec1)
    Spinner mSpec1View;

    @BindView(R.id.spec2)
    Spinner mSpec2View;

    List<String> mRaces;
    ArrayList<String> mFilteredClasses;
    ArrayAdapter<String> mRacesAdapter;
    ArrayAdapter<String> mClassesAdapter;
    ArrayAdapter<String> mSpec1Adapter;
    ArrayAdapter<String> mSpec2Adapter;


    public AddCharacterDialog() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_add_character, container, false);
        ButterKnife.bind(this, v);

        fillRaceSpinner();

        return v;
    }

    private void fillRaceSpinner() {
        mRaces = Arrays.asList(racesArray);
        mClassesAdapter =
                new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_spinner_item,
                        mRaces);
        mRacesView.setAdapter(mClassesAdapter);
        mRacesView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO: fillClassSpinner
                fillClassSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void fillClassSpinner() {

    }

    private void fillSpecSpinners() {
        String[] specs = {""};
        mSpec1Adapter =
                new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_spinner_item,
                        specs);
        mSpec2Adapter =
                new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_spinner_item,
                        specs);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("ADDasdkfjkg");
        return dialog;
    }
}
