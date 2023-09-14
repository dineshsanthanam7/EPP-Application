package com.example.epp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = "dk";
    TextView t1, t2, t3;
    String name, reg, sem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent i = getIntent();
        //Getting the Values from First Activity using the Intent received
        name = i.getStringExtra("name_key");
        reg = i.getStringExtra("reg_key");
        sem = i.getStringExtra("sem_key");

        StringBuilder st = new StringBuilder(reg);
        st.append(" ");
        st.append(name);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

// Get the collection name and document name entered by the user
        String collectionName = sem; // Replace with user input
        String documentName = st.toString();
        DocumentReference documentRef = db.collection(collectionName).document(documentName);

// Retrieve the data from the document
        documentRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    // Document exists, retrieve the data
                    Map<String, Object> data = documentSnapshot.getData();
                    // Convert data to string with line breaks
                    StringBuilder dataStrBuilder = new StringBuilder();
                    for (Map.Entry<String, Object> entry : data.entrySet()) {
                        String key = entry.getKey();
                        Object value = entry.getValue();
                        dataStrBuilder.append(value.toString()).append("\n");
                    }
                    TextView textView = findViewById(R.id.TextView);
                    String dataStr = dataStrBuilder.toString();
                    //textView.setText(dataStr);
                    if(dataStr.contains("Pass")) {
                        String[] s1 = dataStr.split("(?<=Pass)");
                        StringBuilder result = new StringBuilder();

                        for (String z : s1) {
                            result.append(z).append("\n");
                        }
                        StringBuilder result1 = new StringBuilder();
                        String s2=result.toString();
                        String[] s3= s2.split("(?<=Reappear)");
                        for (String z1 : s3) {
                            result1.append(z1).append("\n");
                        }
                        StringBuilder result2 = new StringBuilder();
                        String s4=result1.toString();
                        String[] s5= s4.split("(?<=Absent)");
                        for (String z3 : s5) {
                            result2.append(z3).append("\n");
                        }

                        textView.setText(result2);
                    }
                   else{
                        String[]s2=dataStr.split("(?<=PASS)");
                        StringBuilder result2=new StringBuilder();
                        for(String y:s2){
                            result2.append(y).append("\n");
                        }
                        StringBuilder result1 = new StringBuilder();
                        String s9=result2.toString();
                        String[] s3= s9.split("(?<=RA)");
                        for (String z1 : s3) {
                            result1.append(z1).append("\n");
                        }
                        textView.setText(result1);
                    }





                }
                // Display data in a TextView
                else {
                    // Document does not exist

                    Toast.makeText(getBaseContext()," does not exist", Toast.LENGTH_LONG).show();

                    //Log.d(TAG, "Document does not exist");

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle any errors
                Toast.makeText(getBaseContext()," does not exist", Toast.LENGTH_LONG).show();
               // Log.w(TAG, "Error getting document", e);
                // Display error message in a TextView

            }
        });
    }
}

