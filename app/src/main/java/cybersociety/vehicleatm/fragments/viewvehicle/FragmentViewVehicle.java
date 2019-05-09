package cybersociety.vehicleatm.fragments.viewvehicle;

import android.content.Context;
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

import cybersociety.vehicleatm.AppHelper;
import cybersociety.vehicleatm.R;


public class FragmentViewVehicle extends Fragment {
    private static final String TAG = FragmentViewVehicle.class.getSimpleName();

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;

    // @BindView(R.id.recycler_view)
    // RecyclerView recyclerView;

    private ViewVehicleAdapter mAdapter;

    private ArrayList<ViewVehicleModel> modelList = new ArrayList<>();

    public FragmentViewVehicle() {
    }

    public static FragmentViewVehicle newInstance() {
        return new FragmentViewVehicle();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_vehicle, container, false);

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
        for(String veh_no: AppHelper.getVehicle_no()){
            modelList.add(new ViewVehicleModel(veh_no, "Tap to get Logs"));
        }
        mAdapter = new ViewVehicleAdapter(getActivity(), modelList);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.SetOnItemClickListener(new ViewVehicleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ViewVehicleModel model) {
                Toast.makeText(getActivity(), model.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
