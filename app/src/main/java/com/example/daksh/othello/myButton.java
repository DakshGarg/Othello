package com.example.daksh.othello;

import android.content.Context;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Daksh Garg on 6/18/2017.
 */

public class myButton extends Button {

    int xCoordinate;
    int yCoordinate;
    public myButton(Context context,int x,int y) {
        super(context);
        xCoordinate=x;
        yCoordinate=y;
    }
}
