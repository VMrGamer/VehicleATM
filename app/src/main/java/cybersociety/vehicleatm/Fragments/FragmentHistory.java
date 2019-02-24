package cybersociety.vehicleatm.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cybersociety.vehicleatm.R;
import cybersociety.vehicleatm.RecyclerViewFragment;

public class FragmentHistory extends Fragment{
    private static final String TAG = "FragmentHistory";

    private String title;
    private int page;
    private RecyclerViewFragment recyclerViewFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_history,null);
        FragmentTransaction fragmentTransaction =  getFragmentManager().beginTransaction();
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