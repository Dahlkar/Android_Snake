package com.jdahlkar.snake;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import com.jdahlkar.snake.view.MenuView;

/**
 * Created by Johan on 2015-03-25.
 */
public class SnakeActivity extends Activity {
    private Fragment fragment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        Bundle args = getIntent().getExtras();
        fragment = new MenuView();
        fragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .commit();
    }
}
