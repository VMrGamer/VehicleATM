package cybersociety.vehicleatm.fragments.userprofile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.ArrayList;

import android.widget.Toast;
import android.view.ViewGroup;
import cybersociety.vehicleatm.GuestRegistration;
import cybersociety.vehicleatm.R;

public class FragmentUserProfile extends Fragment {
    private static final String TAG = FragmentUserProfile.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;

    // @BindView(R.id.recycler_view)
    // RecyclerView recyclerView;


    private UserProfileAdapter mAdapter;

    private ArrayList<UserProfileModel> modelList = new ArrayList<>();


    public FragmentUserProfile() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentUserProfile newInstance(String param1, String param2) {
        FragmentUserProfile fragment = new FragmentUserProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static FragmentUserProfile newInstance() {
        return new FragmentUserProfile();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
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

        modelList.add(new UserProfileModel("GUEST REGISTRATION", "Register your expected guests"));
        //modelList.add(new UserProfileModel("STRANGER VEHICLE REG.", "Register any unexpected guests"));
        modelList.add(new UserProfileModel("REGISTERED VEHICLES", "view vehicles registered under you"));
        modelList.add(new UserProfileModel("CONTACT SPOC", "Contact any SPOC"));
        modelList.add(new UserProfileModel("CONTACT GUARD", "Contact a guard"));
        modelList.add(new UserProfileModel("CONTACT RESIDENT", "Contact any fellow Resident"));


        mAdapter = new UserProfileAdapter(getActivity(), modelList);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new UserProfileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, UserProfileModel model) {

                //handle item click events here
                switch (position){
                    case 0 :
                        startActivity(new Intent(getActivity(), GuestRegistration.class));
                        break;

                    case 1 :/*
                        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_view_vehicles, )
                            .commit();*/
                        Toast.makeText(getContext(), "GO THROUGH NAV_BAR", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(getActivity(), ViewVehiclesActivity.class));
                        break;
                }
                //Toast.makeText(getActivity(), " "+model.getTitle(), Toast.LENGTH_SHORT).show();


            }
        });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
