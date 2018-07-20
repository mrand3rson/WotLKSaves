package com.funprojects.wotlksaves.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

import com.arellomobile.mvp.MvpAppCompatDialogFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.funprojects.wotlksaves.R;
import com.funprojects.wotlksaves.mvp.models.Account;
import com.funprojects.wotlksaves.mvp.models.GameCharacter;
import com.funprojects.wotlksaves.mvp.models.GameRealm;
import com.funprojects.wotlksaves.mvp.models.logic.GameInfoFeatures;
import com.funprojects.wotlksaves.mvp.presenters.AddCharacterPresenter;
import com.funprojects.wotlksaves.mvp.views.AddCharacterView;
import com.funprojects.wotlksaves.tools.CharacterConstraints;
import com.funprojects.wotlksaves.tools.CharacterInfoProcessor;
import com.funprojects.wotlksaves.ui.activities.MainActivity;
import com.funprojects.wotlksaves.ui.adapters.spinners.AccountAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Andrei on 13.05.2018.
 */

public class AddCharacterDialog extends MvpAppCompatDialogFragment
        implements AddCharacterView {

    private final static String LOG_TAG = AddCharacterDialog.class.getSimpleName();
    private static final int SECOND_SPEC_MIN_LEVEL = 40;

    @BindArray(R.array.wow_races)
    String[] racesArray;

    @BindArray(R.array.wow_classes)
    String[] classesArray;

    @BindView(R.id.toggle_account_choose)
    ToggleButton mToggle;

    @BindView(R.id.accounts)
    Spinner mAccountsSpinner;

    @BindView(R.id.edit_account)
    EditText mAccountEdit;

    @BindView(R.id.edit_nickname)
    EditText mNicknameView;

    @BindView(R.id.char_race)
    Spinner mRacesView;

    @BindView(R.id.char_class)
    Spinner mClassesView;

    @BindView(R.id.char_level)
    SeekBar mLevelView;

    @BindView(R.id.level_label)
    TextView mLevelLabelView;

    @BindView(R.id.spec1)
    Spinner mSpec1View;

    @BindView(R.id.spec2)
    Spinner mSpec2View;

    @InjectPresenter
    AddCharacterPresenter mPresenter;

    private ArrayList<Account> mAccountList;
    private ArrayList<String> mRaces = new ArrayList<>();
    private ArrayList<String> mFilteredClasses = new ArrayList<>();
    private ArrayAdapter<Account> mAccountAdapter;
    private ArrayAdapter<String> mRacesAdapter;
    private ArrayAdapter<String> mClassesAdapter;
    private ArrayAdapter<String> mSpec1Adapter;
    private ArrayAdapter<String> mSpec2Adapter;

    private byte mLevel = 1;


    public AddCharacterDialog() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_add_character, container, false);
        ButterKnife.bind(this, v);

        mAccountList = mPresenter.getAccountList(((MainActivity)getActivity()).getGameRealm());

        toggleFieldsVisibility(mAccountList.isEmpty());

        if (mAccountList.isEmpty()) {
            mToggle.setEnabled(false);
            mToggle.setChecked(true);
        } else {
            mAccountAdapter = new AccountAdapter(getActivity(),
                    android.R.layout.simple_list_item_1,
                    mAccountList);
            mAccountsSpinner.setAdapter(mAccountAdapter);
        }
        mToggle.setOnCheckedChangeListener((compoundButton, b) -> toggleFieldsVisibility(b));

        fillRaceSpinner();

        SeekBar.OnSeekBarChangeListener levelChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mLevel = (byte) (progress + 1);
                mLevelLabelView.setText(getString(R.string.label_add_level, mLevel));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                boolean isLevelEnough = (seekBar.getProgress() >= SECOND_SPEC_MIN_LEVEL);
                if (isLevelEnough) {
                    mSpec2View.setVisibility(View.VISIBLE);
                } else {
                    mSpec2View.setSelection(0);
                    mSpec2View.setVisibility(View.INVISIBLE);
                }
            }
        };

        mLevelView.setOnSeekBarChangeListener(levelChangeListener);
        levelChangeListener.onStopTrackingTouch(mLevelView);
        levelChangeListener.onProgressChanged(mLevelView, 0, true);

        return v;
    }

    private void toggleFieldsVisibility(boolean addAccount) {
        if (addAccount) {
            mAccountsSpinner.setVisibility(View.GONE);
            mAccountEdit.setVisibility(View.VISIBLE);
        } else {
            mAccountsSpinner.setVisibility(View.VISIBLE);
            mAccountEdit.setVisibility(View.GONE);
        }
    }

    private void fillRaceSpinner() {
        mRaces.addAll(Arrays.asList(racesArray));
        mRacesAdapter =
                new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_spinner_item,
                        mRaces);
        mRacesView.setAdapter(mRacesAdapter);
        mClassesAdapter =
                new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_spinner_item,
                        mFilteredClasses);
        mClassesView.setAdapter(mClassesAdapter);
        mRacesView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (mClassesView.getOnItemSelectedListener() == null) {
                    mClassesView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            int classCode = CharacterConstraints.parseClassToCode(getActivity(), mClassesAdapter.getItem(i));
                            fillSpecSpinners(classCode);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
                fillClassSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void fillClassSpinner() {
        String race = (String) mRacesView.getSelectedItem();
        int raceCode = CharacterConstraints.parseRaceToCode(getActivity(), race);
        List<Integer> classCodes = GameInfoFeatures.getRaceClassTable(raceCode);

        mFilteredClasses.clear();
        if (classCodes != null) {
            for (int classCode: classCodes) {
                String classString = CharacterInfoProcessor.getClassFromCode(getActivity(), classCode);
                mFilteredClasses.add(classString);
            }
            mClassesView.setSelection(0);
        } else {
            Log.e(LOG_TAG, "fillClassSpinner() - classCodes is null");
        }
        mClassesAdapter.notifyDataSetChanged();
    }

    private void fillSpecSpinners(int classCode) {
        List<String> specs = GameInfoFeatures.getAllSpecs(getActivity(), classCode);
        mSpec1Adapter =
                new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_spinner_item,
                        specs);
        mSpec2Adapter =
                new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_spinner_item,
                        specs);
        mSpec1View.setAdapter(mSpec1Adapter);
        mSpec2View.setAdapter(mSpec2Adapter);
    }

    private boolean isAllFieldsFilled() {
        boolean accountFilled = (mToggle.isChecked()?
                mAccountEdit.getText().length() > 1: mAccountsSpinner.getSelectedItem() != null);
        return mNicknameView.getText().length() > 1
                && accountFilled
                && mRacesView.getSelectedItem() != null
                && mClassesView.getSelectedItem() != null
                && mLevelView.getProgress() >= 0
                && mSpec1View.getSelectedItem() != null;
    }

    private boolean isCharacterExists(GameRealm realm) {
        return mPresenter.findCharacter(mNicknameView.getText().toString(),
                realm);
    }

    @OnClick(R.id.button_create)
    public void createCharacter() {
        GameRealm gameRealm = ((MainActivity)getActivity()).getGameRealm();
        if (isAllFieldsFilled() && !isCharacterExists(gameRealm)) {
            Account account;
            if (mToggle.isChecked()) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();

                account = realm.createObject(Account.class, Account.setIdIncremented());
                account.setName(mAccountEdit.getText().toString());
                account.setGameRealmId(((MainActivity)getActivity()).getGameRealm().id);
//                account = new Account(mAccountEdit.getText().toString(),
//                        ((MainActivity)getActivity()).getGameRealm().id);
                realm.commitTransaction();
//                mPresenter.saveAccount(account, gameRealm);
            } else {
                account = (Account) mAccountsSpinner.getSelectedItem();
            }

            byte classCode = CharacterConstraints.parseClassToCode(getActivity(), (String) mClassesView.getSelectedItem());
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            GameCharacter character = realm.createObject(GameCharacter.class, GameCharacter.setIdIncremented());
            character.setNickname(mNicknameView.getText().toString());
            character.setRace(CharacterConstraints.parseRaceToCode(getActivity(), (String) mRacesView.getSelectedItem()));
            character.setClass(classCode);
            character.setLevel(mLevel);
            character.setSpec1(CharacterConstraints.parseSpecToCode(getActivity(), classCode, (String) mSpec1View.getSelectedItem()));
            character.setSpec2(CharacterConstraints.parseSpecToCode(getActivity(), classCode, (String) mSpec2View.getSelectedItem()));
            character.setAccountId(account.id);

            realm.commitTransaction();
            //replaced to make full managed list
//                    new GameCharacter(mNicknameView.getText().toString(),
//                    CharacterConstraints.parseRaceToCode(getActivity(), (String) mRacesView.getSelectedItem()),
//                    classCode,
//                    mLevel,
//                    CharacterConstraints.parseSpecToCode(getActivity(), classCode, (String) mSpec1View.getSelectedItem()),
//                    CharacterConstraints.parseSpecToCode(getActivity(), classCode, (String) mSpec2View.getSelectedItem()),
//                    accountName
//            );
            mPresenter.saveCharacter(character);
        } else {
            Toast.makeText(getActivity(), "There are some errors in input fields", Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("ADDasdkfjkg");
        return dialog;
    }

    @Override
    public void onSuccess(GameCharacter character) {
        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
        ((MainActivity)getActivity()).addCharacterToList(character);
        dismiss();
    }
}
