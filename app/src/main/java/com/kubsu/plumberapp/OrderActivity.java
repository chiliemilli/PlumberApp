package com.kubsu.plumberapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class OrderActivity extends AppCompatActivity {


    private int seconds=0;
    private boolean running;
    private Button btnStart;
    private Button btnEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        if (savedInstanceState !=null){
            seconds=savedInstanceState.getInt("seconds");
            running=savedInstanceState.getBoolean("running");
        }
        runTimer();

        btnStart=(Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running=true;
                btnStart.setEnabled(false);
            }
        });

        btnEnd=(Button)findViewById(R.id.btnEnd);
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               running=false;

            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanseState) {
        super.onSaveInstanceState(savedInstanseState);
        savedInstanseState.putInt("seconds", seconds);
        savedInstanseState.putBoolean("running", running);

    }

    public void runTimer(){
        final TextView timeView=(TextView)findViewById(R.id.tvTime);
        final Handler handler =new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours=seconds/3600;
                int minutes=(seconds%3600)/60;
                int secs=seconds%60;
                String time=String.format(Locale.getDefault(),
                        "%d:%02d:%02d",hours,minutes,secs);
                timeView.setText(time);
                if (running){
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }

}

