package com.sheaye.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import com.sheaye.util.ResourcesHelper;
import com.sheaye.widget.ButtonShape;
import com.sheaye.widget.ButtonView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ButtonViewWithCodeActivity extends AppCompatActivity {

    @BindView(R.id.with_shape)
    ButtonView mButtonWithShape;
    @BindView(R.id.with_drawables)
    ButtonView mButtonWithDrawables;
    @BindView(R.id.with_compound_drawable)
    ButtonView mButtonWithCompoundDrawable;
    private ResourcesHelper mResourceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_view_with_code);
        mResourceHelper = new ResourcesHelper(this);
        ButterKnife.bind(this);

        ButtonView.ShapeSelector selector = new ButtonView.ShapeSelector(ButtonShape.CIRCLE_RECT)
                .setSolidColors(mResourceHelper.getColorsFromArray(R.array.solidColors));

        mButtonWithShape
                .setTextColors(mResourceHelper.getColorsFromArray(R.array.textColors))
                .setBackgroundSelector(selector);

        mButtonWithDrawables
                .setTextColors(mResourceHelper.getColorsFromArray(R.array.textColors))
                .setBackgroundSelector(mResourceHelper.getDrawablesFromArray(R.array.drawables));

        mButtonWithCompoundDrawable
                .setTextColors(mResourceHelper.getColorsFromArray(R.array.textColorForCompound))
                .setCompoundIcons(Gravity.TOP,10,mResourceHelper.getDrawablesFromArray(R.array.drawables));
    }

    @OnClick({R.id.with_shape,R.id.with_drawables,R.id.with_compound_drawable})
    public void onClick(View view) {
        view.setSelected(!view.isSelected());
    }


}
