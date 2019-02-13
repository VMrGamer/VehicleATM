package cybersociety.vehicleatm;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.iid.FirebaseInstanceId;

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
        if(mFirestore == null)
            mFirestore = FirebaseFirestore.getInstance();
        if(mFunctions == null)
            mFunctions = FirebaseFunctions.getInstance();
    }

    public static FirebaseUser getFirebaseCurrentUser(){
        return mUser;
    }

    public static FirebaseAuth getFirebaseAuth() {
        return  mAuth;
    }
}
