package com.hm.jsondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_fastjson:
                FastJsonActivity.launch(this);
                break;
            case R.id.btn_gson:
                break;
            case R.id.btn_jackjson:
                break;
            default:
                break;
        }
    }
}
