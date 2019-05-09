package cybersociety.vehicleatm;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {
    private static final String TAG = SplashScreenActivity.class.getSimpleName();
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FirebaseApp.initializeApp(getApplicationContext());
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                handler = new Handler();
                if (user != null) {
                    Log.d("@@@@", "splash:signed_in:" + user.getUid());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AppHelper.init(getApplicationContext());
                            AppHelper.loginFieldGet();
                            Intent home = new Intent(SplashScreenActivity.this, ProfileActivity.class);
                            startActivity(home);
                            finish();
                        }
                    }, 2000);
                } else {
                    Log.d("@@@@", "splash:signed_out");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent login = new Intent(SplashScreenActivity.this, LoginActivity.class);
                            startActivity(login);
                            finish();
                        }
                    }, 2000);
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
