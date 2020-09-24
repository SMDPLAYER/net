package net.my.test.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

import net.my.test.utils.PrefManager;
import net.my.test.R;

public class FallingItem {
    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed = 1;

    private int maxX;
    private int minX;

    private int maxY;
    private int minY;

    private Position position;
    private Type type;
    private Context context;

    //creating a rect object
    private Rect detectCollision;

    public FallingItem(Context context, int screenX, int screenY) {
        this.context = context;
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;
        Random generator = new Random();
        speed = 20;
        y = minY;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        Bitmap tempBitmap;
        int scale = 7;
        switch (generator.nextInt(6) + 1) {
            case 1:
                type = Type.BOOMB;
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bomb);
                break;
            case 2:
                type = Type.GUN;
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gun);
                break;
            case 3:
                type = Type.GAS_MASK;
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gas_mask);
                break;
            case 4:
                type = Type.MACHINE_GUN;
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.machine_gun);
                break;
            case 5:
                type = Type.STRAPS;
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.straps);
                break;
            case 6:
                type = Type.RESIDENT;
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.resident);
                break;
        }

        switch (generator.nextInt(3) + 1) {
            case 1:
                x = (maxX * 5 / 6) - (getBitmap().getWidth() / 2);
                position = Position.RIGHT;
                break;
            case 2:
                x = (maxX / 2) - (getBitmap().getWidth() / 2);
                position = Position.CENTER;
                break;
            case 3:
                x = (maxX / 6) - (getBitmap().getWidth() / 2);
                position = Position.LEFT;
                break;
        }

        //initializing rect object
        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void update() {
        y += ((PrefManager.getPoints(context) + 1000) / 1000) * speed;

        //Adding the top, left, bottom and right to the rect object
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
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

    public int getMaxY() {
        return maxY;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        BOOMB, RESIDENT, STRAPS, GAS_MASK, GUN, MACHINE_GUN
    }
}