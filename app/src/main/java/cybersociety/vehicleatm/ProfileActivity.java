package cybersociety.vehicleatm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cybersociety.vehicleatm.fragments.FragmentRegisterVehicle;
import cybersociety.vehicleatm.fragments.feed24hr.Fragment24HrFeed;
import cybersociety.vehicleatm.fragments.userprofile.FragmentUserProfile;
import io.opencensus.tags.Tag;

public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Fragment24HrFeed.OnFragmentInteractionListener, FragmentUserProfile.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //code starts
        AppHelper.init(getApplicationContext());
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
        loadNavHeader();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //add this line to display menu1 when the activity is loaded
        displaySelectedScreen(R.id.nav_home);
    }

    private void loadNavHeader() {
        final TextView user_name, user_email;

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        user_name = headerView.findViewById(R.id.nav_header_title);
        user_email = headerView.findViewById(R.id.nav_header_subtitle);

        DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String,Object> currUserAttributes = document.getData();
                        String name="";
                        String firstName = ((List<String>)currUserAttributes.get("name")).get(0);
                        String lastName = ((List<String>)currUserAttributes.get("name")).get(1);
                        name = firstName + " " + lastName;

                        String email = document.getString("email");
                        user_name.setText(name);
                        user_email.setText(email);
                        Log.d("MSG", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("MSG", "No such document");
                    }
                } else {
                    Log.d("MSG", "get failed with ", task.getException());
                }
            }
        });
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
                Toast.makeText(getApplicationContext(), "HOME", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_notifications:
                fragment = new FragmentUserProfile();
                Toast.makeText(getApplicationContext(), "PROFILE FRAGMENT", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_registration:
                fragment = new FragmentRegisterVehicle();
                Toast.makeText(getApplicationContext(), "REGISTRATION FRAGMENT", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_24hrfeed:
                ArrayList<String> collectionPaths = new ArrayList<>();
                collectionPaths.add("entry-exit-buffer");
                collectionPaths.add("log-acknowledged");
                collectionPaths.add("log-unacknowledged");

                fragment = Fragment24HrFeed.newInstance(collectionPaths, "yo", "yo");
                Toast.makeText(getApplicationContext(), "24Hr Feed FRAGMENT", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_report_an_issue:
                fragment = new FragmentUserProfile();
                Toast.makeText(getApplicationContext(), "PROFILE FRAGMENT", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                Toast.makeText(getApplicationContext(), "LOGGED OUT..", Toast.LENGTH_LONG).show();
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
