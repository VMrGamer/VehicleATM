package cybersociety.vehicleatm;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends FragmentActivity implements RecyclerViewFragment.OnFragmentInteractionListener {

    private static final String TAG = "MainActivity";

    private FloatingActionButton signOutButton;

    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppHelper.init(getApplicationContext());
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        initApp();
    }

    private void initApp() {
        mAuth = AppHelper.getFirebaseAuth();

        //Sign out Button
        signOutButton = findViewById(R.id.sign_out_button_main);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent login = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login);
                finish();
            }
        });

        //Tabbed Layout
        viewPager = findViewById(R.id.view_pager);
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                onChangeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        AppHelper.loginFieldGet();
    }

    @Override
    public void onBackPressed() {
        Intent profA = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(profA);
        finish();
    }

    private void onChangeTab(int position) {
        //TODO: Implement Something fishy here
        if(position == 0) {

        }

        if (position == 1) {

        }

        if (position == 2) {

        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
