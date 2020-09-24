package net.my.test.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import net.my.test.R;

public class Player {
    private Bitmap bitmap;
    private int x;
    private int y;
    private int maxX;
    private int minX;
    private State state;
    private int intervalForNewBitmap = 0;
    private Position position;
    private Bitmap leftBitmap;
    private Bitmap rightBitmap;

    private Rect detectCollision;

    public Player(Context context, int screenX, int screenY) {
        leftBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.resident_left);
        rightBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.resident_right);
        bitmap = leftBitmap;
        maxX = screenX - bitmap.getWidth();
        minX = 0;
        x = (screenX / 2) - (bitmap.getWidth() / 2);
        y = screenY - bitmap.getHeight();
        state = State.NORMAL;
        position = Position.CENTER;
        //initializing rect object
        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }


    public void update(State state) {

        if (x < minX) {
            x = minX;
        }
        if (x > maxX) {
            x = maxX;
        }

        //adding top, left, bottom and right to the rect object
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();

        if (intervalForNewBitmap == 0) {
            switch (state) {
                case NORMAL:
                    switch (position) {
                        case LEFT:
                        case CENTER:
                            this.bitmap = leftBitmap;
                            break;
                        case RIGHT:
                            this.bitmap = rightBitmap;
                            break;
                    }
                    break;
            }
        } else {
            intervalForNewBitmap--;
        }

    }

    public Rect getDetectCollision() {
        return detectCollision;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public enum State {
        NORMAL, CATCH, BANANA, BOMB
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

}
