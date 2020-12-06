package com.kubsu.plumberapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OrderActivity extends AppCompatActivity {


    private int seconds=0;
    private boolean running;
    private Button btnStart;
    private Button btnEnd;
    private TextView tvOrderInfo;
    private final VolleyRestAPIRequester volleyRestAPIRequester=new VolleyRestAPIRequester();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        tvOrderInfo = findViewById(R.id.tvOrderInfo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        //получаем данные о заказе
        Bundle arguments = getIntent().getExtras();
        assert arguments != null;
        final OrderInfoModel currentOrder = (OrderInfoModel) arguments.getSerializable(OrderInfoModel.class.getSimpleName());
        assert currentOrder != null;
        Toast.makeText(this, currentOrder.toString(), Toast.LENGTH_SHORT).show();
        tvOrderInfo.setText(currentOrder.toString());


        readOrder(currentOrder);

        //начать выполнение заказа
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnStart.setEnabled(false);
                Date dateNow=new Date();
                SimpleDateFormat formaterDate= new SimpleDateFormat("dd.MM.yy hh.mm.ss", Locale.US);
                currentOrder.setPlumberStatus("working");
                volleyRestAPIRequester.changeOrderStatus(OrderActivity.this, "start",formaterDate.format(dateNow), currentOrder.getOrderId(),currentOrder.getPlumberID());

            }
        });

        //закончить выполнения заказа
        btnEnd = (Button) findViewById(R.id.btnEnd);
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date dateNow=new Date();
                SimpleDateFormat formaterDate= new SimpleDateFormat("dd.MM.yy hh.mm.ss", Locale.US);
                currentOrder.setPlumberStatus("online");
                volleyRestAPIRequester.changeOrderStatus(OrderActivity.this, "end",formaterDate.format(dateNow), currentOrder.getOrderId(),currentOrder.getPlumberID());
                Intent intent=new Intent(OrderActivity.this,NoOrderActivity.class);
                intent.putExtra(OrderInfoModel.class.getSimpleName(), currentOrder);
                startActivity(intent);
                finish();
            }
        });
    }

   public  void readOrder(OrderInfoModel currentOrder){
       //читаем заказ
       Date dateNow=new Date();
       SimpleDateFormat formaterDate= new SimpleDateFormat("dd.MM.yy hh.mm.ss", Locale.US);
       volleyRestAPIRequester.changeOrderStatus(OrderActivity.this, "read",formaterDate.format(dateNow), currentOrder.getOrderId(),currentOrder.getPlumberID());

   }
}

