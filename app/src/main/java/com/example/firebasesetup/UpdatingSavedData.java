package com.example.firebasesetup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class UpdatingSavedData extends AppCompatActivity {
    Button submitUpdated;
    EditText updateName, updatePhone, updateEmail, updatePassword, updateCountryCode;
    TextView updateStatus, updateNameConfig, updatePhoneConfig, updateEmailConfig, updatePasswordConfig, textView;
    ImageView iconBackToMainScreen, updateProPic;
    private static final  int IMAGE_REQUEST = 2;

    private Uri imageUri;
    ValueEventListener valueEventListener;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updating_saved_data);

        updateName = findViewById(R.id.updatingRealtimeDatabaseUserFullName);
        updatePhone = findViewById(R.id.updatingRealtimeDatabaseUserPhoneNumber);
        updateEmail = findViewById(R.id.updatingRealtimeDatabaseUserEmail);
        updatePassword = findViewById(R.id.updatingRealtimeDatabaseUserPassword);
        updateCountryCode = findViewById(R.id.updatingRealtimeUserPhoneNumberCountryCode);
        updateStatus = findViewById(R.id.updatingDataMessageStatus);
        updateNameConfig = findViewById(R.id.updatingsavingNameConfigMessage);
        updatePhoneConfig = findViewById(R.id.updatingPhoneConfigMessage);
        updateEmailConfig = findViewById(R.id.updatingEmailConfigMessage);
        updatePasswordConfig = findViewById(R.id.updatingPasswordConfigMessage);
        iconBackToMainScreen = findViewById(R.id.updatingSavedDataBackIcon);
        submitUpdated = findViewById(R.id.SubmitButtonTestingDatabase);
       updateProPic = findViewById(R.id.updateProfilePic);

        textView = findViewById(R.id.upText);


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("SangeetVadakApp").child("UserInfo").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ModelClassData modelClassDataUpdate = snapshot.getValue(ModelClassData.class);

                    String updateClassNameText = modelClassDataUpdate.getName();
                    String updateClassCountryCode = modelClassDataUpdate.getCountrycode();
                    String updateClassPhone = modelClassDataUpdate.getPhone();
                    String updateClassEmail = modelClassDataUpdate.getEmail();


                    updateName.setText(updateClassNameText);
                    updateCountryCode.setText(updateClassCountryCode);
                    updatePhone.setText(updateClassPhone);
                    updateEmail.setText(updateClassEmail);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        iconBackToMainScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        submitUpdated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String updatefullname = updateName.getText().toString();
                String updatephone = updatePhone.getText().toString();
                String updateemail = updateEmail.getText().toString();
                String updatepassword = updatePassword.getText().toString();
                String updatecountrycode = updateCountryCode.getText().toString();

//                Toast.makeText(UpdatingSavedData.this, ""+key, Toast.LENGTH_SHORT).show();
                //WITHOUT ON CLICK SAVING DATA
//                FirebaseDatabase.getInstance().getReference().child("Sangeet Vadak").child("User").child("name").setValue("valen");

                //IF EVERYFIELD IS EMPTY
                if (updatefullname.isEmpty() && updatephone.isEmpty() && updateemail.isEmpty() && updatepassword.isEmpty()) {
                    updateStatus.setText("There is no data to be updated");
                } else if (updatefullname.isEmpty()) {
                    updateNameConfig.setText("Your Name Please!");
                    updateStatus.setText(" ");
                } else {
                    updateStatus.setText(" ");
                    if (!(updatefullname.isEmpty())) {
                        updateNameConfig.setText("Name Must Contain 3 Letters");
                        if (updatefullname.length() >= 3) {
                            updateNameConfig.setText(" ");
                            updatePhoneConfig.setText("Enter Phone Number");
                            if (!(updatephone.isEmpty())) {
                                if (updatephone.length() < 10) {
                                    updatePhoneConfig.setText("Number Must Be 10 Digits");
                                } else {
                                    updatePhoneConfig.setText(" ");
                                    updateEmailConfig.setText("Enter Email Address");
                                }
                                if (!(updateemail.isEmpty())) {
                                    if (updateemail.length() < 7) {
                                        updateEmailConfig.setText("www.humanbeing@example.com");
                                    } else if (!(updateemail.endsWith(".com"))) {
                                        updateEmailConfig.setText("email must ends with @example.com/org ");
                                    } else {
                                        updateEmailConfig.setText(" ");
                                        updatePasswordConfig.setText("Enter Password");
                                        if (!(updatepassword.isEmpty())) {
                                            if (updatepassword.contains(" ")) {
                                                updatePasswordConfig.setText("Spaces are not allowed");
                                            } else if ((updatepassword.length() < 6) || (updatepassword.length() >= 15)) {
                                                updatePasswordConfig.setText("Minimum of 6 characters is required, Maximum 15");

                                            } else if (!(updatepassword.contains("@") || updatepassword.contains("#") ||
                                                    updatepassword.contains("!") || updatepassword.contains("~") ||
                                                    updatepassword.contains("$") || updatepassword.contains("%") ||
                                                    updatepassword.contains("^") || updatepassword.contains("&") ||
                                                    updatepassword.contains("*") || updatepassword.contains("(") ||
                                                    updatepassword.contains(")") || updatepassword.contains("-") ||
                                                    updatepassword.contains("+") || updatepassword.contains("/") ||
                                                    updatepassword.contains(":") || updatepassword.contains(".") ||
                                                    updatepassword.contains(", ") || updatepassword.contains("<") ||
                                                    updatepassword.contains(">") || updatepassword.contains("?") ||
                                                    updatepassword.contains("|"))) {
                                                updatePasswordConfig.setText("at least 1 special character is required");
                                            } else {
                                                updatePasswordConfig.setText(" ");
                                                updateStatus.setText(" ");

                                                updateUser(updatefullname, updatecountrycode, updatephone, updateemail, updatepassword);

                                                updateName.setText(null);
                                                updatePhone.getText().clear();
                                                updateEmail.getText().clear();
                                                updatePassword.setText(null);
                                                updateCountryCode.setText(null);

                                                updateStatus.setTextColor(getResources().getColor(R.color.white));
                                                updateStatus.setText("Data Saved Successfully");

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





        updateProPic.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openImage();
        }
    });
    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent ,  IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();
            uploadImage();
        }

    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void uploadImage() {
       final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();
        if(imageUri !=null){
            StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                    .child("Uploads").child(System.currentTimeMillis()+ "." + getFileExtension(imageUri));

            storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();

                            Log.d("DownloaduRL ", url);

                            pd.dismiss();
                            Toast.makeText(UpdatingSavedData.this, "Image Upload SuccessFully", Toast.LENGTH_SHORT).show();


                        }
                    });
                }
            });
        }
    }


    private void updateUser(String updatefullname, String updatecountrycode, String updatephone, String updateemail, String updatepassword) {


        HashMap<String, Object> map = new HashMap<>();
        ModelClassData modelClassData = new ModelClassData();
        new ModelClassData(updatefullname, updatecountrycode, updatephone, updateemail, updatepassword);
        map.put("name", updatefullname);
        map.put("countrycode", updatecountrycode);
        map.put("phone", updatephone);
        map.put("email", updateemail);
        map.put("password", updatepassword);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        databaseReference.child("SangeetVadak App").child("User").updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
        assert user != null;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("SangeetVadakApp").child("UserInfo").child(user.getUid());
        databaseReference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                user.updateEmail(updateEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            firebaseAuth.signOut();
                            startActivity(new Intent(UpdatingSavedData.this, LoginActivity.class));
                            finish();
                        }
                    }
                });

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        finish();
                    }
                }, 1500);

            }
        });


    }


//    @Override
//    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//        if (snapshot.getValue(String.class) != null) {
//            String key = snapshot.getKey();
//            if (key.equals("name")) {
//                String username = snapshot.getValue(String.class);
//                updateName.setText(username);
//            }
//            if (key.equals("phone")) {
//                String phonenumber = snapshot.getValue(String.class);
//                updatePhone.setText(phonenumber);
//            }
//            if (key.equals("email")) {
//                String emailId = snapshot.getValue(String.class);
//                updateEmail.setText(emailId);
//            }
//            if (key.equals("password")) {
//                String userPassword = snapshot.getValue(String.class);
//                updatePassword.setText(userPassword);
//            }
//        }
//    }
//
//    @Override
//    public void onCancelled(@NonNull DatabaseError error) {
//
//        }
//    protected void onStart () {
//        super.onStart();
//        databaseReference.addValueEventListener(UpdatingSavedData.this);
//    }
//}
}




