package net.my.test.utils;

import android.content.Context;
import android.media.MediaPlayer;

import net.my.test.R;

public class MusicPlayer {
    private static MediaPlayer INSTANCE;

    public static MediaPlayer get(Context context) {
        if (INSTANCE == null) {
            INSTANCE = MediaPlayer.create(context, R.raw.music);
            INSTANCE.setLooping(true);
        }
        return INSTANCE;
    }

    public static void release() {
        if (INSTANCE != null) {
            INSTANCE.release();
            INSTANCE = null;
        }
    }

    public static void pause() {
        if (INSTANCE != null) {
            INSTANCE.pause();
        }
    }


}