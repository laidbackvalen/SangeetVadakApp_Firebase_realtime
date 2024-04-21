package com.example.firebasesetup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RetrievingDataInUserProfile extends AppCompatActivity {
    ImageView backIconn;
    TextView retrieveName, retrieveCountryCode, retrievePhone, retrieveEmail, removingData, status;
    Button updateYourProfile;
    Button buttonlogout;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        backIconn = findViewById(R.id.profileBackIcon);
        retrieveName = findViewById(R.id.profileUserFullName);
        retrieveCountryCode = findViewById(R.id.profileUserCountryCode);
        retrievePhone = findViewById(R.id.profileUserPhoneNumber);
        retrieveEmail = findViewById(R.id.profileUserEmail);
        updateYourProfile = findViewById(R.id.updateYourProfileButton);
        removingData = findViewById(R.id.removingDataUpdateScreen);
        status = findViewById(R.id.removingDataMessageStatus);
        buttonlogout = findViewById(R.id.buttonLogOut);


        buttonlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();
                startActivity(new Intent(RetrievingDataInUserProfile.this, LoginActivity.class));
                finish();

            }
        });


        updateYourProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RetrievingDataInUserProfile.this, UpdatingSavedData.class));
            }
        });

        backIconn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//RETRIVING DATA METHOD Call
        retriveUser(retrieveName, retrievePhone, retrieveCountryCode, retrieveEmail);

//DELETING DATA
        removingData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                status.setText("Data Removed Successfully");
                retrieveName.setText(null);
                retrieveCountryCode.setText(null);
                retrievePhone.setText(null);
                retrieveEmail.setText(null);


                deleteUserData();

            }
        });

    }

    private void retriveUser(TextView retrieveName, TextView retrievePhone, TextView retrieveCountryCode, TextView retrieveEmail) {


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("SangeetVadakApp").child("UserInfo").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
//                    for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    ModelClassData modelClassData = snapshot.getValue(ModelClassData.class);

//                    String a = snapshot.child("name").getValue().toString();    //BEST wAY tO dO iT
//                    Toast.makeText(RetrievingDataInUserProfile.this, ""+a, Toast.LENGTH_SHORT).show();

                    String name = modelClassData.getName();
                    String countrycode = modelClassData.getCountrycode();
                    String phone = modelClassData.getPhone();
                    String email = modelClassData.getEmail();

                    retrieveName.setText(name);
                    retrieveCountryCode.setText(countrycode);
                    retrievePhone.setText(phone);
                    retrieveEmail.setText(email);
                }
            }
//            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void deleteUserData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("SangeetVadakApp").child("UserInfo").child(user.getUid());

        //This will remove data with email only
//        databaseReference.child("User").child("email").setValue(null);
//        databaseReference.child("User").child("email").removeValue();

        databaseReference.removeValue();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                firebaseAuth.signOut();
                finish();
            }
        }, 2000);

    }

}