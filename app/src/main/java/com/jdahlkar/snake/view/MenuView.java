package com.jdahlkar.snake.view;

import android.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jdahlkar.snake.GameActivity;
import com.jdahlkar.snake.R;

/**
 * Created by Johan on 2015-03-30.
 */
public class MenuView extends Fragment {
    TextView playButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.menu_layout, null);
        playButton = (TextView) rootView.findViewById(R.id.menu_play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
