package net.my.test.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import net.my.test.R;

import net.my.test.dialog.PersonalBestDialog;
import net.my.test.dialog.RulesDialog;
import net.my.test.utils.MusicPlayer;
import net.my.test.utils.PrefManager;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final MainActivity context = MainActivity.this;
        findViewById(R.id.btn_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, GameActivity.class));
            }
        });

        ImageView rulesIV = findViewById(R.id.iv_rules);
        ImageView leaderboardIV = findViewById(R.id.iv_leaderboard);
        ImageView soundOnIV = findViewById(R.id.iv_soundon);
        ImageView soundOffIV = findViewById(R.id.iv_soundoff);


        rulesIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RulesDialog().show(getSupportFragmentManager(), "RulesDialog");
            }
        });

        leaderboardIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PersonalBestDialog().show(getSupportFragmentManager(), "PersonalBestDialog");
            }
        });

        soundOnIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PrefManager.getMusic(context)) {
                    PrefManager.setMusic(true, context);
                    MusicPlayer.get(context).start();
                }
            }
        });

        soundOffIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PrefManager.getMusic(context)) {
                    PrefManager.setMusic(false, context);
                    MusicPlayer.pause();
                }
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (PrefManager.getMusic(this))
            MusicPlayer.get(this).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MusicPlayer.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MediaPlayer mediaPlayer = MusicPlayer.get(this);
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) MusicPlayer.release();
    }


}
