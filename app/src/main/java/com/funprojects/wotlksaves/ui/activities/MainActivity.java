package com.funprojects.wotlksaves.ui.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.funprojects.wotlksaves.R;
import com.funprojects.wotlksaves.mvp.models.GameCharacter;
import com.funprojects.wotlksaves.mvp.models.GameRealm;
import com.funprojects.wotlksaves.mvp.models.ListRecord;
import com.funprojects.wotlksaves.ui.dialogs.AddCharacterDialog;
import com.funprojects.wotlksaves.ui.fragments.CharactersFragment;
import com.funprojects.wotlksaves.ui.fragments.ContactsFragment;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

import static com.funprojects.wotlksaves.tools.Constraints.ARG_REALM;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public GameRealm getGameRealm() {
        return mGameRealm;
    }

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    private GameRealm mGameRealm;
    private CharactersFragment mCharactersFragment;
    private ContactsFragment mContactsFragment;

    private ActionMode mActionMode;


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            animateActivityOut();
        }
    }

    private void animateActivityOut() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            overridePendingTransition(R.anim.enter_left_in, R.anim.exit_right_out);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        long id = getIntent().getLongExtra(ARG_REALM, -1);
        mGameRealm = GameRealm.byId(id);
        ButterKnife.bind(this);


        mNavigationView = findViewById(R.id.nav_view);
        setupNavigationViewLabels(mNavigationView);

        mNavigationView.setNavigationItemSelectedListener(this);
        MenuItem startItem = mNavigationView.getMenu().getItem(0);
        onNavigationItemSelected(startItem);
        startItem.setChecked(true);
    }

    private void setupNavigationViewLabels(NavigationView navigationView) {
        TextView tvRealm = navigationView.getHeaderView(0).findViewById(R.id.nav_realm_label);
        tvRealm.setText(mGameRealm.getName());
        TextView tvServer = navigationView.getHeaderView(0).findViewById(R.id.nav_server_label);
        tvServer.setText(mGameRealm.getServerName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_characters) {
            mCharactersFragment = CharactersFragment.newInstance(mGameRealm);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, mCharactersFragment)
                    .commit();
        } else if (id == R.id.nav_find) {
            Toast.makeText(this, "NOT YET IMPLEMENTED", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_contacts) {
            mContactsFragment = new ContactsFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, mContactsFragment)
                    .commit();
        } else if (id == R.id.nav_share_blacklist) {
            Toast.makeText(this, "NOT YET IMPLEMENTED", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share_whitelist) {
            Toast.makeText(this, "NOT YET IMPLEMENTED", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_choose_realm) {
            Intent intent = new Intent(this, RealmChooserActivity.class);
            startActivity(intent);
            animateActivityOut();
            this.finish();
            return true;
        } else if (id == R.id.nav_exit){
            this.finish();
            return true;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openAddCharacterDialog() {
        AddCharacterDialog dialog = new AddCharacterDialog();
        dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_Dialog_Light);
        getSupportFragmentManager().beginTransaction()
                .add(dialog, null)
                .commit();
    }

    public void addToBlacklist(@Nullable ListRecord record) {
        if (record != null) {
            Realm realm = Realm.getDefaultInstance();
            if (!realm.isInTransaction())
                realm.beginTransaction();

            //TODO: check this logic
            ListRecord sameRecord = mGameRealm.getBlacklist().where()
                    .equalTo("mName", record.getName())
                    .findFirst();
            if (sameRecord == null) {
                mGameRealm.getBlacklist().add(record);
            }

            realm.commitTransaction();
            mContactsFragment.updateList(mGameRealm.getBlacklist());
        }
    }

    public void addToWhitelist(@Nullable ListRecord record) {
        if (record != null) {
            Realm realm = Realm.getDefaultInstance();
            boolean inOuterTransition = realm.isInTransaction();
            if (!inOuterTransition)
                realm.beginTransaction();

            ListRecord sameRecord = mGameRealm.getWhitelist().where()
                    .equalTo("mName", record.getName())
                    .findFirst();
            if (sameRecord == null) {
                mGameRealm.getWhitelist().add(record);
            }

            if (!inOuterTransition)
                realm.commitTransaction();
            mContactsFragment.updateList(mGameRealm.getWhitelist());
        }
    }

    public void initContactsActionMode(ActionMode.Callback callback) {
        mActionMode = startSupportActionMode(callback);
    }

    public void finishActionMode() {
        if (mActionMode != null) {
            mActionMode.finish();
        }
    }

    public void addCharacterToList(GameCharacter character) {
        mCharactersFragment.updateCharactersList(character);
    }
}
