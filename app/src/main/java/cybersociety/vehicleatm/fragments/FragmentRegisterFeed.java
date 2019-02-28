package cybersociety.vehicleatm.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cybersociety.vehicleatm.R;

public class FragmentRegisterFeed extends Fragment {

    private String vehicle_no;
    private String id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register,null);
    }

    public static FragmentRegisterFeed newInstance(String vehicle_no, String id) {
        FragmentRegisterFeed fragmentRegister= new FragmentRegisterFeed();
        Bundle args = new Bundle();
        args.putString("vehicle_no", vehicle_no);
        args.putString("id", id);
        fragmentRegister.setArguments(args);
        return fragmentRegister;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            vehicle_no = getArguments().getString("vehicle_no", "none");
            id = getArguments().getString("id", "none");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView)view.findViewById(R.id.vehicle_no)).setText(vehicle_no);
        ((TextView)view.findViewById(R.id.doc_id)).setText(id);
    }
}