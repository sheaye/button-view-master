package com.sheaye.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sheaye.widget.ButtonView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButtonView buttonView = new ButtonView(this);
    }


    public void onClick(View view) {
        view.setSelected(!view.isSelected());
    }
}
