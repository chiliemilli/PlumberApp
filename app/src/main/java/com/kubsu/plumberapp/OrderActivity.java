package com.kubsu.plumberapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class OrderActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private int seconds=0;
    private boolean running;
    private Button btnStart;
    private Button btnEnd;
    TextView tvOrderInfo;
    TextView  tvTime, tvTimeText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        tvTime = (TextView) findViewById(R.id.tvTime);
        tvTimeText = (TextView) findViewById(R.id.tvTimeText);
        tvOrderInfo = findViewById(R.id.tvOrderInfo);
        toolbar = findViewById(R.id.my_toolbar);

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
        }
        runTimer();


        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = true;
                btnStart.setEnabled(false);

            }
        });

        btnEnd = (Button) findViewById(R.id.btnEnd);
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = false;


            }
        });

        Bundle arguments = getIntent().getExtras();
        final OrderInfoModel currentOrder;
        if(arguments!=null){
            currentOrder = (OrderInfoModel) arguments.getSerializable(OrderInfoModel.class.getSimpleName());
            tvOrderInfo.setText(currentOrder.toString());
        }

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

