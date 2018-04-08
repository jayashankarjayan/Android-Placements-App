package com.example.jayashankarjayan.placements;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jayashankarjayan.placements.functions.Functions;
import com.example.jayashankarjayan.placements.functions.LocalDatabaseHandler;

import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout linearLayout;
    Menu menu;
    MenuItem menu_home,menu_user_login,menu_user_profile,menu_edit_user_profile,
            menu_user_logout,menu_share,menu_notices,menu_apply,menu_applied_companies,menu_chat,menu_company_invites;
    String menuText,studentCV;
    int menuIcon;
    boolean isCurrentActivity = true, isLoggedIn;
    TextView nav_header_user_name_short,nav_header_name_of_user,nav_header_username;
    FrameLayout activity_main_fragment_container;
    LocalDatabaseHandler localDatabaseHandler;
    Functions functions;
    static final int  WRITE_EXTERNAL_STORAGE = 1,READ_EXTERNAL_STORAGE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        functions = new Functions(getApplicationContext());
        localDatabaseHandler = new LocalDatabaseHandler(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        linearLayout = (LinearLayout) findViewById(R.id.activity_main_linear_layout);
        activity_main_fragment_container = (FrameLayout) findViewById(R.id.activity_main_fragment_container);

        if (functions.checkNetworkState()) {
            if (!functions.loginCheck()) {
                isCurrentActivity = false;
                Intent goToLoginActivity = new Intent(getApplicationContext(), Login.class);
                startActivity(goToLoginActivity);
            } else {


                if(functions.getRole().equals("student"))
                {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    MainFragment mainFragment = new MainFragment();
                    fragmentTransaction.add(R.id.activity_main_fragment_container, mainFragment);
                    fragmentTransaction.addToBackStack("main_activity_initally_loaded");
                    fragmentTransaction.commit();

                    permissions();

                    runThread();
                }
                if(functions.getRole().equals("admin"))
                {
                    startActivity(new Intent(getApplicationContext(),AdminHomeActivity.class));
                    finish();
                }
//                finish();

            }
        }

//        runThread();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        // get menu from navigationView
        menu = navigationView.getMenu();

        menu_home = menu.findItem(R.id.menu_home);
        menu_user_profile = menu.findItem(R.id.menu_user_profile);
        menu_edit_user_profile = menu.findItem(R.id.menu_edit_user_profile);
        menu_user_login = menu.findItem(R.id.menu_user_login);
        menu_user_logout = menu.findItem(R.id.menu_user_logout);
        menu_share = menu.findItem(R.id.menu_share);
        menu_notices = menu.findItem(R.id.menu_notices);
        menu_apply = menu.findItem(R.id.menu_apply);
        menu_applied_companies = menu.findItem(R.id.menu_applied_companies);
        menu_chat = menu.findItem(R.id.menu_chat);
        menu_company_invites = menu.findItem(R.id.menu_invitations);

        if (functions.loginCheck()) {
            //User is now logged in
            menu_user_profile.setVisible(true);
            menu_edit_user_profile.setVisible(true);
            menu_notices.setVisible(true);
            menu_apply.setVisible(true);
            menu_applied_companies.setVisible(true);
            menu_share.setVisible(true);
            menu_chat.setVisible(true);
            menu_user_login.setVisible(false);
            menu_company_invites.setVisible(true);
            isLoggedIn = true;
        } else {
            //User not logged in
            menu_user_profile.setVisible(false);
            menu_user_logout.setVisible(false);
            menu_share.setVisible(false);
            menu_notices.setVisible(false);
            menu_apply.setVisible(false);
            menu_applied_companies.setVisible(false);
            menu_chat.setVisible(false);
            menu_company_invites.setVisible(false);
            isLoggedIn = false;
        }

        menu_home.setTitle("Home");
        menu_home.setIcon(R.drawable.home);
        menu_home.setChecked(true);

        menu_edit_user_profile.setTitle("Edit Profile");
        menu_edit_user_profile.setIcon(R.drawable.edit_my_profile);

        menu_user_profile.setTitle("My profile");
        menu_user_profile.setIcon(R.drawable.my_profile);

        menu_user_login.setTitle("Login");
        menu_user_login.setIcon(R.drawable.login);

        menu_user_logout.setTitle("Logout");
        menu_user_logout.setIcon(R.drawable.logout);

        menu_share.setTitle("Share My Resume");
        menu_share.setIcon(R.drawable.share);

        menu_notices.setTitle("Notices");
        menu_notices.setIcon(R.drawable.notices);

        menu_apply.setTitle("Apply");
        menu_apply.setIcon(R.drawable.apply_to_company);

        menu_applied_companies.setTitle("Applied companies");
        menu_applied_companies.setIcon(R.drawable.applied_to_company);

        menu_company_invites.setTitle("Invitations");
        menu_company_invites.setIcon(R.drawable.company_invitation);

        menu_chat.setTitle("Discussion Forum");
        menu_chat.setIcon(R.drawable.forum);
        menu_chat.setVisible(false);

        navigationView.setNavigationItemSelectedListener(this);

        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        android.app.FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            Toast.makeText(this, ""+fragmentManager.getBackStackEntryCount(), Toast.LENGTH_SHORT).show();
        } else {
//            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }

        if(isLoggedIn)
        {
            //User is now logged in
            menu_user_profile.setVisible(true);
            menu_share.setVisible(true);
            menu_user_login.setVisible(false);
            isLoggedIn = true;
        }
        else
        {
            menu_user_profile.setVisible(false);
            menu_user_logout.setVisible(false);
            menu_share.setVisible(false);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainFragment mainFragment = new MainFragment();
        switch (id)
        {
            case  R.id.menu_home:

                if (!mainFragment.isVisible())
                {
                    fragmentTransaction.replace(R.id.activity_main_fragment_container,mainFragment,"MainFragment");
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                    activity_main_fragment_container.setBackgroundColor(getResources().getColor(R.color.root_layout_background_shade));
                    fragmentTransaction.addToBackStack("main_activity");
                    fragmentTransaction.commit();
                }
                break;
            case R.id.menu_notices:
                NoticesFragment noticesFragment = new NoticesFragment();

                if (!noticesFragment.isVisible())
                {
                    fragmentTransaction.replace(R.id.activity_main_fragment_container,noticesFragment,"NoticesFragment");
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                    fragmentTransaction.remove(mainFragment);
                    activity_main_fragment_container.setBackgroundColor(getResources().getColor(R.color.root_layout_background_shade));
                    fragmentTransaction.addToBackStack("notices_fragment");
                    fragmentTransaction.commit();
                }
                break;
            case R.id.menu_applied_companies:
                AppliedCompaniesFragment appliedCompaniesFragment = new AppliedCompaniesFragment();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.activity_main_fragment_container,appliedCompaniesFragment);
                activity_main_fragment_container.setBackgroundColor(getResources().getColor(R.color.root_layout_background_shade));
                fragmentTransaction.addToBackStack("applied_companies_fragment");
                fragmentTransaction.commit();
                break;
            case R.id.menu_apply:
                ApplyCompanyFragment applyCompanyFragment = new ApplyCompanyFragment();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.activity_main_fragment_container,applyCompanyFragment);
                activity_main_fragment_container.setBackgroundColor(getResources().getColor(R.color.root_layout_background_shade));
                fragmentTransaction.addToBackStack("apply_to_companies_fragment");
                fragmentTransaction.commit();
                break;
            case R.id.menu_invitations:
                CompanyInvitationsFragment companyInvitationsFragment = new CompanyInvitationsFragment();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.activity_main_fragment_container,companyInvitationsFragment);
                activity_main_fragment_container.setBackgroundColor(getResources().getColor(R.color.root_layout_background_shade));
                fragmentTransaction.addToBackStack("invitations_fragment");
                fragmentTransaction.commit();
                break;
            case R.id.menu_chat:
                DiscussionForumFragment discussionForumFragment = new DiscussionForumFragment();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.activity_main_fragment_container,discussionForumFragment);
                activity_main_fragment_container.setBackgroundColor(getResources().getColor(R.color.root_layout_background_shade));
                fragmentTransaction.addToBackStack("discussion_threads_fragment");
                fragmentTransaction.commit();
                break;
//            case  R.id.menu_user_login:
//                final Intent loginActivity = new Intent(getApplicationContext(),Login.class);
//                startActivity(loginActivity);
//                break;
            case R.id.menu_edit_user_profile:
                Intent goToEditProfileActivity = new Intent(getApplicationContext(),EditPersonalDetails.class);
                startActivity(goToEditProfileActivity);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;

            case R.id.menu_user_profile:
                ViewUserProfileFragment viewUserProfileFragment = new ViewUserProfileFragment();
                if (!viewUserProfileFragment.isVisible()) {
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.activity_main_fragment_container, viewUserProfileFragment, "ViewUserProfile");
                    activity_main_fragment_container.setBackgroundColor(getResources().getColor(R.color.white));
                    fragmentTransaction.addToBackStack("view_user_profile_fragment");
                    fragmentTransaction.commit();
                    menu_user_profile.setChecked(true);
                }
                break;
            case R.id.menu_share:

                try
                {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { null });
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Resume - "+functions.getNameOfUser());
                    shareIntent.putExtra(Intent.EXTRA_TEXT, getStudentCV());
                    /*shareIntent.putExtra(Intent.EXTRA_STREAM, getStudentCV());*/
                    startActivity(shareIntent);
                }
                catch (NullPointerException empty)
                {
                    Snackbar noAppFoundSnackBar = Snackbar.make(linearLayout,
                            R.string.please_try_again_after_some_time, Snackbar.LENGTH_SHORT);
                    noAppFoundSnackBar.show();
                }
                catch (ActivityNotFoundException e)
                {
                    Snackbar noAppFoundSnackBar = Snackbar.make(linearLayout,
                            R.string.no_app_found, Snackbar.LENGTH_SHORT);
                    noAppFoundSnackBar.show();

                }
                break;
            case R.id.menu_user_logout:
                final AlertDialog.Builder logoutConfirmation = new AlertDialog.Builder(MainActivity.this);
                logoutConfirmation.setMessage("Do you wish to log out?");
                logoutConfirmation.setPositiveButton("Go ahead", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        PrefManager prefManager = new PrefManager(MainActivity.this);
                        prefManager.setFirstTimeLaunch(true);
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("token");
                        editor.remove("username");
                        editor.remove("id");
                        editor.remove("college_id");
                        editor.remove("course_id");
                        editor.apply();
                        isLoggedIn = false;

                        localDatabaseHandler.dropTheDatabase();
                        Intent goToLoginActivity = new Intent(getApplicationContext(),Welcome.class);
                        startActivity(goToLoginActivity);

                    }
                });
                logoutConfirmation.setNegativeButton("Take me back",null);
                logoutConfirmation.show();
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isCurrentActivity = true;
    }

    @Override
    protected void onResume () {
        super.onResume();
        isCurrentActivity = true;
        if(!functions.loginCheck())
        {
            isCurrentActivity = false;
            Intent goToLoginActivity = new Intent(getApplicationContext(), Login.class);
            startActivity(goToLoginActivity);
        }

        if(isLoggedIn)
        {
            //User is now logged in
            menu_user_profile.setVisible(true);
            menu_share.setVisible(true);
            menu_user_login.setVisible(false);
            isLoggedIn = true;
        }
        else
        {
            menu_user_profile.setVisible(false);
            menu_user_logout.setVisible(false);
            menu_share.setVisible(false);
            menu_notices.setVisible(false);
            menu_apply.setVisible(false);
            menu_applied_companies.setVisible(false);
            menu_chat.setVisible(false);
            menu_company_invites.setVisible(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isCurrentActivity = false;

        if(isLoggedIn)
        {
            //User is now logged in
            menu_user_profile.setVisible(true);
            menu_share.setVisible(true);
            menu_user_login.setVisible(false);
            isLoggedIn = true;
        }
        else
        {
            menu_user_profile.setVisible(false);
            menu_user_logout.setVisible(false);
            menu_share.setVisible(false);
        }
    }

    public String getMenuText() {
        Toast.makeText(getApplicationContext(),this.menuText,Toast.LENGTH_SHORT).show();
        return this.menuText;
    }

    public void setMenuItemText(String menuText)
    {
        this.menuText = menuText;
    }

    public int getMenuIcon() {
        return menuIcon;
    }

    public void setIcon(int icon) {
        this.menuIcon = menuIcon;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void setStudentCV(String studentCV) {
        this.studentCV = functions.getLink().substring(0,(functions.getLink().length()-1))+studentCV.substring(2);
    }

    public String getStudentCV() {
        if(studentCV.equals(""))
        {
            return "";
        }
        return studentCV;
    }

    private void runThread() {

        new Thread() {
            public void run() {
                    try {
                        sleep(5000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                    new GetAllUserData().execute();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        .start();
    }

    private void permissions()
    {
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this)
                        .setMessage("You need to provide with external storage permissions to proceed");
                builder.show();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_EXTERNAL_STORAGE);
            }

        }

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this)
                        .setMessage("You need to provide with external storage permissions to proceed");
                builder.show();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},READ_EXTERNAL_STORAGE );
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this)
                            .setMessage("Permission granted")
                            .setPositiveButton("Ok",null);
                    builder.show();

                } else {
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this)
                            .setMessage("Permission not granted");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.show();
                }

            break;

            case READ_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this)
                            .setMessage("Permission granted")
                            .setPositiveButton("Ok",null);
                    builder.show();

                } else {
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this)
                            .setMessage("Permission not granted");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.show();
                break;
            }
        }
    }

    private class GetAllUserData extends AsyncTask<String, String, String>
    {
        private static final int READ_TIMEOUT = 15000;
        private static final int CONNECT_TIMEOUT = 10000;
        URL url = null;
        HttpURLConnection conn;
        String post_data,name_of_the_student,username,studentNameShort,studentCV,result="",success;
        final Functions functions = new Functions(getApplicationContext());
        int userId = functions.getId();

        @Override
        protected String doInBackground(String... strings) {

            name_of_the_student = localDatabaseHandler.getStudentDetails("name_of_student");
            username = localDatabaseHandler.getStudentDetails("username");
            if(!name_of_the_student.equals(""))
            {
                studentNameShort = name_of_the_student.substring(0,1);
            }

            studentCV = localDatabaseHandler.getStudentDetails("student_cv");
            if(!studentCV.equals(""))
            {
                setStudentCV(studentCV);
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            nav_header_user_name_short = (TextView)findViewById(R.id.nav_header_user_name_short);
            nav_header_name_of_user = (TextView)findViewById(R.id.nav_header_name_of_user);
            nav_header_username = (TextView)findViewById(R.id.nav_header_username);
            nav_header_user_name_short.setText(studentNameShort);
//            functions.loadImage(functions.getLink()+studentImage,nav_header_user_image,"rounded",300,0);
            nav_header_name_of_user.setText(name_of_the_student);
            nav_header_username.setText(username);

            this.cancel(true);
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}