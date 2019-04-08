package cybersociety.vehicleatm.fragments;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;

import cybersociety.vehicleatm.AppHelper;
import cybersociety.vehicleatm.R;

import static android.support.constraint.Constraints.TAG;

public class FragmentRegisterVehicle extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_vehicle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Vehicle Registration");
        final EditText veh_no;
        Button b1;
        veh_no = view.findViewById(R.id.editText);
        b1 = view.findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = AppHelper.getFirebaseCurrentUser();
                if(user!=null){
                    Toast.makeText(getContext(), "Registering...", Toast.LENGTH_LONG).show();
                    AppHelper.getFirestore().collection("users").document(AppHelper.getFirebaseCurrentUser().getUid())
                            .update("vehicles", FieldValue.arrayUnion(veh_no.getText().toString()))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                    Toast.makeText(getContext(), "Registered", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                            Toast.makeText(getContext(), "Error!!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(getContext(), "No User Found", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}














