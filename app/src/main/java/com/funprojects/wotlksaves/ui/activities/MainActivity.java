package com.funprojects.wotlksaves.ui.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.funprojects.wotlksaves.R;
import com.funprojects.wotlksaves.mvp.models.BlacklistRecord;
import com.funprojects.wotlksaves.mvp.models.GameRealm;
import com.funprojects.wotlksaves.ui.dialogs.AddCharacterDialog;
import com.funprojects.wotlksaves.ui.fragments.CharactersFragment;
import com.funprojects.wotlksaves.ui.fragments.ContactsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.funprojects.wotlksaves.tools.Constraints.ARG_REALM;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public GameRealm getGameRealm() {
        return mGameRealm;
    }

    GameRealm mGameRealm;
    ContactsFragment contactsFragment;

    @BindView(R.id.fab_add)
    FloatingActionButton fab;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;


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

        mGameRealm = getIntent().getParcelableExtra(ARG_REALM);

        ButterKnife.bind(this);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

        fab.setOnClickListener(null);
        fab.setVisibility(View.INVISIBLE);

        if (id == R.id.nav_characters) {
            CharactersFragment fragment = CharactersFragment.newInstance(mGameRealm);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        } else if (id == R.id.nav_find) {
            Toast.makeText(this, "NOT YET IMPLEMENTED", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_contacts) {
            contactsFragment = new ContactsFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, contactsFragment)
                    .commit();

            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contactsFragment.addToList();
                }
            });
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

    public void updateBlacklist(BlacklistRecord record) {
        mGameRealm.getBlacklist().add(record);
        contactsFragment.updateList();
    }
}
