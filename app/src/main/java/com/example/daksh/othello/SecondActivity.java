package com.example.daksh.othello;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {
    Bundle bundle;
    String player_1_name;
    String player_2_name;
    int player_1_balls;
    int player_2_balls;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        textView=(TextView)findViewById(R.id.textView);
        Intent intent=getIntent();
        bundle=intent.getExtras();
        player_1_name=bundle.getString("player1name");
        player_2_name=bundle.getString("player2name");
        player_1_balls=bundle.getInt("player1balls");
        player_2_balls=bundle.getInt("player2balls");

        if(player_1_balls>player_2_balls)
        {
            textView.setText(player_1_name+" Wins\n"+"Black Balls - "+player_1_balls+"\nWhite Balls - "+player_2_balls);
        }
        else if(player_1_balls<player_2_balls)
        {
            textView.setText(player_2_name+" Wins\n"+"Black Balls - "+player_1_balls+"\nWhite Balls - "+player_2_balls);
        }
        else if(player_1_balls==player_2_balls){
            textView.setText("Game Ends In Draw\n"+"Black Balls - "+player_1_balls+"\nWhite Balls - "+player_2_balls);
        }



    }
}
