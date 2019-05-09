package cybersociety.vehicleatm;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class GuestReg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_reg);

        final EditText name, contact_no, desc;
        name = findViewById(R.id.editText);
        contact_no = findViewById(R.id.editText4);
        desc = findViewById(R.id.editText3);
        Button b1 = findViewById(R.id.button2);
        final MediaPlayer mp;
        mp = MediaPlayer.create(this, R.raw.pika_pika_pika);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(GuestReg.this)
                    .setTitle("ADD GUEST ?")
                    .setMessage("Add guest with entered  details ?")
                    .setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                        int which) {
                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Log.d("TAG", "proceeding reg..");
                    Toast.makeText(getApplicationContext(), "PROCEEDING REG...", Toast.LENGTH_LONG).show();
                    Map<String, Object> doc_vehicle = new HashMap<>();
                    doc_vehicle.put("guest name", name.getText().toString());
                    doc_vehicle.put("contact no", contact_no.getText().toString());
                    doc_vehicle.put("occasion", desc.getText().toString());
                    doc_vehicle.put("to", user.getUid());

                   //Toast.makeText(getApplicationContext(), "DOC STORED...", Toast.LENGTH_LONG).show();
                   FirebaseFirestore.getInstance().collection("guest").add(doc_vehicle).

                           addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                            Toast.makeText(getApplicationContext(), "WRITE SUCCESSFUL..", Toast.LENGTH_LONG).show();
                            mp.start();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                            Toast.makeText(getApplicationContext(), "WRITE UNSUCCESSFUL..", Toast.LENGTH_LONG).show();
                        }
                    });

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "NULL USER", Toast.LENGTH_LONG).show();
                }
                                }
                            })
                    .setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                }
                            }).show();
            }
        });
    }
}
