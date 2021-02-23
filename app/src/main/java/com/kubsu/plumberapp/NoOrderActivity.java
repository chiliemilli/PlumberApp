package com.kubsu.plumberapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class NoOrderActivity extends AppCompatActivity implements Runnable {

    private Toolbar toolbar;
    private final VolleyRestAPIRequester volleyRestAPIRequester=new VolleyRestAPIRequester();
    private PlumberDataModel plumberDataModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_order);
        Toolbar toolbar =  findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        Bundle arguments = getIntent().getExtras();
        assert arguments != null;
        plumberDataModel = (PlumberDataModel) arguments.getSerializable(PlumberDataModel.class.getSimpleName());


        Timer timer=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {

                String[] data = FileHolder.getLoginPasswordFromFile(NoOrderActivity.this);
                final String username=data[0];
                final String password=data[1];
                volleyRestAPIRequester.getOrderInfoByLogin(NoOrderActivity.this,username,password, new VolleyRestAPIRequester.OrderInfoCallback() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(NoOrderActivity.this, "Error click", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(OrderInfoModel orderInfoModel, PlumberDataModel plumberDataModel) {

                        FileHolder.writeLoginPasswordToFile(NoOrderActivity.this, username,password);
                        if (orderInfoModel.isOrderStatus()){

                            Intent intent=new Intent(NoOrderActivity.this,OrderActivity.class);

                            Bundle extras=new Bundle();
                            extras.putSerializable(OrderInfoModel.class.getSimpleName(),orderInfoModel);
                            extras.putSerializable(PlumberDataModel.class.getSimpleName(), plumberDataModel);
                            intent.putExtras(extras);

                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask,0,10000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_exit,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                plumberDataModel.setPlumberStatus("online");
                FileHolder.clearFile(this);
                volleyRestAPIRequester.exitPlumber(this,(int) plumberDataModel.getPlumberID(), (String) plumberDataModel.getPlumberStatus());
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
            return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void run() {

    }
}