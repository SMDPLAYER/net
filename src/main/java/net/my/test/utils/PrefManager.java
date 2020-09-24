package net.my.test.utils;

import android.content.Context;
import android.content.SharedPreferences;


import net.my.test.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class PrefManager {

    private static String leaderboard_points_ = "leaderboard_points_";

    public static void saveNewPoints(int newPoints, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.local_achivments), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(context.getString(R.string.points), getPoints(context) + newPoints);
        editor.apply();
    }

    public static void setPoints(int points, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.local_achivments), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(context.getString(R.string.points), points);
        editor.apply();
    }

    public static int getPoints(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.local_achivments), Context.MODE_PRIVATE);
        return sharedPref.getInt(context.getString(R.string.points), 0);
    }

    public static void resetPoints(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.local_achivments), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(context.getString(R.string.points), 0);
        editor.apply();
    }

    public static void setLeaderboardPoints(int points, Context context) {
        ArrayList<Integer> places = getLeaderboardPoints(context);
        places.add(points);
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        };
        Collections.sort(places, comparator);

        places.remove(5);
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.local_achivments), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        for (int i = 0; i < 5; i++)
            setLeaderboardPoint(places.get(i), leaderboard_points_ + i, context);
        editor.apply();
    }

    public static ArrayList<Integer> getLeaderboardPoints(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.local_achivments), Context.MODE_PRIVATE);
        ArrayList<Integer> places = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            places.add(i, getLeaderboardPoint(context, leaderboard_points_ + i));
        return places;
    }

    public static void setLeaderboardPoint(int points, String leaderboardPlace, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.local_achivments), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(leaderboardPlace, points);
        editor.apply();
    }

    public static int getLeaderboardPoint(Context context, String leaderboardPlace) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.local_achivments), Context.MODE_PRIVATE);
        return sharedPref.getInt(leaderboardPlace, 0);
    }

    public static int getLives(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.local_achivments), Context.MODE_PRIVATE);
        return sharedPref.getInt(context.getString(R.string.lives), 0);
    }

    public static void resetLives(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.local_achivments), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(context.getString(R.string.lives), 5);
        editor.apply();
    }

    public static void setLives(int lives, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.local_achivments), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(context.getString(R.string.lives), lives);
        editor.apply();
    }


    public static void increaseLives(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.local_achivments), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(context.getString(R.string.lives), getLives(context) + 1);
        editor.apply();
    }


    public static void decreaseLives(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.local_achivments), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(context.getString(R.string.lives), getLives(context) - 1);
        editor.apply();
    }

    public static void setMusic(boolean isMusicPlaying, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.local_achivments), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(context.getString(R.string.music), isMusicPlaying);
        editor.apply();
    }

    public static boolean getMusic(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.local_achivments), Context.MODE_PRIVATE);
        return sharedPref.getBoolean(context.getString(R.string.music), true);
    }


}
