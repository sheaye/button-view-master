package com.sheaye.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.sheaye.widget.ButtonShape;
import com.sheaye.widget.ButtonView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ButtonViewWithCodeActivity extends AppCompatActivity {

    @BindView(R.id.shape_spinner)
    Spinner mShapeSpinner;
    @BindView(R.id.button_view)
    ButtonView mButtonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_view_with_code);
        ButterKnife.bind(this);
        mShapeSpinner.setOnItemSelectedListener(new ShapeSelectListener());
    }

    @OnClick({})
    public void onClick(View view) {

    }

    class ShapeSelectListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    mButtonView.setShape(ButtonShape.RECTANGLE).commit();
                    break;
                case 1:
                    mButtonView.setShape(ButtonShape.CIRCLE_RECT).commit();
                    break;
                case 2:
                    mButtonView.setShape(ButtonShape.CIRCLE).commit();
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
