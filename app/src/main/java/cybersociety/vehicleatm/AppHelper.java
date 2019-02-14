package cybersociety.vehicleatm;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.Arrays;
import java.util.List;
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
    private static List<String> mobile_no;
    private static List<String> vehicle_no;
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
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    currUserAttributes = document.getData();
                    boolean updateValue = false;
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
                    setUserData();
                    if(updateValue){
                        updateFirestore("users", uid, currUserAttributes);
                    }
                    Log.d(TAG, "Cached document data: " + document.getData());
                } else {
                    Log.d(TAG, "Cached get failed: ", task.getException());
                }
            }
        });

    }

    private static void setUserData() {
        uid = currUserAttributes.get("uid").toString();
        token = currUserAttributes.get("token").toString();
        firstName = ((List<String>)currUserAttributes.get("name")).get(0);
        lastName = ((List<String>)currUserAttributes.get("name")).get(0);
        email = currUserAttributes.get("email").toString();
        userType = currUserAttributes.get("user_type").toString();
        flat_no = currUserAttributes.get("flat_no").toString();
        mobile_no = (List<String>)currUserAttributes.get("mobile_no");
        vehicle_no = (List<String>)currUserAttributes.get("vehicles");
    }

    public static void updateFirestore(String collectionPath, String document, Map<String, Object> doc){
        mFirestore.collection(collectionPath)
            .document(document)
            .set(doc)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "onSuccess: DocumentSnapshot successfully written!");
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "onFailure: Error writing document", e);
                }
            });
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
        else Log.d(TAG, "setToken: Token Equal");

        currUserAttributes.put("token", token);
        updateFirestore("users", uid, currUserAttributes);
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
