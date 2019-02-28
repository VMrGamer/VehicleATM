package cybersociety.vehicleatm.fragments.feed24hr;

import android.content.Context;
import android.net.Uri;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import android.support.v4.widget.SwipeRefreshLayout;

import android.widget.Toast;
import android.os.Handler;


import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import cybersociety.vehicleatm.AppHelper;
import cybersociety.vehicleatm.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment24HrFeed.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment24HrFeed#newInstance} factory method to
 * create an instance of this fragment.
 */


public class Fragment24HrFeed extends Fragment {
    private static final String TAG = "Fragment24HrFeed";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "listArrayList1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<String> collectionStrings;
    private List<DocumentSnapshot> documentSnapshotList;

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;

    // @BindView(R.id.recycler_view)
    // RecyclerView recyclerView;


    // @BindView(R.id.swipe_refresh_recycler_list)
    // SwipeRefreshLayout swipeRefreshRecyclerList;

    private SwipeRefreshLayout swipeRefreshRecyclerList;
    private RecyclerView24HrFeedAdapter mAdapter;

    private ArrayList<Feed24hrModel> modelList = new ArrayList<>();


    public Fragment24HrFeed() {
        // Required empty public constructor
    }

    public static Fragment24HrFeed newInstance(ArrayList<String> stringList, String param1, String param2) {
        Fragment24HrFeed fragment = new Fragment24HrFeed();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putStringArrayList(ARG_PARAM3, stringList);
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment24HrFeed newInstance() {
        return new Fragment24HrFeed();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Log.d(TAG, "onCreate: ");
        collectionStrings = new ArrayList<>();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            collectionStrings = getArguments().getStringArrayList(ARG_PARAM3);
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_24hr_feed, container, false);

        // ButterKnife.bind(this);
        findViews(view);

        return view;

    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter();
        swipeRefreshRecyclerList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // Do your stuff on refresh
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final Calendar c = new GregorianCalendar();
                        c.set(Calendar.HOUR_OF_DAY, 0);
                        c.set(Calendar.MINUTE, 0);
                        c.set(Calendar.SECOND, 0);

                        final Source source = Source.SERVER;
                        modelList = new ArrayList<>();

                        for(final String collectionPath: collectionStrings){
                            AppHelper.getFirestore().collection(collectionPath).whereGreaterThanOrEqualTo("timestamp", new Timestamp(c.getTime()))
                                    .get(source).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        QuerySnapshot querySnapshot = task.getResult();
                                        Log.d(TAG, "onComplete: Cached get Success - " + collectionPath + " - "+ Objects.requireNonNull(querySnapshot).getDocuments().size());
                                        for(DocumentSnapshot documentSnapshot: querySnapshot.getDocuments()){
                                            Map<String, Object> dataDoc = documentSnapshot.getData();
                                            modelList.add(new Feed24hrModel(documentSnapshot.getId(),
                                                    Objects.requireNonNull(Objects.requireNonNull(dataDoc).get("vehicle_no")).toString(),
                                                    Objects.requireNonNull(dataDoc.get("rid")).toString()));
                                            mAdapter.updateList(modelList);
                                        }
                                    }else{
                                        Log.d(TAG, "onComplete: Cached get failed - ", task.getException());
                                    }
                                }
                            });
                        }

                        if (swipeRefreshRecyclerList.isRefreshing())
                            swipeRefreshRecyclerList.setRefreshing(false);
                    }
                }, 5000);

            }
        });


    }


    private void findViews(View view) {

        recyclerView = view.findViewById(R.id.recycler_view);
        swipeRefreshRecyclerList = view.findViewById(R.id.swipe_refresh_recycler_list);
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

        /*
        modelList.add(new Feed24hrModel("Android", "Hello " + " Android"));
        modelList.add(new Feed24hrModel("Beta", "Hello " + " Beta"));
        modelList.add(new Feed24hrModel("Cupcake", "Hello " + " Cupcake"));
        modelList.add(new Feed24hrModel("Donut", "Hello " + " Donut"));
        modelList.add(new Feed24hrModel("Eclair", "Hello " + " Eclair"));
        modelList.add(new Feed24hrModel("Froyo", "Hello " + " Froyo"));
        modelList.add(new Feed24hrModel("Gingerbread", "Hello " + " Gingerbread"));
        modelList.add(new Feed24hrModel("Honeycomb", "Hello " + " Honeycomb"));
        modelList.add(new Feed24hrModel("Ice Cream Sandwich", "Hello " + " Ice Cream Sandwich"));
        modelList.add(new Feed24hrModel("Jelly Bean", "Hello " + " Jelly Bean"));
        modelList.add(new Feed24hrModel("KitKat", "Hello " + " KitKat"));
        modelList.add(new Feed24hrModel("Lollipop", "Hello " + " Lollipop"));
        modelList.add(new Feed24hrModel("Marshmallow", "Hello " + " Marshmallow"));
        modelList.add(new Feed24hrModel("Nougat", "Hello " + " Nougat"));
        modelList.add(new Feed24hrModel("Android O", "Hello " + " Android O"));
        */

        mAdapter = new RecyclerView24HrFeedAdapter(getActivity(), modelList);
        final Calendar c = new GregorianCalendar();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);


        final Source source = Source.SERVER;

        for(final String collectionPath: collectionStrings){
            AppHelper.getFirestore().collection(collectionPath).whereGreaterThanOrEqualTo("timestamp", new Timestamp(c.getTime()))
                .get(source).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            QuerySnapshot querySnapshot = task.getResult();
                            Log.d(TAG, "onComplete: Cached get Success - " + collectionPath + " - "+ Objects.requireNonNull(querySnapshot).getDocuments().size());
                            for(DocumentSnapshot documentSnapshot: querySnapshot.getDocuments()){
                                Map<String, Object> dataDoc = documentSnapshot.getData();
                                modelList.add(new Feed24hrModel(documentSnapshot.getId(),
                                        Objects.requireNonNull(Objects.requireNonNull(dataDoc).get("vehicle_no")).toString(),
                                        Objects.requireNonNull(dataDoc.get("timestamp")).toString()));
                                mAdapter.updateList(modelList);
                            }
                        }else{
                            Log.d(TAG, "onComplete: Cached get failed - ", task.getException());
                        }
                    }
                });
        }

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.SetOnItemClickListener(new RecyclerView24HrFeedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Feed24hrModel model) {

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
