package com.sheaye.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sheaye.util.ResourcesHelper;
import com.sheaye.widget.ButtonView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClick(View view) {
        switch (view.getId()){
            case R.id.with_xml:
                startActivity(new Intent(this,ButtonViewWithXMLActivity.class));
                break;
            case R.id.with_code:
                startActivity(new Intent(this,ButtonViewWithCodeActivity.class));
                break;
        }
    }
}
