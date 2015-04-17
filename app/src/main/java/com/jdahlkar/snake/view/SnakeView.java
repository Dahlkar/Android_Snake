package com.jdahlkar.snake.view;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.jdahlkar.snake.GameActivity;
import com.jdahlkar.snake.R;
import com.jdahlkar.snake.model.Food;
import com.jdahlkar.snake.model.Snake;
import com.jdahlkar.snake.model.SnakeSegment;
import com.jdahlkar.snake.view.dialogs.GameOverDialog;

import java.util.Iterator;


/**
 * Created by Johan on 2015-03-25.
 */
public class SnakeView extends View implements View.OnTouchListener{
    private Context context;
    private Snake snake;
    private boolean dead = false;
    private Food food;
    private int score = 0;
    private int highscore;
    private boolean pause = false;
    private int counter = 0;
    private int boardWidth = 20;
    private int boardHeight = 20;
    private final Paint paint = new Paint();
    private RefreshHandler redrawHandler = new RefreshHandler();
    private TextView scoreTextView, highScoreTextView, pauseTextView;
    private SharedPreferences data;
    private final GestureDetector gestureDetector = new GestureDetector(new GestureListener());
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    class RefreshHandler extends Handler {
        public void handleMessage(Message msg) {
            if(msg.what == 0) {
                SnakeView.this.update();
                SnakeView.this.invalidate();
                Log.d("handlemessage", "msg == 0");
            } else {
                Log.d("handlemessage", "msg != 0");
            }
        }
        public void sleep(long delayMillis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    }

    public SnakeView(Context context) {
        super(context);
        init(context);
    }
    public SnakeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public SnakeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        setFocusable(true);
        this.snake = new Snake(9, 9);
        this.food = new Food(boardWidth, boardHeight);
        paint.setStyle(Paint.Style.FILL);
        this.setOnTouchListener(this);
        data = context.getSharedPreferences("com.jdahlkar.snake", Context.MODE_PRIVATE);
    }
    public void setScoreTextView(TextView scoreTextView) {
        this.scoreTextView = scoreTextView;
        scoreTextView.setText("Score: " + score);
    }
    public void setHighScoreTextView(TextView highScoreTextView) {
        this.highScoreTextView = highScoreTextView;
        highscore = data.getInt("highscore", 0);
        highScoreTextView.setText("High Score: " + highscore);
    }

    public void setPauseTextView(TextView pauseTextView) {
        this.pauseTextView = pauseTextView;
        pauseTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
            }
        });
    }
    public boolean checkForWallCollision() {
        SnakeSegment head = snake.getHead();
        int headX = head.getX();
        int headY = head.getY();
        if(headX == -1 || headX == boardWidth || headY == -1 || headY == boardHeight) {
            return true;
        }
        return false;
    }
    public boolean checkForFoodCollision() {
        SnakeSegment head = snake.getHead();
        int headX = head.getX();
        int headY = head.getY();
        int foodX = food.getX();
        int foodY = food.getY();
        if(headX == foodX && headY == foodY) {
            return true;
        }
        return false;
    }
    public void update() {
        if(snake.checkForSelfCollision() || checkForWallCollision()) {
            dead = true;
        }
        if(checkForFoodCollision()) {
            food.respawn();
            score+= 10;
            scoreTextView.setText("Score: " + score);
                   /* int delay = timer.getDelay();
                    if(delay != 100) {
                        timer.setDelay(delay-2);
                    }*/
        } else {
            snake.dropLast();
        }
        if(dead) {
            Log.d("update", "dead");
            GameOverDialog gameOverDialog = GameOverDialog.newInstance();
            gameOverDialog.setSV(this);
            gameOverDialog.show(((Activity) context).getFragmentManager(), "gameover");
            highscore = highscore > score ? highscore : score;
            SharedPreferences.Editor editor = data.edit();
            editor.putInt("highscore", highscore);
            editor.commit();
            highScoreTextView.setText("High Score: " + highscore);
            return;
        }
        snake.move();
        redrawHandler.sleep(300);
    }
    public int tileWidth() {
        return getWidth() / boardWidth;
    }
    public int tileHeight() {
        return getHeight()/boardHeight;
    }

    private void drawTile(final Canvas canvas, int x, int y){
        paint.setColor(Color.RED);
        Rect rect = new Rect(x, y, x+tileWidth(), y+tileHeight());
        canvas.drawRect(rect, paint);
    }
    private void drawSnakeTile(final Canvas canvas,int x, int y){
        paint.setColor(Color.BLACK);
        Rect rect = new Rect(x + 1, y + 1, x+tileWidth() - 1, y+tileHeight() - 1);
        canvas.drawRect(rect, paint);

    }
    protected void onDraw(final Canvas canvas) {
        int x = food.getX();
        int y = food.getY();

        drawTile(canvas, x * tileWidth(), y * tileHeight());
        Iterator<SnakeSegment> it = snake.getBody();
        while(it.hasNext()) {
            SnakeSegment current = it.next();
            x = current.getX();
            y = current.getY();
            drawSnakeTile(canvas, x * tileWidth(), y * tileHeight());
        }
    }
    protected void onMeasure(int width, int height) {
        setMeasuredDimension(450, 450);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    private void onSwipeLeft() {
        snake.goLeft();
        Log.d("snake", "left");
    }

    private void onSwipeTop() {
        snake.goUp();
        Log.d("snake", "up");
    }

    private void onSwipeBottom() {
        snake.goDown();
        Log.d("snake", "down");
    }

    private void onSwipeRight() {
        snake.goRight();
        Log.d("snake", "right");
    }
    public void restart() {
        snake = new Snake(9, 9);
        food.respawn();
        dead = false;
        update();
        invalidate();
        Log.d("SnakeView", "restart");
    }
    private void pause() {
        if (pause) {
            redrawHandler.removeMessages(1);
            redrawHandler.sendMessage(redrawHandler.obtainMessage(0));
            pauseTextView.setText("Pause");
            pause = false;
        } else {
            redrawHandler.removeMessages(0);
            redrawHandler.sendMessage(redrawHandler.obtainMessage(1));
            pauseTextView.setText("Play");
            pause = true;
        }
    }
    public void exit() {
        ((Activity)context).finish();
    }
    public int getScore() { return score; }
}
