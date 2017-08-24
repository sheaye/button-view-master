package com.sheaye.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sheaye.widget.ButtonShape;
import com.sheaye.widget.ButtonView;

public class ButtonViewWithCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_view_with_code);
        ButtonView buttonView = new ButtonView(this);

    }
}
