package com.kubsu.plumberapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {


    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private  final VolleyRestAPIRequester restAPIRequester=new VolleyRestAPIRequester();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.edtEnterLogin);
        etPassword = (EditText) findViewById(R.id.edtEnterPassword);
        btnLogin = (Button) findViewById(R.id.btnEnter);


        final VolleyRestAPIRequester restAPIRequester=new VolleyRestAPIRequester();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                restAPIRequester.getOrderInfoByLogin(LoginActivity.this, etUsername.getText().toString(), etPassword.getText().toString(), new VolleyRestAPIRequester.OrderInfoCallback() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(LoginActivity.this, "Error click", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(OrderInfoModel orderInfoModel, PlumberDataModel plumberDataModel) {

                        FileHolder.writeLoginPasswordToFile(LoginActivity.this, etUsername.getText().toString(),etPassword.getText().toString());
                        if (orderInfoModel.isOrderStatus()){

                            Intent intent=new Intent(LoginActivity.this,OrderActivity.class);

                            Bundle extras=new Bundle();
                            extras.putSerializable(OrderInfoModel.class.getSimpleName(),orderInfoModel);
                            extras.putSerializable(PlumberDataModel.class.getSimpleName(), plumberDataModel);
                            intent.putExtras(extras);

                            startActivity(intent);
                            finish();
                        }else{
                            Intent intent=new Intent(LoginActivity.this,NoOrderActivity.class);

                            intent.putExtra(PlumberDataModel.class.getSimpleName(), plumberDataModel);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            }
        });


    }

    public void onLoginClick(){

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (FileHolder.emptyFile(this)==false) {
                String[] data = FileHolder.getLoginPasswordFromFile(this);
                etUsername.setText(data[0]);
                etPassword.setText(data[1]);
                restAPIRequester.getOrderInfoByLogin(LoginActivity.this, etUsername.getText().toString(), etPassword.getText().toString(), new VolleyRestAPIRequester.OrderInfoCallback() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(LoginActivity.this, "Error click", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(OrderInfoModel orderInfoModel, PlumberDataModel plumberDataModel) {

                        FileHolder.writeLoginPasswordToFile(LoginActivity.this, etUsername.getText().toString(), etPassword.getText().toString());
                        if (orderInfoModel.isOrderStatus()) {

                            Intent intent = new Intent(LoginActivity.this, OrderActivity.class);
                            Bundle extras=new Bundle();
                            extras.putSerializable(OrderInfoModel.class.getSimpleName(),orderInfoModel);
                            extras.putSerializable(PlumberDataModel.class.getSimpleName(), plumberDataModel);
                            intent.putExtras(extras);

                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(LoginActivity.this, NoOrderActivity.class);
                            intent.putExtra(PlumberDataModel.class.getSimpleName(), plumberDataModel);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
            else{
                super.onResume();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}