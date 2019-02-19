package cybersociety.vehicleatm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentHistory extends Fragment{

    private String title;
    private int page;
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
    }

}