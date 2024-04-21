package com.example.firebasesetup;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    ImageView menuBurgerIcon, musisplayIcon;
    TextView nameMainAct;
    View view;
    ViewPager2 viewPagerMain;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        menuBurgerIcon = findViewById(R.id.burgerIconMenuNavDrawer);
        nameMainAct = findViewById(R.id.nameMainActivity);
        view = findViewById(R.id.view3);
        musisplayIcon = findViewById(R.id.musicPlay);
//        recreate();

        //VIEW PAGER SETTINGS Starts HERE
        viewPagerMain = findViewById(R.id.viewPager);
        //Here, i'm preparing list of images from drawables
        //You can get it from API as well.
        List<ImageSliderModelClassFromMainActivity> sliderModelClassViewpager = new ArrayList<>();

        sliderModelClassViewpager.add(new ImageSliderModelClassFromMainActivity(R.drawable.sliderb));
        sliderModelClassViewpager.add(new ImageSliderModelClassFromMainActivity(R.drawable.slidera));
        sliderModelClassViewpager.add(new ImageSliderModelClassFromMainActivity(R.drawable.sliderc));
        sliderModelClassViewpager.add(new ImageSliderModelClassFromMainActivity(R.drawable.sliderd));
        sliderModelClassViewpager.add(new ImageSliderModelClassFromMainActivity(R.drawable.slidere));

        viewPagerMain.setAdapter(new SliderAdapterMainActivityViewPager(sliderModelClassViewpager, viewPagerMain));
        viewPagerMain.setClipToPadding(false);
        viewPagerMain.setClipChildren(false);
        viewPagerMain.setOffscreenPageLimit(2);
        viewPagerMain.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);


        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                Float r = 1 - Math.abs(position);
                page.setScaleX(0.85f + r * 0.15f);
            }
        });
        viewPagerMain.setPageTransformer(compositePageTransformer);
        viewPagerMain.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                slideHandler.removeCallbacks(sliderRunnable);
                slideHandler.postDelayed(sliderRunnable, 2500); //SLIDE duration 2 Secs

            }
        });

//VIEW PAGER SETTINGS ENDS HERE, there is code in the end where the on create function ends, please checkout


        musisplayIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musisplayIcon.setImageResource(R.drawable.pause);
                startActivity(new Intent(MainActivity.this, PlayMusicActivityMain.class));
                finish();
            }
        });


        menuBurgerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Fragment Using Code
//
//                Fragment fragment = new UserProfile_Fragment();
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.container,fragment).commit();

                menuBurgerIcon.setImageResource(R.drawable.baseline_close_24);
//                recreate();

            }
        });



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("SangeetVadakApp").child("UserInfo").child(Objects.requireNonNull(firebaseAuth.getUid()));
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ModelClassData mcd = snapshot.getValue(ModelClassData.class);

                    String mainClassNameText = mcd.getName();

                    nameMainAct.setText("Hi, " + mainClassNameText);

                    //this will print "V" capital and "len" small
//                        nameMainAct.setText(mainClassNameText.substring(0,1).toUpperCase()+mainClassNameText.substring(1,5).toLowerCase());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RetrievingDataInUserProfile.class));
            }
        });
        nameMainAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RetrievingDataInUserProfile.class));
            }
        });
    }









    //Outside Oncreate ViewPager Code
    Handler slideHandler = new Handler();

    Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPagerMain.setCurrentItem(viewPagerMain.getCurrentItem() + 1);
        }
    };
    @Override
    protected void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        slideHandler.postDelayed(sliderRunnable, 3000);
    }
}