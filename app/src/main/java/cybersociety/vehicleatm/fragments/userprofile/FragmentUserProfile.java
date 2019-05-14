package cybersociety.vehicleatm.fragments.userprofile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.ArrayList;

import android.view.ViewGroup;
import cybersociety.vehicleatm.GuestRegistration;
import cybersociety.vehicleatm.R;
import cybersociety.vehicleatm.fragments.viewvehicle.FragmentViewVehicle;

public class FragmentUserProfile extends Fragment {
    private static final String TAG = FragmentUserProfile.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private UserProfileAdapter mAdapter;
    private ArrayList<UserProfileModel> modelList = new ArrayList<>();

    public FragmentUserProfile() {
        // Required empty public constructor
    }

    public static FragmentUserProfile newInstance() {
        return new FragmentUserProfile();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_user_profile, container, false);

        // ButterKnife.bind(this);
        findViews(view);
        return view;

    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setAdapter();
    }


    private void findViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setAdapter() {

        modelList.add(new UserProfileModel("Guest Registration", "Register your expected guests"));
        modelList.add(new UserProfileModel("Registered Vehicles", "view vehicles registered under you"));
        modelList.add(new UserProfileModel("Contact POC", "Contact any POC"));
        modelList.add(new UserProfileModel("Contact Guard", "Contact a guard"));
        modelList.add(new UserProfileModel("Contact Resident", "Contact any fellow Resident"));

        mAdapter = new UserProfileAdapter(getActivity(), modelList);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new UserProfileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, UserProfileModel model) {
                Log.d(TAG, "onItemClick: Clicked" + model.getTitle());
                Fragment fragment = null;
                switch (position){
                    case 0 :
                        startActivity(new Intent(getActivity(), GuestRegistration.class));
                        break;

                    case 1 :
                        fragment = FragmentViewVehicle.newInstance();
                        break;
                }
                if (fragment != null) {
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }

            }
        });
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
