package com.kubsu.plumberapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin, btnTest;
    private TextView loginLocked;
    private TextView attempts;
    private TextView numberOfAttempts;
    int numberOfRemainingLoginAttempts = 3;

    String url="https://localhost:8080/Plumbers?task=auth&mode=1login=admin&password=admin";
    //String url1="https://www.metaweather.com/api/location/search/?query=london";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.edtEnterLogin);
        etPassword = (EditText) findViewById(R.id.edtEnterPassword);
        btnLogin = (Button) findViewById(R.id.btnEnter);
        btnTest=(Button) findViewById(R.id.btnTest);


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
                    public void onResponse(OrderInfoModel orderInfoModel) {

                        Intent intent=new Intent(LoginActivity.this,OrderActivity.class);
                        intent.putExtra(OrderInfoModel.class.getSimpleName(),orderInfoModel);
                        startActivity(intent);
                        finish();
                    }
                });

            }
        });

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restAPIRequester.registrationRequest(LoginActivity.this,"diana","diana");
            }
        });
    }



    // Обрабатываем нажатие кнопки "Войти":


}