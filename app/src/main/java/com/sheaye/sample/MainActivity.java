package com.sheaye.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sheaye.util.DrawableUtil;
import com.sheaye.util.SelectorFactory;
import com.sheaye.util.ShapeFactory;

public class MainActivity extends AppCompatActivity {

    protected View mButton2;
    protected View mButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        ViewCompat.setBackground(mButton2,
                SelectorFactory.createDrawableSelector(
                        DrawableUtil.createDrawable(ShapeFactory.createRoundRect(15), Color.BLUE),
                        DrawableUtil.createDrawable(ShapeFactory.createRoundRect(15), Color.RED),
                        DrawableUtil.createDrawable(ShapeFactory.createRoundRect(15), Color.YELLOW)
                ));
        mButton1.setSelected(true);
        mButton2.setSelected(true);

    }
}
