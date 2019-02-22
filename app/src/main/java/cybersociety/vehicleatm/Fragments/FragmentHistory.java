package cybersociety.vehicleatm.Fragments;

import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import org.w3c.dom.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
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
        ArrayList<String> assHole = new ArrayList<>();
        assHole.add("entry-exit-buffer");
        assHole.add("log-acknowledged");
        assHole.add("log-unacknowledged");
        recyclerViewFragment = RecyclerViewFragment.newInstance(assHole, "null", "null");
        //Bundle args = new Bundle();
        //recyclerViewFragment.setArguments(args);
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
    }

}