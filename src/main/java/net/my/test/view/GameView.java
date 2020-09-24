package net.my.test.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import net.my.test.utils.PrefManager;
import net.my.test.model.FallingItem;
import net.my.test.model.Player;
import net.my.test.model.Position;
import net.my.test.R;

public class GameView extends SurfaceView implements Runnable {

    volatile boolean playing;
    private Thread gameThread = null;
    private Player player;
    private AppCompatActivity context;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private int screenX;
    private int screenY;
    private ArrayList<FallingItem> fallingItems;
    private Bitmap livesBitmap;
    private Bitmap monitorBitmap;
    private int bonusLives;
    private Bitmap bgBitmap;
    private GameOverListener listener;

    private int intervalAnimation;
    private Position futurePlayerPosition;
    private int futurePlayerX;
    private Bitmap tempLivesBitmap;
    private Bitmap live1;
    private Bitmap live2;
    private Bitmap live3;
    private Bitmap live4;
    private Bitmap live5;

    public GameView(AppCompatActivity context, int screenX, int screenY) {
        super(context);
        this.screenX = screenX;
        this.screenY = screenY;
        player = new Player(context, screenX, screenY);
        this.context = context;
        surfaceHolder = getHolder();
        paint = new Paint();
        fallingItems = new ArrayList<>();
        fallingItems.add(new FallingItem(context, screenX, screenY));
        BitmapFactory.Options options = new BitmapFactory.Options();
        monitorBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.monitor);
        bgBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.bg), screenX, screenY, false);

        live1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.live_1);
        live2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.live_2);
        live3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.live_3);
        live4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.live_4);
        live5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.live_5);
        }

    @Override
    public void run() {
        while (playing) {
            update();
            draw();
            control();
        }
    }

    private void update() {
        int fallingItemMinY = screenY;

        Player.State state = Player.State.NORMAL;
        ArrayList<FallingItem> itemsForDelete = new ArrayList<>();
        for (int i = 0; i < fallingItems.size(); i++) {
            FallingItem fallingItem = fallingItems.get(i);
            fallingItem.update();

            if (fallingItemMinY > fallingItem.getY())
                fallingItemMinY = fallingItem.getY();

            if (fallingItem.getY() > fallingItem.getMaxY() + fallingItem.getBitmap().getHeight()) {
                itemsForDelete.add(fallingItem);
            }

            //if collision occurs with player
            if (fallingItem.getPosition().equals(player.getPosition())) {

                if (Rect.intersects(player.getDetectCollision(), fallingItem.getDetectCollision())) {
                    switch (fallingItem.getType()) {
                        case BOOMB:
                            PrefManager.decreaseLives(context);
                            break;
                        case MACHINE_GUN:
                            PrefManager.saveNewPoints(50, context);
                            break;
                        case GUN:
                            PrefManager.saveNewPoints(40, context);
                            break;
                        case GAS_MASK:
                            PrefManager.saveNewPoints(30, context);
                            break;
                        case STRAPS:
                            PrefManager.saveNewPoints(20, context);
                            break;
                        case RESIDENT:
                            PrefManager.saveNewPoints(10, context);
                            break;
                    }

                    itemsForDelete.add(fallingItem);
                }
            }
        }

        if (fallingItems.size() > 0)
            fallingItems.removeAll(itemsForDelete);

        if (fallingItemMinY > 500)
            fallingItems.add(new FallingItem(context, screenX, screenY));
        player.update(state);
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawBitmap(bgBitmap, 0, 0, paint);

            paint.setColor(Color.WHITE);

            if (intervalAnimation < 1)
                canvas.drawBitmap(
                        player.getBitmap(),
                        player.getX(),
                        player.getY(),
                        paint);
            else {
                if (intervalAnimation == 3) {
                    player.setX(futurePlayerX);
                    player.setPosition(futurePlayerPosition);
                }
                Bitmap bitmap = player.getBitmap();

                if (intervalAnimation == 4 || intervalAnimation == 3) {
                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 4, bitmap.getHeight() / 4, false);
                    canvas.drawBitmap(
                            scaledBitmap,
                            player.getX() + (bitmap.getWidth() - scaledBitmap.getWidth()) / 2,
                            player.getY() + (bitmap.getHeight() - scaledBitmap.getHeight()) / 2,
                            paint);
                }
                if (intervalAnimation == 5 || intervalAnimation == 2) {
                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2, false);
                    canvas.drawBitmap(
                            scaledBitmap,
                            player.getX() + (bitmap.getWidth() - scaledBitmap.getWidth()) / 2,
                            player.getY() + (bitmap.getHeight() - scaledBitmap.getHeight()) / 2,
                            paint);
                }
                if (intervalAnimation == 6 || intervalAnimation == 1) {
                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() / 1.5), (int) (bitmap.getHeight() / 1.5), false);
                    canvas.drawBitmap(
                            scaledBitmap,
                            player.getX() + (bitmap.getWidth() - scaledBitmap.getWidth()) / 2,
                            player.getY() + (bitmap.getHeight() - scaledBitmap.getHeight()) / 2,
                            paint);
                }
                intervalAnimation--;
            }

            //drawing the enemies
            for (int i = 0; i < fallingItems.size(); i++) {
                canvas.drawBitmap(
                        fallingItems.get(i).getBitmap(),
                        fallingItems.get(i).getX(),
                        fallingItems.get(i).getY(),
                        paint
                );
            }

            //lives
            if (PrefManager.getLives(context) == 0) {
                ((GameOverListener) context).onGameOver();
                playing = false;
                gameThread.interrupt();
            }

            int lives = PrefManager.getLives(context);
            if (lives != 0) {
                switch (lives) {
                    case 1:
                        tempLivesBitmap = live1;
                        break;
                    case 2:
                        tempLivesBitmap = live2;
                        break;
                    case 3:
                        tempLivesBitmap = live3;
                        break;
                    case 4:
                        tempLivesBitmap = live4;
                        break;
                    case 5:
                        tempLivesBitmap = live5;
                        break;
                }

                canvas.drawBitmap(
                        tempLivesBitmap,
                        screenX / 2 - tempLivesBitmap.getWidth() / 2,
                        tempLivesBitmap.getHeight() / 2,
                        paint
                );
            }

            int points = PrefManager.getPoints(context);

            if (points / 1000 > bonusLives) {
                if (PrefManager.getLives(context) < 5)
                    PrefManager.increaseLives(context);
                bonusLives++;
            }

            String text = String.valueOf(points);
            int measureText = (int) paint.measureText(text);
            paint.setTextSize((float) (monitorBitmap.getHeight() * 0.9));
            canvas.drawBitmap(
                    monitorBitmap,
                    screenX / 2 - monitorBitmap.getWidth() / 2,
                    (float) (tempLivesBitmap.getHeight() * 2.5),
                    paint
            );
            canvas.drawText(text, screenX / 2 - measureText / 2, (float) (tempLivesBitmap.getHeight() * 2.5 + (monitorBitmap.getHeight() * 0.8)), paint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                if (motionEvent.getX() > screenX * 2 / 3) {
                    intervalAnimation = 6;
                    futurePlayerX = ((screenX * 5 / 6) - (player.getBitmap().getWidth() / 2));
                    futurePlayerPosition = Position.RIGHT;
                } else if (motionEvent.getX() > screenX / 3) {
                    intervalAnimation = 6;
                    futurePlayerX = ((screenX / 2) - (player.getBitmap().getWidth() / 2));
                    futurePlayerPosition = Position.CENTER;
                } else {
                    intervalAnimation = 6;
                    futurePlayerX = ((screenX / 6) - (player.getBitmap().getWidth() / 2));
                    futurePlayerPosition = Position.LEFT;
                }
                break;
        }
        return true;
    }

    public interface GameOverListener {
        void onGameOver();
    }
}