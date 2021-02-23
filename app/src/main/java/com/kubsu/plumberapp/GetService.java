package com.kubsu.plumberapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class GetService extends Service {
    VolleyRestAPIRequester restAPIRequester;
    Context context;

    public GetService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        postQuery();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Служба запущена",
                Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    public void postQuery(){
        final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                restAPIRequester.getOrderInfoByLogin(context, LoginActivity.etUsername.getText().toString(), LoginActivity.etPassword.getText().toString(), new VolleyRestAPIRequester.OrderInfoCallback() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(context, "Error click", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(OrderInfoModel orderInfoModel, PlumberDataModel plumberDataModel) {

                        FileHolder.writeLoginPasswordToFile(context, LoginActivity.etUsername.getText().toString(),LoginActivity.etPassword.getText().toString());
                        if (orderInfoModel.isOrderStatus()){

                            Intent intent=new Intent(context,OrderActivity.class);

                            Bundle extras=new Bundle();
                            extras.putSerializable(OrderInfoModel.class.getSimpleName(),orderInfoModel);
                            extras.putSerializable(PlumberDataModel.class.getSimpleName(), plumberDataModel);
                            intent.putExtras(extras);

                            //startActivity(intent);
                        }else{
                            Intent intent=new Intent(context,NoOrderActivity.class);

                            intent.putExtra(PlumberDataModel.class.getSimpleName(), plumberDataModel);
                            //startActivity(intent);
                        }
                    }
                });
               handler.postDelayed(this,10000);
            }
        });
    }
}
