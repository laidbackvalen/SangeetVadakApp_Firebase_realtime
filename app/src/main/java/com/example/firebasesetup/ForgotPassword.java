package com.example.firebasesetup;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    EditText emailInput;
    Button resetpassword;
    TextView returnToLogin, txtEmailStatusMessage, statusMessage;
    ImageView backIconForgotPassword;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emailInput = findViewById(R.id.editTextEmailInput);
        resetpassword = findViewById(R.id.buttonResetYourPassword);
        returnToLogin = findViewById(R.id.textReturnToLoginPage);
        txtEmailStatusMessage = findViewById(R.id.emailInputStatus);
        statusMessage = findViewById(R.id.statusMessages);
        backIconForgotPassword = findViewById(R.id.backIconimageForgotPassword);
        firebaseAuth = FirebaseAuth.getInstance();


        backIconForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        returnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //FORGET PASSWORD
        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputEmail = emailInput.getText().toString().trim();
               // sendVerification(inputEmail);



                if (TextUtils.isEmpty(inputEmail)) {
                    txtEmailStatusMessage.setText("Enter your registered Email Address");
                    return;
                } else if (inputEmail.length() < 7) {
                    txtEmailStatusMessage.setText("www.humanbeing@example.com");
                } else if (!(inputEmail.endsWith(".com"))) {
                    txtEmailStatusMessage.setText("email must ends with .com/org");
                } else {
                   sendVerification(inputEmail);
                    txtEmailStatusMessage.setText(" ");
                }


            }

        });

    }
        private void sendVerification(String inputEmail) {

        final ProgressDialog progressDialog = new ProgressDialog(ForgotPassword.this);
        progressDialog.setMessage("verifying..");
        progressDialog.show();


        firebaseAuth.getInstance().sendPasswordResetEmail(inputEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Log.d(TAG, "Email sent.");
                    progressDialog.dismiss();
                    statusMessage.setText("We have sent a link on your Email Address to Reset Your Password");


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
                            finish();

                        }
                    }, 3000);
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                statusMessage.setText(e.getMessage());
            }
        });


            //    private void sendVerification(String inputEmail) {
//
//        FirebaseAuth.getInstance().fetchSignInMethodsForEmail("cool.chitransh83@gmail.com")
//                .addOnCompleteListener(task -> {
//                            if (task.isSuccessful()) {
//                                if (!Objects.requireNonNull(task.getResult().getSignInMethods()).isEmpty()) {
//                                    Log.d("TAG", "onCreateTAGIF: " + task.getResult().getSignInMethods().isEmpty());
//                                } else {
//                                    Log.d("TAG", "onCreateTAGELSE: " + task.getResult().getSignInMethods().isEmpty());
//                                }
//                            }
//                        });



//        firebaseAuth.fetchSignInMethodsForEmail(inputEmail).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
//            @Override
//            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
//                if (task.isSuccessful()) {
//                    if (Objects.requireNonNull(task.getResult().getSignInMethods()).isEmpty()) {
//                        Log.d(TAG, "onComplete: " + task.getResult().getSignInMethods().isEmpty());
//                    }else {
//                        Log.d(TAG, "onComplete: else " + task.getResult().getSignInMethods().isEmpty());
//
//                    }
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
    }
}

