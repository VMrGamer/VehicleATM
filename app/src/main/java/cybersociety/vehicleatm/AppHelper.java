package cybersociety.vehicleatm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Map;

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
    private static Map<String,Object> currUserAttributes;

    private static FirebaseAuth mAuth;
    private static FirebaseApp mApp;
    private static FirebaseUser mUser;
    private static FirebaseInstanceId mInstanceId;
    private static FirebaseFirestore mFirestore;
    private static FirebaseFunctions mFunctions;
    private static FirebaseMessaging mMessaginig;

    private static AppHelper appHelper;

    public static void init() {
        if (appHelper == null)
            appHelper = new AppHelper();
        else {
            appHelper = null;
            appHelper = new AppHelper();
        }
        if(mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
            mUser = mAuth.getCurrentUser();
        }
        else{
            mAuth = null;
            mUser = null;
            mAuth = FirebaseAuth.getInstance();
            mUser = mAuth.getCurrentUser();
        }
        if(mInstanceId == null)
            mInstanceId = FirebaseInstanceId.getInstance();
        else{
            mInstanceId = null;
            mInstanceId = FirebaseInstanceId.getInstance();
        }
        mInstanceId.getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        token = task.getResult().getToken();
                        String msg = "InstanceID Token: " + token;
                        Log.d(TAG, msg);
                    }
                });

        if(mFirestore == null)
            mFirestore = FirebaseFirestore.getInstance();
        else{
            mFirestore = null;
            mFirestore = FirebaseFirestore.getInstance();
        }
        if(mFunctions == null)
            mFunctions = FirebaseFunctions.getInstance();
        else{
            mFunctions= null;
            mFunctions = FirebaseFunctions.getInstance();
        }
        if(mMessaginig == null){
            mMessaginig = FirebaseMessaging.getInstance();
        }
        else {
            mMessaginig = null;
            mMessaginig = FirebaseMessaging.getInstance();
        }
        mMessaginig.setAutoInitEnabled(true);
    }

    public static Map<String, Object> getCurrUserAttributes(){
        return currUserAttributes;
    }

    public static void loginFieldGet(){
        DocumentReference docRef = mFirestore.collection("users").document(mUser.getUid());
        Source source = Source.SERVER;
        boolean updateValue = false;
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    currUserAttributes = document.getData();
                    Log.d(TAG, "Cached document data: " + document.getData());
                } else {
                    Log.d(TAG, "Cached get failed: ", task.getException());
                }
            }
        });

        if (currUserAttributes.containsKey("token")){
            if(currUserAttributes.get("token") != token){
                currUserAttributes.put("token", token);
                updateValue = true;
            }
        }
        else{
            currUserAttributes.put("token",token);
            updateValue = true;
        }

        if(updateValue){

        }
    }

    public static void updateFirestore(String collection, String document){

    }

    public static void setToken(String t){
        if(t == null || t == ""){
            Log.d(TAG, "setToken: Invalid Operation");
            return;
        }
        if(token == null || token == ""){
            Log.d(TAG, "setToken: Token Empty");
            token = t;
        }
        else if (token != t){
            token = t;
        }
    }

    public static String getToken(){
        return token;
    }

    public static FirebaseUser getFirebaseCurrentUser(){
        return mUser;
    }

    public static FirebaseAuth getFirebaseAuth() {
        return  mAuth;
    }

    public static FirebaseFirestore getFirestore(){
        return mFirestore;
    }
}
