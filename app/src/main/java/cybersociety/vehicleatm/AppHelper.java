package cybersociety.vehicleatm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class AppHelper {
    private static final String TAG = "AppHelper";

    private static String uid;
    private static String token;
    private static String firstName;
    private static String lastName;
    private static String email;
    private static String userType;
    private static String flat_no;
    private static ArrayList<String> mobile_no;
    private static ArrayList<String> vehicle_no;

    private static FirebaseAuth mAuth;
    private static FirebaseApp mApp;
    private static FirebaseUser mUser;
    private static FirebaseInstanceId mInstanceId;
    private static FirebaseFirestore mFirestore;
    private static FirebaseFunctions mFunctions;
    private static FirebaseMessaging mMessaginig;

    private static AppHelper appHelper;

    public static void init(Context context) {
        if (appHelper == null)
            appHelper = new AppHelper();
        if(mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
            mUser = mAuth.getCurrentUser();
        }
        if(mInstanceId == null)
            mInstanceId = FirebaseInstanceId.getInstance();

        mInstanceId.getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        String token = task.getResult().getToken();
                        String msg = "InstanceID Token: " + token;
                        Log.d(TAG, msg);
                    }
                });
        if(mFirestore == null)
            mFirestore = FirebaseFirestore.getInstance();
        if(mFunctions == null)
            mFunctions = FirebaseFunctions.getInstance();
        if(mMessaginig == null){
            mMessaginig = FirebaseMessaging.getInstance();
        }
        mMessaginig.setAutoInitEnabled(true);
    }

    public static FirebaseUser getFirebaseCurrentUser(){
        return mUser;
    }

    public static FirebaseAuth getFirebaseAuth() {
        return  mAuth;
    }
}
