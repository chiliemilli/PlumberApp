package com.kubsu.plumberapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class NoOrderActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private final VolleyRestAPIRequester volleyRestAPIRequester=new VolleyRestAPIRequester();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_order);
        Toolbar toolbar =  findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        Bundle arguments = getIntent().getExtras();
        assert arguments != null;
        final OrderInfoModel currentOrder = (OrderInfoModel) arguments.getSerializable(OrderInfoModel.class.getSimpleName());
        assert currentOrder != null;

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
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
            return super.onOptionsItemSelected(item);
        }
    }
}