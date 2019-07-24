package com.youngmind.oasiscab_driver.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.youngmind.oasiscab_driver.R;
import com.google.android.material.navigation.NavigationView;
import com.youngmind.oasiscab_driver.fragments.Home;
import com.youngmind.oasiscab_driver.notifications.InitialTokenPush;

import static com.youngmind.oasiscab_driver.Config.NOTIFICATION_API;

//import androidx.legacy.app.ActionBarDrawerToggle;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final String TAG_HOME = "home";
    private static final String TAG_PROFILE = "profile";
    private static final String TAG_FAVOURITE_LOCATION = "favouriteLocation";
    private static final String TAG_PAST_TRIP = "pastTrip";
    private static final String TAG_REPORT_ISSUE = "reportIssue";
    private static final String TAG_SETTINGS = "settings";
    private static final String TAG_HELP_AND_SUPPORT = "helpAndSupport";
    public static String CURRENT_TAG = TAG_HOME;

    // index to identify current nav menu item
    public static int navItemIndex = 0;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;

            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

        //start the notification service
        /*Intent serviceIntent = new Intent();
        serviceIntent.setAction("com.youngmind.services.NotificationService");
        startService(serviceIntent);*/

        //send push notification token to server as soon as the user logs in
        new InitialTokenPush(getApplicationContext()).pushTokenToServer(NOTIFICATION_API);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadHomeFragment() {
        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            return;
        }
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                Home homeFragment = new Home();

                return homeFragment;

//            case 1:
//                // Profile
//                MyProfileFragment myProfileFragment = new MyProfileFragment();
//
//                return myProfileFragment;
//
//            case 2:
//                // Favourite Location fragment
//                FavouriteLocation favouriteLocation = new FavouriteLocation();
//
//                return favouriteLocation;
//
//            case 3:
//                // past trips fragment
//                PastTrips pastTrips = new PastTrips();
//
//                return pastTrips;
//
//            case 4:
//                // Report issue fragment
//                ReportIssue reportIssue = new ReportIssue();
//
//                return reportIssue;
//
//
//            case 5:
//                // Settings fragment
//                Settings settings = new Settings();
//
//                return settings;
//
//            case 6:
//                // Help and Support fragment
//                HelpAndSupport helpAndSupport = new HelpAndSupport();
//
//                return helpAndSupport;

            default:
                return new Home();
        }
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_profile:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_PROFILE;
                        break;
                    case R.id.nav_past_trips:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_PAST_TRIP;
                        break;
                    case R.id.nav_settings:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_SETTINGS;
                        break;
                    case R.id.nav_help_and_support:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_HELP_AND_SUPPORT;
                        break;
                    case R.id.nav_report_issue:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_REPORT_ISSUE;
                        break;

                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }
}
