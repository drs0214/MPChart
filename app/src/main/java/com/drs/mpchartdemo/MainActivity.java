package com.drs.mpchartdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_line,btn_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListen();
    }

    private void initListen() {
        btn_line.setOnClickListener(this);
        btn_bar.setOnClickListener(this);
    }

    private void initView() {
        btn_line=(Button) findViewById(R.id.btn_line);
        btn_bar=(Button) findViewById(R.id.btn_bar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_line:
                Intent intent=new Intent(MainActivity.this,LineChartActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_bar:
                Intent intent1=new Intent(MainActivity.this,BarChartActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
