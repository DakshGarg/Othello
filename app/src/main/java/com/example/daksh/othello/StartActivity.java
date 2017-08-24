package com.example.daksh.othello;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.button;

public class StartActivity extends AppCompatActivity {


    EditText editText1;
    EditText editText2;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);
        //setTitle("Start");
        Button button=(Button)findViewById(R.id.button);
        editText1=(EditText)findViewById(R.id.edittext1);
        editText2=(EditText)findViewById(R.id.edittext2);
        textView=(TextView)findViewById(R.id.textView);
        textView.setGravity(Gravity.CENTER);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextSize(15);

        SharedPreferences sharedPreferences=getSharedPreferences("Othello",MODE_PRIVATE);
        String player1name=sharedPreferences.getString("player1name",null);
        String player2name =sharedPreferences.getString("player2name",null);

        if(player1name==null)
        {
            textView.setText("N/A");
        }
        else{
            textView.setText(player1name);
        }
        if(player2name==null)
        {
            textView.append("\nN/A");
        }
        else{
            textView.append("\n"+player2name);
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1=editText1.getText().toString();
                String name2=editText2.getText().toString();
                if(name1.isEmpty()&&name2.isEmpty())
                {
                    Toast.makeText(StartActivity.this,"Enter players name",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(name1.isEmpty()){
                    Toast.makeText(StartActivity.this,"Enter player1 name",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(name2.isEmpty()){
                    Toast.makeText(StartActivity.this,"Enter player2 name",Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreferences sharedPreferences= getSharedPreferences("Othello",MODE_PRIVATE);

                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("player1name",name1);
                editor.putString("player2name",name2);
                editor.commit();

                   Bundle bundle=new Bundle();
                bundle.putString("player1name",name1);
                bundle.putString("player2name",name2);

                Intent i=new Intent(StartActivity.this,MainActivity.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }
}
