package cybersociety.vehicleatm;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import cybersociety.vehicleatm.fragments.FragmentRegisterFeed;
import cybersociety.vehicleatm.fragments.FragmentRegisterVehicle;
import cybersociety.vehicleatm.fragments.feed24hr.FragmentFeed24hr;
import cybersociety.vehicleatm.fragments.notification.FragmentNotification;
import cybersociety.vehicleatm.fragments.userprofile.FragmentUserProfile;
import cybersociety.vehicleatm.fragments.viewvehicle.FragmentViewVehicle;

public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentFeed24hr.OnFragmentInteractionListener,
        FragmentUserProfile.OnFragmentInteractionListener,
        FragmentNotification.OnFragmentInteractionListener,
        FragmentViewVehicle.OnFragmentInteractionListener{
    private static final String TAG = ProfileActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
            if (bundle.containsKey("data")) {
                Log.d(TAG, "onCreate: containsKey" + bundle.getBundle("data").getString("title"));
            }
        }
        else{
            Log.d(TAG, "onCreate: bundle null");
        }
        //code starts
        AppHelper.init(getApplicationContext());
        AppHelper.loginFieldGet();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Snackbar.make(view, "Logged Out!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Toast.makeText(getApplicationContext(), "Logged Out!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            }
        });


        // load nav menu header data
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //add this line to display menu1 when the activity is loaded
        displaySelectedScreen(R.id.nav_home);
        loadNavHeader();
    }

    private void loadNavHeader() {
        final TextView user_name, user_email;
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        user_name = headerView.findViewById(R.id.nav_header_title);
        user_email = headerView.findViewById(R.id.nav_header_subtitle);
        Thread th = new Thread(new Runnable() {
            private long startTime = System.currentTimeMillis();
            public void run() {
                while (AppHelper.getFirstName() == null) {
                    runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            user_email.setText(""+((System.currentTimeMillis()-startTime)/1000));
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        user_name.setText(String.format("%s %s", AppHelper.getFirstName(), AppHelper.getLastName()));
                        user_email.setText(AppHelper.getEmail());
                    }
                });
            }
        });
        th.start();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent: Im Here");
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, FragmentRegisterFeed.newInstance(bundle.getString("vehicle_no"), bundle.getString("did")))
                    .commit();
        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            new AlertDialog.Builder(this)
                    .setTitle("Exiting")
                    .setMessage("Do you really want to exit?")
                    .setPositiveButton("Exit",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    finish();
                                }
                            })
                    .setNegativeButton("Go Back",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                }
                            }).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }

    public void displaySelectedScreen(int itemId) {
        Fragment fragment = null;
        switch (itemId) {
            case R.id.nav_home:
                fragment = FragmentUserProfile.newInstance();
                break;
            case R.id.nav_notifications:
                fragment = FragmentNotification.newInstance();
                break;
            case R.id.nav_24hrfeed:
                ArrayList<String> collectionPaths = new ArrayList<>();
                collectionPaths.add("log-vehicle");
                fragment = FragmentFeed24hr.newInstance(collectionPaths);
                break;
            case R.id.nav_report_an_issue:
                fragment = new FragmentUserProfile();
                break;
            case R.id.nav_reg_veh:
                fragment = FragmentViewVehicle.newInstance();
                break;
            case R.id.nav_guest_registration:
                fragment = new FragmentRegisterVehicle();
                break;
            case R.id.nav_vehicle_registration:
                fragment = new FragmentRegisterVehicle();
                break;
            case R.id.nav_logout:
                 new AlertDialog.Builder(this)
                    .setTitle("Log Out")
                    .setMessage("Do you really want to log out from your account?")
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                        int which) {
                                        FirebaseAuth.getInstance().signOut();
                                        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                                        finish();
                                }
                            })
                    .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                        int which) {
                                }
                            }).show();
                break;
            case R.id.nav_settings:
                fragment = new FragmentUserProfile();
                break;
            case R.id.nav_additional_info:
                fragment = new FragmentUserProfile();
                break;
            case R.id.nav_about_us:
                fragment = new FragmentUserProfile();
                break;
            case R.id.nav_about_app:
                fragment = new FragmentUserProfile();
                break;
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
