package com.jdahlkar.snake.view.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jdahlkar.snake.R;
import com.jdahlkar.snake.view.SnakeView;

/**
 * Created by Johan on 2015-03-30.
 */
public class GameOverDialog extends DialogFragment {
    private SnakeView sV;

    public static GameOverDialog newInstance() {
        GameOverDialog fragment = new GameOverDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public GameOverDialog(){
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_layout, container);
        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.dialog);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        linearLayout.addView(getTryAgain(inflater, (ViewGroup) v));
        linearLayout.addView(getShareScore(inflater, (ViewGroup) v));
        linearLayout.addView(getQuit(inflater, (ViewGroup) v));
        return v;
    }
    private View getTryAgain(LayoutInflater inflater, ViewGroup parent) {
        TextView btnTryAgain = (TextView) inflater.inflate(R.layout.dialog_section, parent, false);
        btnTryAgain.setText("Try Again");
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Game Over dialog", "Try Again");
                sV.restart();
                dismiss();
            }
        });

        return btnTryAgain;
    }
    private View getQuit(LayoutInflater inflater, ViewGroup parent) {
        TextView btnQuit = (TextView) inflater.inflate(R.layout.dialog_section, parent, false);
        btnQuit.setText("Quit");
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sV.exit();
                dismiss();
            }
        });
        return btnQuit;
    }

    private View getShareScore(LayoutInflater inflater, ViewGroup parent) {
        TextView btnShareScore = (TextView) inflater.inflate(R.layout.dialog_section, parent, false);
        btnShareScore.setText("Share Score");
        btnShareScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                String text = "I just scored " + sV.getScore() + " points in SNAKE!";
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(shareIntent, "Share using"));
            }
        });
        return btnShareScore;
    }
    public void setSV(SnakeView sV) {
        this.sV = sV;
    }
}
