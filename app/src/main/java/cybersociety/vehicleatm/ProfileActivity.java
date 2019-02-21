package cybersociety.vehicleatm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import cybersociety.vehicleatm.Fragments.FragmentRegisterVehicle;
import cybersociety.vehicleatm.Fragments.FragmentUserProfile;

public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        AppHelper.init(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LinearLayout container = findViewById(R.id.container);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Snackbar.make(view, "LOGGED OUT..", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Toast.makeText(getApplicationContext(), "LOGGED OUT..", Toast.LENGTH_LONG).show();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            }
        });

        // load nav menu header data
        loadNavHeader();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //add this line to display menu1 when the activity is loaded
        displaySelectedScreen(R.id.nav_profile);
    }

    private void loadNavHeader() {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
       
        //calling the method display selected screen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        return true;
    }

    private void displaySelectedScreen(int itemId) {
        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_profile:
                fragment = new FragmentUserProfile();
                Toast.makeText(getApplicationContext(), "PROFILE FRAGMENT", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_notifications:
                fragment = new FragmentUserProfile();
                Toast.makeText(getApplicationContext(), "PROFILE FRAGMENT", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_registration:
                fragment = new FragmentRegisterVehicle();
                Toast.makeText(getApplicationContext(), "REGISTRATION FRAGMENT", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_report_an_issue:
                fragment = new FragmentUserProfile();
                Toast.makeText(getApplicationContext(), "PROFILE FRAGMENT", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_logout:

                break;
            case R.id.nav_faq:
                fragment = new FragmentUserProfile();
                Toast.makeText(getApplicationContext(), "PROFILE FRAGMENT", Toast.LENGTH_LONG).show();
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
}
