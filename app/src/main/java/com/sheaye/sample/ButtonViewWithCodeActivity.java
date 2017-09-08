package com.sheaye.sample;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sheaye.util.ResourcesHelper;
import com.sheaye.util.SelectorFactory;
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

        /*ButtonView.ShapeSelector selector = new ButtonView.ShapeSelector(ButtonShape.CIRCLE_RECT)
                .setSolidColors(mResourceHelper.getColorArray(R.array.solidColors))
                .setStrokeColors(mResourceHelper.getColorArray(R.array.strokeColors))
                .setStrokeWidth(5);*/

        /*mButtonWithShape
                .setTextColors(mResourceHelper.getColorArray(R.array.textColors))
                .setBackgroundSelector(selector);*/

        mButtonWithShape
                .setStrokeColor(mResourceHelper.getColorArray(R.array.strokeColors))
                .setStrokeWidth(5)
                .setBackgroundShape(ButtonView.BackgroundShape.CIRCLE_RECT, mResourceHelper.getColorArray(R.array.solidColors));

        mButtonWithDrawables.setTextColor(SelectorFactory.createColorSelector(mResourceHelper.getColorArray(R.array.textColors)));
        ViewCompat.setBackground(mButtonWithDrawables,
                SelectorFactory.createDrawableSelector(mResourceHelper.getDrawableArray(R.array.drawables)));

        mButtonWithCompoundDrawable.setCompoundDrawable(
                SelectorFactory.createDrawableSelector(
                        mResourceHelper.getDrawableArray(R.array.drawables)));


    }

    @OnClick({R.id.with_shape, R.id.with_drawables, R.id.with_compound_drawable})
    public void onClick(View view) {
        view.setSelected(!view.isSelected());
    }


}
