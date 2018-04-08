package com.example.jayashankarjayan.placements;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class AdminHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MenuItem admin_home_display_students,admin_home_display_moderators,
            admin_home_display_companies,admin_home_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = new Bundle();
        bundle.putString("view", "student");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        admin_home_display_students = menu.findItem(R.id.admin_home_display_students);
        admin_home_display_moderators = menu.findItem(R.id.admin_home_display_moderators);
        admin_home_display_companies = menu.findItem(R.id.admin_home_display_companies);
        admin_home_logout = menu.findItem(R.id.admin_home_display_companies);

        admin_home_display_students.setChecked(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AdminDisplayLevelDetailsFragment adminDisplayLevelDetailsFragment;
        adminDisplayLevelDetailsFragment = new AdminDisplayLevelDetailsFragment();
        fragmentTransaction.replace(R.id.content_admin_home_fragment_container,adminDisplayLevelDetailsFragment);
        adminDisplayLevelDetailsFragment.setArguments(bundle);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AdminDisplayLevelDetailsFragment adminDisplayLevelDetailsFragment;
        Bundle bundles = new Bundle();
        switch (id)
        {
            case R.id.admin_home_display_students:

                bundles.putString("view", "student");

                adminDisplayLevelDetailsFragment = new AdminDisplayLevelDetailsFragment();
                adminDisplayLevelDetailsFragment.setArguments(bundles);
                fragmentTransaction.replace(R.id.content_admin_home_fragment_container,adminDisplayLevelDetailsFragment);
                fragmentTransaction.commit();
                break;
            case R.id.admin_home_display_moderators:

                bundles.putString("view", "moderator");

                adminDisplayLevelDetailsFragment = new AdminDisplayLevelDetailsFragment();
                fragmentTransaction.replace(R.id.content_admin_home_fragment_container,adminDisplayLevelDetailsFragment);
                adminDisplayLevelDetailsFragment.setArguments(bundles);
                fragmentTransaction.commit();
                break;
            case R.id.admin_home_display_companies:

                bundles.putString("view", "company");

                adminDisplayLevelDetailsFragment = new AdminDisplayLevelDetailsFragment();
                fragmentTransaction.replace(R.id.content_admin_home_fragment_container,adminDisplayLevelDetailsFragment);
                adminDisplayLevelDetailsFragment.setArguments(bundles);
                fragmentTransaction.commit();
                break;

            case R.id.admin_home_logout:
                final AlertDialog.Builder logoutConfirmation = new AlertDialog.Builder(AdminHomeActivity.this);
                logoutConfirmation.setMessage("Do you wish to log out?");
                logoutConfirmation.setPositiveButton("Go ahead", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        PrefManager prefManager = new PrefManager(AdminHomeActivity.this);
                        prefManager.setFirstTimeLaunch(true);
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AdminHomeActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("token");
                        editor.remove("role");
                        editor.apply();
                        Intent goToLoginActivity = new Intent(getApplicationContext(),Login.class);
                        startActivity(goToLoginActivity);

                    }
                });
                logoutConfirmation.setNegativeButton("Take me back",null);
                logoutConfirmation.show();
                break;
            default:
                adminDisplayLevelDetailsFragment = new AdminDisplayLevelDetailsFragment();
                fragmentTransaction.replace(R.id.content_admin_home_fragment_container,adminDisplayLevelDetailsFragment);
                adminDisplayLevelDetailsFragment.setArguments(bundles);
                fragmentTransaction.commit();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
