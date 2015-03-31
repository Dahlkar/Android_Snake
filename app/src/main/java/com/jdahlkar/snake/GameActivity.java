package com.jdahlkar.snake;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.jdahlkar.snake.view.SnakeView;

/**
 * Created by Johan on 2015-03-30.
 */
public class GameActivity extends Activity {
    private SnakeView view;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snake_layout);
        TextView scoreTextView = (TextView) findViewById(R.id.score);
        view = (SnakeView) findViewById(R.id.snake);
        view.setScoreTextView(scoreTextView);
        view.update();
    }
}
