package com.jdahlkar.snake.view;

import android.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
        LinearLayout listView = (LinearLayout)rootView.findViewById(R.id.main_menu);
        playButton = (TextView) inflater.inflate(R.layout.dialog_section, (ViewGroup)rootView, false);
        playButton.setText("Play");
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                startActivity(intent);
            }
        });
        listView.addView(playButton);
        listView.addView(getQuit(inflater, (ViewGroup) rootView));
        return rootView;
    }
    private View getQuit(LayoutInflater inflater, ViewGroup parent) {
        TextView btnQuit = (TextView) inflater.inflate(R.layout.dialog_section, parent, false);
        btnQuit.setText("Quit");
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return btnQuit;
    }
}
