package com.kubsu.plumberapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
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
   private OrderInfoModel currentOrder;
    private final VolleyRestAPIRequester volleyRestAPIRequester=new VolleyRestAPIRequester();
    private PlumberDataModel plumber;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);


//        String[] loginDetails=FileHolder.getLoginPasswordFromFile(this);
//        login=loginDetails[0];
//        password=loginDetails[1];

        tvOrderInfo = findViewById(R.id.tvOrderInfo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState==null) {
            //получаем данные о заказе
            Bundle arguments = getIntent().getExtras();
            assert arguments != null;
            //final OrderInfoModel
            currentOrder = (OrderInfoModel) arguments.getSerializable(OrderInfoModel.class.getSimpleName());
            plumber=(PlumberDataModel) arguments.getSerializable((PlumberDataModel.class.getSimpleName()));
            assert currentOrder != null;
            Toast.makeText(this, currentOrder.toString(), Toast.LENGTH_SHORT).show();
            tvOrderInfo.setText(currentOrder.toString());
        }
        else{
            currentOrder=(OrderInfoModel)savedInstanceState.getSerializable("currentOrder");
            plumber=(PlumberDataModel)savedInstanceState.getSerializable("plumber");
        }

        //прочитать заказ
        readOrder(currentOrder);


        //начать выполнение заказа
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnStart.setEnabled(false);
                Date dateNow=new Date();
                SimpleDateFormat formaterDate= new SimpleDateFormat("dd.MM.yy hh.mm.ss", Locale.US);
                plumber.setPlumberStatus("working");
                volleyRestAPIRequester.changeOrderStatus(OrderActivity.this, "start",formaterDate.format(dateNow), currentOrder.getOrderId(),plumber.getPlumberID());

            }
        });

        //закончить выполнения заказа
        btnEnd = (Button) findViewById(R.id.btnEnd);
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date dateNow=new Date();
                SimpleDateFormat formaterDate= new SimpleDateFormat("dd.MM.yy hh.mm.ss", Locale.US);
                plumber.setPlumberStatus("online");
                volleyRestAPIRequester.changeOrderStatus(OrderActivity.this, "end",formaterDate.format(dateNow), currentOrder.getOrderId(),plumber.getPlumberID());
                Intent intent=new Intent(OrderActivity.this,NoOrderActivity.class);
                Bundle extras=new Bundle();
                extras.putSerializable(OrderInfoModel.class.getSimpleName(),currentOrder);
                extras.putSerializable(PlumberDataModel.class.getSimpleName(), plumber);
                intent.putExtras(extras);
                startActivity(intent);
                finish();
            }
        });
    }

   public  void readOrder(OrderInfoModel currentOrder){
       //читаем заказ
       Date dateNow=new Date();
       SimpleDateFormat formaterDate= new SimpleDateFormat("dd.MM.yy hh.mm.ss", Locale.US);
       volleyRestAPIRequester.changeOrderStatus(OrderActivity.this, "read",formaterDate.format(dateNow), currentOrder.getOrderId(),plumber.getPlumberID());

   }

    //сохранение состояния
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

        super.onSaveInstanceState(outState, outPersistentState);
        outState.putSerializable("current_order", currentOrder);
        outState.putSerializable("plumber", plumber);
    }




}

