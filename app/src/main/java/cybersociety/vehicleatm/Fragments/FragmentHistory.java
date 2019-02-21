package cybersociety.vehicleatm.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cybersociety.vehicleatm.AppHelper;
import cybersociety.vehicleatm.R;
import cybersociety.vehicleatm.RecyclerViewFragment;

public class FragmentHistory extends Fragment{
    private static final String TAG = "FragmentHistory";

    private String title;
    private int page;
    private Map<String, Object> dataEEBuffer;
    private Map<String,Object> dataLogUnack;
    private Map<String, Object> dataLogAck;
    private List<DocumentSnapshot> EEBuffer;
    private List<DocumentSnapshot> logUnack;
    private List<DocumentSnapshot> logAck;
    private RecyclerViewFragment recyclerViewFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_history,null);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        fragmentManager.beginTransaction();
        recyclerViewFragment = RecyclerViewFragment.newInstance();
        Bundle args = new Bundle();
        recyclerViewFragment.setArguments(args);
        fragmentTransaction.add(R.id.fragment_history_container, recyclerViewFragment);
        fragmentTransaction.commit();
        return layout;
    }

    public static FragmentHistory newInstance(int page, String title) {
        FragmentHistory fragmentHistory = new FragmentHistory();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentHistory.setArguments(args);
        return fragmentHistory;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");

        Source source = Source.SERVER;
        AppHelper.getFirestore().collection("entry-exit-buffer").whereGreaterThanOrEqualTo("timestamp_entry", new Timestamp(new Date()))
                .get(source).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot querySnapshot = task.getResult();
                    EEBuffer = querySnapshot.getDocuments();
                    Log.d(TAG, "onComplete: EEB Cached Number - " + EEBuffer.size());
                }else{
                    Log.d(TAG, "onComplete: Cached get failed - ", task.getException());
                }
            }
        });

        AppHelper.getFirestore().collection("log-acknowledged").whereGreaterThanOrEqualTo("timestamp_entry", new Timestamp(new Date()))
                .get(source).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot querySnapshot = task.getResult();
                    logAck = querySnapshot.getDocuments();
                    Log.d(TAG, "onComplete: EEB Cached Number - " + logAck.size());
                }else{
                    Log.d(TAG, "onComplete: Cached get failed - ", task.getException());
                }
            }
        });

        AppHelper.getFirestore().collection("log-unacknowledged").whereGreaterThanOrEqualTo("timestamp_entry", new Timestamp(new Date()))
                .get(source).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot querySnapshot = task.getResult();
                    logUnack = querySnapshot.getDocuments();
                    Log.d(TAG, "onComplete: EEB Cached Number - " + logUnack.size());
                }else{
                    Log.d(TAG, "onComplete: Cached get failed - ", task.getException());
                }
            }
        });
    }

}