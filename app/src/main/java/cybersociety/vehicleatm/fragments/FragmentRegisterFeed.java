package cybersociety.vehicleatm.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

import cybersociety.vehicleatm.AppHelper;
import cybersociety.vehicleatm.R;
import cybersociety.vehicleatm.fragments.feed24hr.FragmentFeed24hr;

public class FragmentRegisterFeed extends Fragment {
    private static final String TAG = "FragmentRegisterFeed";

    private String vehicle_no;
    private String id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_feed,null);
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
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView)view.findViewById(R.id.vehicle_no)).setText(vehicle_no);
        ((TextView)view.findViewById(R.id.doc_id)).setText(id);
        view.findViewById(R.id.reg_feed_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){;
                    Map<String, Object> doc_reg = new HashMap<>();
                    doc_reg.put("rid", id);
                    doc_reg.put("uid", AppHelper.getFirebaseCurrentUser().getUid());
                    doc_reg.put("name",((EditText) view.findViewById(R.id.reg_feed_name)).getText().toString());
                    doc_reg.put("vehicle_no", vehicle_no);
                    doc_reg.put("flat_no", ((EditText) view.findViewById(R.id.reg_feed_flat_no)).getText().toString());
                    doc_reg.put("timestamp", new Timestamp(new GregorianCalendar().getTime()));
                    AppHelper.getFirestore().collection("registration").document(id).set(doc_reg).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                            ArrayList<String> collectionPaths = new ArrayList<>();
                            //collectionPaths.add("entry-exit-buffer");
                            //collectionPaths.add("log-acknowledged");
                            //collectionPaths.add("log-unacknowledged");
                            collectionPaths.add("log-vehicle");
                            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, FragmentFeed24hr.newInstance(collectionPaths,"null", "null"))
                                    .commit();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document: ", e);
                        }
                    });
                }
                else{
                    Toast.makeText(getActivity(), "Please check your input", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.findViewById(R.id.vehicle_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private boolean validate(){
        return true;
    }
}