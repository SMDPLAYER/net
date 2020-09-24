package net.my.test.activities;

import android.graphics.Point;
import android.media.MediaPlayer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.widget.LinearLayout;

import net.my.test.R;
import net.my.test.dialog.GameOverDialog;
import net.my.test.utils.MusicPlayer;
import net.my.test.view.GameView;
import net.my.test.utils.PrefManager;

public class GameActivity extends AppCompatActivity implements GameView.GameOverListener {

    private GameView pirateGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PrefManager.resetPoints(this);
        PrefManager.resetLives(this);
        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        pirateGameView = new GameView(this, size.x, size.y);

        setContentView(R.layout.activity_game);

        setContentView(R.layout.activity_game);
        LinearLayout surface = (LinearLayout)findViewById(R.id.surface);
        surface.addView(pirateGameView);
    }

    //pausing the game when activity is paused
    @Override
    protected void onPause() {
        super.onPause();
        pirateGameView.pause();
       MusicPlayer.pause();
    }

    //running the game when activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        pirateGameView.resume();
        if (PrefManager.getMusic(this))
            MusicPlayer.get(this).start();
    }

    @Override
    public void onGameOver() {
        new GameOverDialog().show(getSupportFragmentManager(), "GameOverDialog");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MediaPlayer mediaPlayer = MusicPlayer.get(this);
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) MusicPlayer.release();
    }
}
