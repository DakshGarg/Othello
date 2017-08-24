package com.example.daksh.othello;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout mainLayout;
    static int n = 8;
    myButton buttons[][];
    LinearLayout rowLayout1;
    LinearLayout rowLayout2[];
    TextView textView1;
    TextView textView2;
    final static int BLACK = 0;
    final static int WHITE = 1;
    final static int EMPTY = -1;
    final static int VALID_MOVE = 2;
    boolean gameOver = false;
    boolean player1Turn = true;
    static final int INCOMPLETE = 0;
    static final int PLAYER_1_WINS = 1;
    static final int PLAYER_2_WINS = 2;
    static final int DRAW = 3;
    int board[][];
    LinearLayout.LayoutParams params;
    int countBalls = 2;
    String player1name,player2name;
    Bundle bundle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
      bundle=intent.getExtras();
        player1name=bundle.getString("player1name");
        player2name=bundle.getString("player2name");


        board = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = EMPTY;
            }
        }
        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        setUpBoard();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.newgame) {
            /*Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);*/
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    board[i][j] = EMPTY;
                }
            }
            gameOver=false;
            player1Turn=true;
            countBalls=2;
            setUpBoard();
        }
        return true;
    }
    private void setUpBoard() {

    mainLayout.setBackgroundResource(R.drawable.main_layout_making);
        mainLayout.removeAllViews();
        textView1 = new TextView(this);
        params = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,0, 1.5f);
        params.setMargins(1, 1, 1, 1);
        textView1.setLayoutParams(params);
        textView1.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        textView1.setText("Black Balls - " + countBalls+"   "+"White Balls - " + countBalls);
        textView1.setTextColor(ContextCompat.getColor(this, R.color.black));
        textView1.setTypeface(textView1.getTypeface(), Typeface.BOLD);
        textView1.setGravity(Gravity.CENTER);
        textView1.setTextSize(23);
        mainLayout.addView(textView1);

        rowLayout2 = new LinearLayout[n];
        for (int i = 0; i < n; i++) {
            rowLayout2[i] = new LinearLayout(this);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f);
            rowLayout2[i].setLayoutParams(params);
            rowLayout2[i].setOrientation(LinearLayout.HORIZONTAL);
            mainLayout.addView(rowLayout2[i]);
        }

        buttons = new myButton[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                buttons[i][j] = new myButton(this, i, j);
                params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
                params.setMargins(1,1,1,1);
                buttons[i][j].setLayoutParams(params);
                buttons[i][j].setBackgroundColor(ContextCompat.getColor(this,R.color.green));
                buttons[i][j].setBackgroundResource(R.drawable.button_making);
                buttons[i][j].setOnClickListener(this);
                rowLayout2[i].addView(buttons[i][j]);
            }

        }
        textView2 = new TextView(this);
        params = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,0, 1.8f);
        //params.setMargins(1, 1, 1, 1);
        textView2.setLayoutParams(params);
        textView2.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
        mainLayout.addView(textView2);
        board[3][3]=WHITE;
        board[4][4]=WHITE;
        board[3][4]=BLACK;
        board[4][3]=BLACK;

        buttons[3][3].setBackgroundResource(R.drawable.white_ball_making);
        buttons[4][4].setBackgroundResource(R.drawable.white_ball_making);
        buttons[3][4].setBackgroundResource(R.drawable.black_ball_making);
        buttons[4][3].setBackgroundResource(R.drawable.black_ball_making);


        validMoves(BLACK); //always player_1_turn ball is black and it will click first

    }

    @Override
    public void onClick(View v) {
        if (gameOver) {
            return;
        }
        boolean flag=false;


        myButton button = (myButton) v;
        if (player1Turn) {
            boardChange(button,BLACK);
            validMoves(WHITE);
            for(int i=0;i<n;i++)
            {
                for(int j=0;j<n;j++)
                {
                    if(board[i][j]==VALID_MOVE)
                    {
                        flag=true;
                        break;
                    }
                }
            }
            if(flag==false )
            {

                validMoves(BLACK);
                player1Turn=true;
            }

        }
        else{
            boardChange(button,WHITE);
            validMoves(BLACK);
            for(int i=0;i<n;i++)
            {
                for(int j=0;j<n;j++)
                {
                    if(board[i][j]==VALID_MOVE)
                    {
                        flag=true;
                        break;
                    }
                }
            }
            if(flag==false )
            {
                validMoves(WHITE);
                player1Turn=false;
            }


        }
        if(flag==true)
        player1Turn=!player1Turn;

        int black_ball = countBalls(BLACK);
        int white_ball = countBalls(WHITE);
        textView1.setText("Black Balls - "+black_ball+"   "+"White Balls - "+white_ball);

        int status=gameStatus();

        if(status==PLAYER_1_WINS)
        {
            Toast.makeText(this,player1name+" Wins",Toast.LENGTH_SHORT).show();
            gameOver=true;

        }
        else if(status==PLAYER_2_WINS)
        {
            Toast.makeText(this,player2name+" Wins",Toast.LENGTH_SHORT).show();
            gameOver=true;
        }
        else if(status==DRAW)
        {
            Toast.makeText(this,"Draw",Toast.LENGTH_SHORT).show();
            gameOver=true;
        }
        if(gameOver)
        {
            bundle.putInt("player1balls",black_ball);
            bundle.putInt("player2balls",white_ball);
            Intent intent=new Intent(this,SecondActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            return;
        }
        if(flag==false)
        {
            Toast.makeText(this,"Pass",Toast.LENGTH_SHORT).show();
        }

    }




    private int gameStatus() {

        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                if(board[i][j]==VALID_MOVE)
                {
                    return INCOMPLETE;
                }

            }
        }

        if(countBalls(BLACK)>countBalls(WHITE))
            return PLAYER_1_WINS;
        else if(countBalls(BLACK)<countBalls(WHITE))
            return PLAYER_2_WINS;

        return DRAW;
    }

    private int countBalls(int ball) {
        int count=0;

        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                if(board[i][j]==ball)
                    count++;
            }
        }
        return count;
    }

    private void boardChange(myButton button, int ball) {
        int i=button.xCoordinate;
        int j=button.yCoordinate;
        int secondball;
        board[i][j]=ball;

        if(ball==BLACK) {
            buttons[i][j].setText("");
            buttons[i][j].setBackgroundResource(R.drawable.black_ball_making);
            secondball = WHITE;
        }
        else {
            buttons[i][j].setText("");
            buttons[i][j].setBackgroundResource(R.drawable.white_ball_making);
            secondball = BLACK;
        }
        int x,y;

        x = i;
        y = j - 1;
        while (y >= 0 && board[x][y] == secondball) {
            y--;
        }
        if (y != j - 1 && y >= 0 && board[x][y] == ball) {
            while(y<=j- 1){
                board[x][y]=ball;
                if(ball==WHITE)
                {
                    buttons[x][y].setBackgroundResource(R.drawable.white_ball_making);
                }
                else{
                    buttons[x][y].setBackgroundResource(R.drawable.black_ball_making);
                }
                y++;
            }

        }

        x = i + 1;
        y = j - 1;
        while (y >= 0 && x < n && board[x][y] == secondball) {
            x++;
            y--;
        }
        if (y != j - 1 && x != i + 1 && y >= 0 && x < n && board[x][y] == ball) {
            while(y<=j- 1 && x>=i+1 ){
                board[x][y]=ball;
                if(ball==WHITE)
                {

                    buttons[x][y].setBackgroundResource(R.drawable.white_ball_making);
                }
                else{

                    buttons[x][y].setBackgroundResource(R.drawable.black_ball_making);
                }
                y++;
                x--;
            }
        }

        x = i + 1;
        y = j;
        while (x < n && board[x][y] == secondball) {
         //   Log.i("Check","Not working first");
            x++;
        }
        if (x != i + 1 && x < n && board[x][y] == ball) {
            while(x>=i+ 1){
           //     Log.i("Check","not working second");
                board[x][y]=ball;
                if(ball==WHITE)
                {
                    buttons[x][y].setBackgroundResource(R.drawable.white_ball_making);
                }
                else{
                    buttons[x][y].setBackgroundResource(R.drawable.black_ball_making);
                }
                x--;
            }
        }

        x = i + 1;
        y = j + 1;
        while (y < n && x < n && board[x][y] == secondball) {
            x++;
            y++;
        }
        if (x != i + 1 && y != j + 1 && y < n && x < n && board[x][y] == ball) {
            while(y>=j+ 1 && x>=i+1){
                board[x][y]=ball;
                if(ball==WHITE)
                {
                    buttons[x][y].setBackgroundResource(R.drawable.white_ball_making);
                }
                else{
                    buttons[x][y].setBackgroundResource(R.drawable.black_ball_making);
                }
                y--;
                x--;
            }
        }

        x = i;
        y = j + 1;
        while (y < n && board[x][y] == secondball) {
            y++;
        }
        if (y != j + 1 && y < n && board[x][y] == ball) {
            while(y>=j+ 1){
                board[x][y]=ball;
                if(ball==WHITE)
                {
                    buttons[x][y].setBackgroundResource(R.drawable.white_ball_making);
                }
                else{
                    buttons[x][y].setBackgroundResource(R.drawable.black_ball_making);
                }
                y--;
            }
        }

        x = i - 1;
        y = j + 1;
        while (y < n && x >= 0 && board[x][y] == secondball) {
            x--;
            y++;
        }
        if (x != i - 1 && y != j + 1 && y < n && x >= 0 && board[x][y] == ball) {
            while(y>=j+1 && x<=i-1){
                board[x][y]=ball;
                if(ball==WHITE)
                {
                    buttons[x][y].setBackgroundResource(R.drawable.white_ball_making);
                }
                else{
                    buttons[x][y].setBackgroundResource(R.drawable.black_ball_making);
                }
                y--;
                x++;
            }
        }

        x = i - 1;
        y = j;
        while (x >= 0 && board[x][y] == secondball) {
            x--;
        }
        if (x != i - 1 && x >= 0 && board[x][y] == ball) {
            while(x<=i- 1){
                board[x][y]=ball;
                if(ball==WHITE)
                {
                    buttons[x][y].setBackgroundResource(R.drawable.white_ball_making);
                }
                else{
                    buttons[x][y].setBackgroundResource(R.drawable.black_ball_making);
                }
                x++;
            }
        }

        x = i - 1;
        y = j - 1;
        while (x >= 0 && y >= 0 && board[x][y] == secondball) {
            x--;
            y--;
        }
        if (x != i - 1 && y != j - 1 && x >= 0 && y >= 0 && board[x][y] == ball) {
            while(x<=i- 1 && y<=j-1){
                board[x][y]=ball;
                if(ball==WHITE)
                {
                    buttons[x][y].setBackgroundResource(R.drawable.white_ball_making);
                }
                else{
                    buttons[x][y].setBackgroundResource(R.drawable.black_ball_making);
                }
                y++;
                x++;
            }
        }
    }

    private void validMoves(int ball) {
        int x, y;
        int secondball;
        if(ball==BLACK)
            secondball=WHITE;
        else
            secondball=BLACK;



        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(board[i][j]==VALID_MOVE){
                    board[i][j]=EMPTY;
                    buttons[i][j].setText("");
                }
                buttons[i][j].setEnabled(false);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == ball) {
                    x = i;
                    y = j - 1;
                    while (y >= 0 && board[x][y] == secondball) {
                        y--;
                    }
                    if (y != j - 1 && y >= 0 && board[x][y] == EMPTY) {
                        board[x][y] = VALID_MOVE;
                        if(ball==BLACK){
                            buttons[x][y].setTextColor(ContextCompat.getColor(this, R.color.black));
                            buttons[x][y].setText("o");
                          //  buttons[x][y].setGravity(Gravity.CENTER);
                        }
                        else{
                            buttons[x][y].setTextColor(ContextCompat.getColor(this, R.color.white));
                            buttons[x][y].setText("o");
                         //   buttons[x][y].setGravity(Gravity.CENTER);
                        }
                        //buttons[x][y].setTextSize(40);
                        buttons[x][y].setEnabled(true);
                    }

                    x = i + 1;
                    y = j - 1;
                    while (y >= 0 && x < n && board[x][y] == secondball) {
                        x++;
                        y--;
                    }
                    if (y != j - 1 && x != i + 1 && y >= 0 && x < n && board[x][y] == EMPTY) {
                        board[x][y] = VALID_MOVE;
                        if(ball==BLACK){
                            buttons[x][y].setTextColor(ContextCompat.getColor(this, R.color.black));
                            buttons[x][y].setText("o");
                       //     buttons[x][y].setGravity(Gravity.CENTER);
                        }
                        else{
                            buttons[x][y].setTextColor(ContextCompat.getColor(this, R.color.white));
                            buttons[x][y].setText("o");
                     //       buttons[x][y].setGravity(Gravity.CENTER);
                        }
                        //buttons[x][y].setTextSize(40);
                        buttons[x][y].setEnabled(true);
                    }

                    x = i + 1;
                    y = j;
                    while (x < n && board[x][y] == secondball) {
                       // Log.i("Check","First while loop");
                        x++;
                    }
                    if (x != i + 1 && x < n && board[x][y] == EMPTY) {
                        //Log.i("Check","Second while loop");
                        board[x][y] = VALID_MOVE;
                        if(ball==BLACK){
                            buttons[x][y].setTextColor(ContextCompat.getColor(this, R.color.black));
                            buttons[x][y].setText("o");
                       //     buttons[x][y].setGravity(Gravity.CENTER);
                        }
                        else{
                            buttons[x][y].setTextColor(ContextCompat.getColor(this, R.color.white));
                            buttons[x][y].setText("o");
                     //       buttons[x][y].setGravity(Gravity.CENTER);
                        }
    //                    buttons[x][y].setTextSize(40);
                        buttons[x][y].setEnabled(true);
                    }

                    x = i + 1;
                    y = j + 1;
                    while (y < n && x < n && board[x][y] == secondball) {
                        x++;
                        y++;
                    }
                    if (x != i + 1 && y != j + 1 && y < n && x < n && board[x][y] == EMPTY) {
                        board[x][y] = VALID_MOVE;
                        if(ball==BLACK){
                            buttons[x][y].setTextColor(ContextCompat.getColor(this, R.color.black));
                            buttons[x][y].setText("o");
                   //         buttons[x][y].setGravity(Gravity.CENTER);
                        }
                        else{
                            buttons[x][y].setTextColor(ContextCompat.getColor(this, R.color.white));
                            buttons[x][y].setText("o");
                 //           buttons[x][y].setGravity(Gravity.CENTER);
                        }
                        //buttons[x][y].setTextSize(40);
                        buttons[x][y].setEnabled(true);
                    }

                    x = i;
                    y = j + 1;
                    while (y < n && board[x][y] == secondball) {
                        y++;
                    }
                    if (y != j + 1 && y < n && board[x][y] == EMPTY) {
                        board[x][y] = VALID_MOVE;
                        if(ball==BLACK){
                            buttons[x][y].setTextColor(ContextCompat.getColor(this, R.color.black));
                            buttons[x][y].setText("o");
                            //buttons[x][y].setGravity(Gravity.CENTER);
                        }
                        else{
                            buttons[x][y].setTextColor(ContextCompat.getColor(this, R.color.white));
                            buttons[x][y].setText("o");
                           // buttons[x][y].setGravity(Gravity.CENTER);
                        }
                        //buttons[x][y].setTextSize(40);
                        buttons[x][y].setEnabled(true);
                    }

                    x = i - 1;
                    y = j + 1;
                    while (y < n && x >= 0 && board[x][y] == secondball) {
                        x--;
                        y++;
                    }
                    if (x != i - 1 && y != j + 1 && y < n && x >= 0 && board[x][y] == EMPTY) {
                        board[x][y] = VALID_MOVE;
                        if(ball==BLACK){
                            buttons[x][y].setTextColor(ContextCompat.getColor(this, R.color.black));
                            buttons[x][y].setText("o");
                           // buttons[x][y].setGravity(Gravity.CENTER);
                        }
                        else{
                            buttons[x][y].setTextColor(ContextCompat.getColor(this, R.color.white));
                            buttons[x][y].setText("o");
                            //buttons[x][y].setGravity(Gravity.CENTER);
                        }
                        //buttons[x][y].setTextSize(40);
                        buttons[x][y].setEnabled(true);
                    }

                    x = i - 1;
                    y = j;
                    while (x >= 0 && board[x][y] == secondball) {
                        x--;
                    }
                    if (x != i - 1 && x >= 0 && board[x][y] == EMPTY) {
                        board[x][y] = VALID_MOVE;
                        if(ball==BLACK){
                            buttons[x][y].setTextColor(ContextCompat.getColor(this, R.color.black));
                            buttons[x][y].setText("o");
                            //buttons[x][y].setGravity(Gravity.CENTER);
                        }
                        else{
                            buttons[x][y].setTextColor(ContextCompat.getColor(this, R.color.white));
                            buttons[x][y].setText("o");
                          //  buttons[x][y].setGravity(Gravity.CENTER);
                        }
                        //buttons[x][y].setTextSize(40);
                        buttons[x][y].setEnabled(true);
                    }

                    x = i - 1;
                    y = j - 1;
                    while (x >= 0 && y >= 0 && board[x][y] == secondball) {
                        x--;
                        y--;
                    }
                    if (x != i - 1 && y != j - 1 && x >= 0 && y >= 0 && board[x][y] == EMPTY) {
                        board[x][y] = VALID_MOVE;
                        if(ball==BLACK){
                            buttons[x][y].setTextColor(ContextCompat.getColor(this, R.color.black));
                            buttons[x][y].setText("o");
                           // buttons[x][y].setGravity(Gravity.CENTER);
                        }
                        else{
                            buttons[x][y].setTextColor(ContextCompat.getColor(this, R.color.white));
                            buttons[x][y].setText("o");
                         //   buttons[x][y].setGravity(Gravity.CENTER);
                        }
                        //buttons[x][y].setTextSize(40);
                        buttons[x][y].setEnabled(true);
                    }
                }
            }

        }


    }

}