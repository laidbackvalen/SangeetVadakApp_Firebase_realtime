package com.example.firebasesetup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PlayMusicActivityMain extends AppCompatActivity {

    ImageView backIconPlayMusic, playIconImage;
    CardView playIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music_main);
        backIconPlayMusic = findViewById(R.id.back);
        playIcon = findViewById(R.id.musicIconCardViewMusicPlayerActivity);
        playIconImage = findViewById(R.id.musicPlayMusicPlayerActivity);
        backIconPlayMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        playIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playIconImage.setImageResource(R.drawable.pause);
            }
        });
    }
}