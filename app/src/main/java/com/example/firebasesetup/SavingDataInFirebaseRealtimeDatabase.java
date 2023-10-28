package com.example.firebasesetup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SavingDataInFirebaseRealtimeDatabase extends AppCompatActivity {
    Button submit;
    EditText rdfullname, rdphone, rdemail, rdpassword, rdcountrycode;
    TextView savingStatus, savingnameconfig, savingphoneconfig, savingemailconfig, savingpasswordconfig;
    ImageView goBack;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_data_in_firebase_realtime_database);

        rdfullname = findViewById(R.id.savingRealtimeDatabaseUserFullName);
        rdphone = findViewById(R.id.savingRealtimeDatabaseUserPhoneNumber);
        rdemail = findViewById(R.id.savingRealtimeDatabaseUserEmail);
        rdpassword = findViewById(R.id.savingRealtimeDatabaseUserPassword);
        submit = findViewById(R.id.SubmitButtonTestingDatabase);
        savingStatus = findViewById(R.id.savingDataMessageStatus);
        savingnameconfig = findViewById(R.id.savingNameConfigMessage);
        savingphoneconfig = findViewById(R.id.savingPhoneConfigMessage);
        savingemailconfig = findViewById(R.id.savingEmailConfigMessage);
        savingpasswordconfig = findViewById(R.id.savingPasswordConfigMessage);
        rdcountrycode = findViewById(R.id.savingRealtimeUserPhoneNumberCountryCode);
        goBack = findViewById(R.id.savingDataBackIcon);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //SUBMIT BUTTON ON CLICK
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String fullname = rdfullname.getText().toString();
                String phone = rdphone.getText().toString();
                String email = rdemail.getText().toString();
                String password = rdpassword.getText().toString();
                String countrycode = rdcountrycode.getText().toString();

//                //WITHOUT ON CLICK SAVING DATA
//                FirebaseDatabase.getInstance().getReference().child("Sangeet Vadak").child("User").child("name").setValue("valen");

                //IF EVERYFIELD IS EMPTY
                if (fullname.isEmpty() && phone.isEmpty() && email.isEmpty() && password.isEmpty()) {
                    savingStatus.setText("There is no data to be saved");
                } else if (fullname.isEmpty()) {
                    savingnameconfig.setText("Your Name Please!");
                    savingStatus.setText(" ");
                } else {
                    savingStatus.setText(" ");

                    if (!(fullname.isEmpty())) {
                        savingnameconfig.setText("Name Must Contain 3 Letters");
                        if (fullname.length() >= 3) {
                            savingnameconfig.setText(" ");
                            savingphoneconfig.setText("Enter Phone Number");
                            if (!(phone.isEmpty())) {
                                if (phone.length() < 10) {
                                    savingphoneconfig.setText("Number Must Be 10 Digits");
                                } else {
                                    savingphoneconfig.setText(" ");
                                    savingemailconfig.setText("Enter Email Address");
                                }
                                if (!(email.isEmpty())) {
                                    if (email.length() < 7) {
                                        savingemailconfig.setText("www.humanbeing@example.com");
                                    } else if (!(email.endsWith(".com"))) {
                                        savingemailconfig.setText("email must ends with @example.com/org");
                                    } else {
                                        savingemailconfig.setText(" ");
                                        savingpasswordconfig.setText("Enter Password");
                                        if (!(password.isEmpty())) {
                                            if (password.contains(" ")) {
                                                savingpasswordconfig.setText("Spaces are not allowed");
                                            } else if ((password.length() < 6) || (password.length() >= 15)) {
                                                savingpasswordconfig.setText("Minimum of 6 characters is required, Maximum 15");

                                            } else if (!(password.contains("@") || password.contains("#")
                                                    || password.contains("!") || password.contains("~")
                                                    || password.contains("$") || password.contains("%")
                                                    || password.contains("^") || password.contains("&")
                                                    || password.contains("*") || password.contains("(")
                                                    || password.contains(")") || password.contains("-")
                                                    || password.contains("+") || password.contains("/")
                                                    || password.contains(":") || password.contains(".")
                                                    || password.contains(", ") || password.contains("<")
                                                    || password.contains(">") || password.contains("?")
                                                    || password.contains("|"))) {
                                                savingpasswordconfig.setText("at least 1 special character is required");
                                            } else {
                                                savingpasswordconfig.setText(" ");
                                                savingStatus.setText(" ");

                                                saveData(fullname, countrycode, phone, email, password);

                                                rdfullname.setText(null);
                                                rdphone.getText().clear();
                                                rdemail.getText().clear();
                                                rdpassword.setText(null);
                                                rdcountrycode.setText(null);

                                                savingStatus.setTextColor(getResources().getColor(R.color.white));
                                                savingStatus.setText("Data Saved Successfully");
                                            }
                                        }
                                    }

                                }
                            }
                        }

                    }
                }
            }
        });

    }

    //    private void setUser(String fullname, String countrycode, String phone, String email, String password) {
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//
//
//        HashMap<String, String> map = new HashMap<>();
//        map.put("name", fullname);
//        map.put("phone", countrycode + phone);
//        map.put("email", email);
//        map.put("password", password);
//
//
//        databaseReference.child("SangeetVadakApp").child("Users").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @SuppressLint("ResourceAsColor")
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        finish();
//                    }
//                }, 1500);
//
//            }
//        });
//
//    }
    // SET USER USING MODEL CLASS //YOU CAN CREATE USER WITH MODEL CLASS //YOU CANNOT UPDATE IT

    private void saveData(String updatefullname, String updatecountrycode, String updatephone, String updateemail, String updatepassword) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        ModelClassData modelClassData = new ModelClassData(updatefullname, updatecountrycode, updatephone, updateemail, updatepassword);

        assert user != null;
        databaseReference.child("SangeetVadakApp").child("UserInfo").child(user.getUid()).setValue(modelClassData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

//              String key = databaseReference.push().getKey().toString();   //to know push key
//                Toast.makeText(SavingDataInFirebaseRealtimeDatabase.this, "" + key, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SavingDataInFirebaseRealtimeDatabase.this, MainActivity.class));

            }

//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    finish();
//                }
//            }, 1500);
//
//        }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SavingDataInFirebaseRealtimeDatabase.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
