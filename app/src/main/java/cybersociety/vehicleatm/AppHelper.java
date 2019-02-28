package cybersociety.vehicleatm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AppHelper {
    private static final String TAG = "AppHelper";

    //Data for more accessibility
    private static String uid;
    private static String token;
    private static String firstName;
    private static String lastName;
    private static String email;
    private static String userType;
    private static String flat_no;
    private static List<String> mobile_no;
    private static List<String> vehicle_no;

    //Master Variable Map
    private static Map<String, Object> currUserAttributes;
    private static ArrayList<Map<String, Object>> notification = new ArrayList<>();

    //Firebase Inits
    private static FirebaseAuth mAuth;
    private static FirebaseUser mUser;
    private static FirebaseInstanceId mInstanceId;
    private static FirebaseFunctions mFunctions;
    private static FirebaseMessaging mMessaginig;

    private static AppHelper appHelper;

    static void init(Context context) {
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
                        token = Objects.requireNonNull(task.getResult()).getToken();
                        String msg = "InstanceID Token: " + token;
                        Log.d(TAG, msg);
                    }
                });
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
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(mUser.getUid());
        Source source = Source.SERVER;
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        currUserAttributes = document.getData();
                        boolean updateValue = false;
                        if (currUserAttributes != null) {
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
                        }
                        setUserData();
                        if(updateValue){
                            updateFirestore("users", uid, currUserAttributes);
                        }
                        Log.d(TAG, "Cached document data: " + document.getData());
                    } else {
                        Log.d(TAG, "onComplete: Something is wrong, contact developer");
                    }
                } else {
                    Log.d(TAG, "Cached get failed: ", task.getException());
                }
            }
        });
    }

    //Getting Notifications
    private static void getNotification(){
        FirebaseFirestore.getInstance().collection("users").document(uid).collection("notification").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                notification.add(document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    //Setting up more accessible fields to use in the App
    private static void setUserData() {
        uid = Objects.requireNonNull(currUserAttributes.get("uid")).toString();
        token = Objects.requireNonNull(currUserAttributes.get("token")).toString();
        firstName = ((List<String>) Objects.requireNonNull(currUserAttributes.get("name"))).get(0);
        lastName = ((List<String>) Objects.requireNonNull(currUserAttributes.get("name"))).get(1);
        email = Objects.requireNonNull(currUserAttributes.get("email")).toString();
        userType = Objects.requireNonNull(currUserAttributes.get("user_type")).toString();
        flat_no = Objects.requireNonNull(currUserAttributes.get("flat_no")).toString();
        mobile_no = (List<String>)currUserAttributes.get("mobile_no");
        vehicle_no = (List<String>)currUserAttributes.get("vehicles");
    }//You can reference the assignments for use of the currUserAttributes Map, or any other Document Snapshot

    //A General function to add or update firestore documents on an already existing collection
    public static void updateFirestore(String collectionPath, String document, Map<String, Object> doc){
        FirebaseFirestore.getInstance().collection(collectionPath)
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

    //to set token on new token generation
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
        return FirebaseFirestore.getInstance();
    }

    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String firstName) {
        AppHelper.firstName = firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        AppHelper.lastName = lastName;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        AppHelper.email = email;
    }

    public static String getUserType() {
        return userType;
    }

    public static void setUserType(String userType) {
        AppHelper.userType = userType;
    }

    public static String getFlat_no() {
        return flat_no;
    }

    public static void setFlat_no(String flat_no) {
        AppHelper.flat_no = flat_no;
    }

    public static List<String> getMobile_no() {
        return mobile_no;
    }

    public static void setMobile_no(List<String> mobile_no) {
        AppHelper.mobile_no = mobile_no;
    }

    public static List<String> getVehicle_no() {
        return vehicle_no;
    }

    public static void setVehicle_no(List<String> vehicle_no) {
        AppHelper.vehicle_no = vehicle_no;
    }
}
