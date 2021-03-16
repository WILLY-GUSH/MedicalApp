package com.example.medicalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientProfileDetails extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile_details);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView userName4TextView = (TextView) findViewById(R.id.userName4);
        final TextView emailTextView = (TextView) findViewById(R.id.email_textView);
        final TextView phoneTextView = (TextView) findViewById(R.id.phone_textView);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User3 userProfile = snapshot.getValue(User3.class);

                if(userProfile != null){
                    String userName4 = userProfile.userName3;
                    String email_textView = userProfile.email3;

                    userName4TextView.setText(userName4);
                    emailTextView.setText(email_textView);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PatientProfileDetails.this, "Something wrong happened!", Toast.LENGTH_LONG).show();

            }
        });
    }


}