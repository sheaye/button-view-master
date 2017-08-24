package com.sheaye.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import com.sheaye.util.ResourcesHelper;
import com.sheaye.widget.ButtonShape;
import com.sheaye.widget.ButtonView;

public class MainActivity extends AppCompatActivity {

    protected ButtonView mButton1;
    protected ResourcesHelper mResourceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton1 = ((ButtonView) findViewById(R.id.button1));
        mResourceHelper = new ResourcesHelper(this);
        mButton1.setShape(ButtonShape.CIRCLE_RECT)
                .setStrokeColor(Color.BLUE, Color.DKGRAY, Color.GREEN)
                .setStrokeWidth(15)
                .setTextColors(Color.BLUE, Color.DKGRAY, Color.GREEN)
                .setSolidColor(Color.WHITE, Color.YELLOW, Color.RED)
                .setCompoundIcons(R.drawable.ic_normal, R.drawable.ic_pressed, R.drawable.ic_selected, Gravity.RIGHT)
                .setCompoundPadding(30)
                .commit();
    }


    public void onClick(View view) {
        view.setSelected(!view.isSelected());
    }
}
