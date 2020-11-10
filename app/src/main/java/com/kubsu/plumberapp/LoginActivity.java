package com.kubsu.plumberapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    private EditText username;
    private EditText password;
    private Button login;
    private TextView loginLocked;
    private TextView attempts;
    private TextView numberOfAttempts;
    int numberOfRemainingLoginAttempts = 3;
    ProgressDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.edtEnterLogin);
        password = (EditText) findViewById(R.id.edtEnterPassword);
        login = (Button) findViewById(R.id.btnEnter);
        loginLocked = (TextView) findViewById(R.id.tvLoginIsLocked);
        attempts = (TextView) findViewById(R.id.tvAttempts);
        numberOfAttempts = (TextView) findViewById(R.id.tvNumberOfAttempts);
        numberOfAttempts.setText(Integer.toString(numberOfRemainingLoginAttempts));

        loadingDialog=new ProgressDialog(this);

        login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Login(v);
             }
         });     ;
    }

    // Обрабатываем нажатие кнопки "Войти":
    public void Login(View view) {

        loadingDialog.setMessage("Пожалуйста, подождите");
        loadingDialog.show();

        // Если введенные логин и пароль будут словом "admin",
        // показываем Toast сообщение об успешном входе:
        if (username.getText().toString().equals("admin") &&
                password.getText().toString().equals("admin")) {

            Toast.makeText(getApplicationContext(), "Вход выполнен!",Toast.LENGTH_SHORT).show();

            // Выполняем переход на другой экран:
            Intent intent = new Intent(LoginActivity.this,OrderActivity.class);
            startActivity(intent);
            loadingDialog.dismiss();
        }

        // В другом случае выдаем сообщение с ошибкой:
        else {
            Toast.makeText(getApplicationContext(), "Неправильные данные!",Toast.LENGTH_SHORT).show();
            numberOfRemainingLoginAttempts--;

            // Делаем видимыми текстовые поля, указывающие на количество оставшихся попыток:
            attempts.setVisibility(View.VISIBLE);
            numberOfAttempts.setVisibility(View.VISIBLE);
            numberOfAttempts.setText(Integer.toString(numberOfRemainingLoginAttempts));

            // Когда выполнено 3 безуспешных попытки залогиниться,
            // делаем видимым текстовое поле с надписью, что все пропало и выставляем
            // кнопке настройку невозможности нажатия setEnabled(false):
            if (numberOfRemainingLoginAttempts == 0) {
                login.setEnabled(false);
                loginLocked.setVisibility(View.VISIBLE);
                loginLocked.setBackgroundColor(Color.RED);
                loginLocked.setText("Вход заблокирован!!!");
            }
            loadingDialog.dismiss();
        }
    }
}