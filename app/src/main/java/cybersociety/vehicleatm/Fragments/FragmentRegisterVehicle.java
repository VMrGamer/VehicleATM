package cybersociety.vehicleatm.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import cybersociety.vehicleatm.AppHelper;
import cybersociety.vehicleatm.R;

import static android.support.constraint.Constraints.TAG;

public class FragmentRegisterVehicle extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_vehicle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        Objects.requireNonNull(getActivity()).setTitle("VEHICLE REGISTRATION");
        final EditText veh_no;
        Button b1;
        //initialize
        veh_no = view.findViewById(R.id.editText);
        b1 = view.findViewById(R.id.button2);
        // Store values at the time of the registration attempt.

        //registering the vehicle
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = AppHelper.getFirebaseCurrentUser();
                if(user!=null){
                    Toast.makeText(getContext(), "PROCEEDING REG...", Toast.LENGTH_LONG).show();
                    Map<String, Object> doc_vehicle = new HashMap<>();
                    Date currentTime = Calendar.getInstance().getTime();
                    doc_vehicle.put("vehicle no", veh_no.getText().toString());
                    doc_vehicle.put("timestamp", currentTime);
                    doc_vehicle.put("owner", user.getUid());
                    doc_vehicle.put("rid", "null");
                    //writing data
                    AppHelper.getFirestore().collection("registration")
                            .document(user.getUid()+currentTime)
                            .set(doc_vehicle)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                    Toast.makeText(getContext(), "WRITE SUCCESSFUL..", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error writing document", e);
                                    Toast.makeText(getContext(), "WRITE UNSUCCESSFUL..", Toast.LENGTH_LONG).show();
                                }
                            });
                }
                else
                {
                    Toast.makeText(getContext(), "NULL USER", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}














