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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import cybersociety.vehicleatm.AppHelper;
import cybersociety.vehicleatm.R;
import cybersociety.vehicleatm.fragments.FragmentRegisterFeed;

public class FragmentFeed24hr extends Fragment {
    private static final String TAG = FragmentFeed24hr.class.getSimpleName();

    private static final String ARG_LIST1 = "listArrayList1";

    private ArrayList<String> collectionStrings;

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;

    private SwipeRefreshLayout swipeRefreshRecyclerList;
    private Feed24hrAdapter mAdapter;

    private ArrayList<Feed24hrModel> modelList = new ArrayList<>();


    public FragmentFeed24hr() {
        // Required empty public constructor
    }

    public static FragmentFeed24hr newInstance(ArrayList<String> stringList) {
        FragmentFeed24hr fragment = new FragmentFeed24hr();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_LIST1, stringList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Log.d(TAG, "onCreate: ");
        collectionStrings = new ArrayList<>();
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            collectionStrings = getArguments().getStringArrayList(ARG_LIST1);
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
    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
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
                            AppHelper.getFirestore().collection(collectionPath)//.whereGreaterThanOrEqualTo("timestamp", new Timestamp(c.getTime()))
                                    .orderBy("timestamp", Query.Direction.DESCENDING)
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
                                                    ((Timestamp)Objects.requireNonNull(dataDoc.get("timestamp"))).toDate().toString(),
                                                    Objects.requireNonNull(dataDoc.get("snap_link")).toString()));
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
                                        ((Timestamp)Objects.requireNonNull(dataDoc.get("timestamp"))).toDate().toString(),
                                        Objects.requireNonNull(dataDoc.get("snap_link")).toString()));
                            }
                        }else{
                            Log.d(TAG, "onComplete: Cached get failed - ", task.getException());
                        }
                    }
                });
        }
        mAdapter = new Feed24hrAdapter(getActivity(), modelList);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new Feed24hrAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Feed24hrModel model) {
                Toast.makeText(getActivity(), "Hey " + model.getTitle(), Toast.LENGTH_SHORT).show();
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, FragmentRegisterFeed.newInstance(model.getTitle(), model.getDocID()))
                        .commit();
            }
        });
        
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
