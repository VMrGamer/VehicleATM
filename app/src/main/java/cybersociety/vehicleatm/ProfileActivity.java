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
    private static final String TAG = "ProfileActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            Log.d(TAG, "onCreate: getExtras: " + bundle.toString());
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
                Snackbar.make(view, "LOGGED OUT...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Toast.makeText(getApplicationContext(), "LOGGED OUT..", Toast.LENGTH_LONG).show();
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
                    .setTitle("Close App?")
                    .setMessage("Do you really want to close this beautiful app?")
                    .setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    finish();
                                }
                            })
                    .setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                }
                            }).show();
        }
    }

    /*
    *  new AlertDialog.Builder(this)
                    .setTitle("Close App?")
                    .setMessage("Do you really want to close this beautiful app?")
                    .setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                        int which) {
                                    finish();
                                }
                            })
                    .setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                        int which) {
                                }
                            }).show();
            // load your first Fragment
            */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
       
        //calling the method display selected screen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        return true;
    }

    public void displaySelectedScreen(int itemId) {
        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_home:
                fragment = FragmentUserProfile.newInstance();
                Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_notifications:
                fragment = FragmentNotification.newInstance();
                Toast.makeText(getApplicationContext(), "Notifications", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_registration:
                fragment = new FragmentRegisterVehicle();
                Toast.makeText(getApplicationContext(), "Registration Fragment", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_24hrfeed:
                ArrayList<String> collectionPaths = new ArrayList<>();
                //collectionPaths.add("entry-exit-buffer");
                //collectionPaths.add("log-acknowledged");
                //collectionPaths.add("log-unacknowledged");
                collectionPaths.add("log-vehicle");

                fragment = FragmentFeed24hr.newInstance(collectionPaths, "yo", "yo");
                Toast.makeText(getApplicationContext(), "24Hr Feed Fragment", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_report_an_issue:
                fragment = new FragmentUserProfile();
                Toast.makeText(getApplicationContext(), "Profile Fragment", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_logout:
                Toast.makeText(getApplicationContext(), "Logging Out..", Toast.LENGTH_LONG).show();
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                break;
            case R.id.nav_reg_veh:
                fragment = FragmentViewVehicle.newInstance();
                Toast.makeText(getApplicationContext(), "Registered Veh.", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_settings:
                fragment = new FragmentUserProfile();
                Toast.makeText(getApplicationContext(), "PROFILE FRAGMENT", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_additional_info:
                fragment = new FragmentUserProfile();
                Toast.makeText(getApplicationContext(), "PROFILE FRAGMENT", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_about_us:
                fragment = new FragmentUserProfile();
                Toast.makeText(getApplicationContext(), "PROFILE FRAGMENT", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_about_app:
                fragment = new FragmentUserProfile();
                Toast.makeText(getApplicationContext(), "PROFILE FRAGMENT", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(getApplicationContext(), "CHOOSE PROFILE", Toast.LENGTH_LONG).show();
        }

        //replacing the fragment
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
