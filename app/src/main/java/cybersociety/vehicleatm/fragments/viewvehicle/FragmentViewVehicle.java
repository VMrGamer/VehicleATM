package cybersociety.vehicleatm.fragments.viewvehicle;

import android.content.Context;
import android.net.Uri;

import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;


import android.widget.Toast;


import android.view.ViewGroup;

import cybersociety.vehicleatm.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentViewVehicle.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentViewVehicle#newInstance} factory method to
 * create an instance of this fragment.
 */


public class FragmentViewVehicle extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;

    // @BindView(R.id.recycler_view)
    // RecyclerView recyclerView;


    private ViewVehicleAdapter mAdapter;

    private ArrayList<ViewVehicleModel> modelList = new ArrayList<>();


    public FragmentViewVehicle() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentViewVehicle.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentViewVehicle newInstance(String param1, String param2) {
        FragmentViewVehicle fragment = new FragmentViewVehicle();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static FragmentViewVehicle newInstance() {
        FragmentViewVehicle fragment = new FragmentViewVehicle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_vehicle, container, false);

        // ButterKnife.bind(this);
        findViews(view);

        return view;

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setAdapter();


    }


    private void findViews(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
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


        modelList.add(new ViewVehicleModel("Android", "Hello " + " Android"));
        modelList.add(new ViewVehicleModel("Beta", "Hello " + " Beta"));
        modelList.add(new ViewVehicleModel("Cupcake", "Hello " + " Cupcake"));
        modelList.add(new ViewVehicleModel("Donut", "Hello " + " Donut"));
        modelList.add(new ViewVehicleModel("Eclair", "Hello " + " Eclair"));
        modelList.add(new ViewVehicleModel("Froyo", "Hello " + " Froyo"));
        modelList.add(new ViewVehicleModel("Gingerbread", "Hello " + " Gingerbread"));
        modelList.add(new ViewVehicleModel("Honeycomb", "Hello " + " Honeycomb"));
        modelList.add(new ViewVehicleModel("Ice Cream Sandwich", "Hello " + " Ice Cream Sandwich"));
        modelList.add(new ViewVehicleModel("Jelly Bean", "Hello " + " Jelly Bean"));
        modelList.add(new ViewVehicleModel("KitKat", "Hello " + " KitKat"));
        modelList.add(new ViewVehicleModel("Lollipop", "Hello " + " Lollipop"));
        modelList.add(new ViewVehicleModel("Marshmallow", "Hello " + " Marshmallow"));
        modelList.add(new ViewVehicleModel("Nougat", "Hello " + " Nougat"));
        modelList.add(new ViewVehicleModel("Android O", "Hello " + " Android O"));


        mAdapter = new ViewVehicleAdapter(getActivity(), modelList);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new ViewVehicleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ViewVehicleModel model) {

                //handle item click events here
                Toast.makeText(getActivity(), "Hey " + model.getTitle(), Toast.LENGTH_SHORT).show();


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
