package com.example.firebasesetup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if ( firebaseAuth.getCurrentUser()!= null) {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                    finish();

                }

            }
        }, 4000);
    }

}