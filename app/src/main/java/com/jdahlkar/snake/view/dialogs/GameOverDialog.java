package com.jdahlkar.snake.view.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ImageView;
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
        TextView btnTryAgain = (TextView) v.findViewById(R.id.try_again_button);
        TextView btnQuit = (TextView) v.findViewById(R.id.quit);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Game Over dialog", "Try Again");
                sV.restart();
                dismiss();
            }
        });
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sV.exit();
                dismiss();
            }
        });
        return v;
    }
    public void setSV(SnakeView sV) {
        this.sV = sV;
    }
}
