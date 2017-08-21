package com.sheaye.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.ColorRes;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.sheaye.util.DrawableUtil;
import com.sheaye.util.ResourcesHelper;
import com.sheaye.util.SelectorFactory;
import com.sheaye.util.ShapeFactory;

/**
 * Created by yexinyan on 2017/8/20.
 */

public class ButtonView extends AppCompatButton {

    protected int mRadius;

    public enum ButtonShape {
        RECTANGLE, CIRCLE, ROUND_RECT, CIRCLE_RECT;
    }

    private Context mContext;
    protected ResourcesHelper mResourcesHelper;

    public ButtonView(Context context) {
        this(context, null);
    }

    public ButtonView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        ViewCompat.setBackground(this,null);
        setMinHeight(0);
        mContext = context;
        mResourcesHelper = new ResourcesHelper(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ButtonView, defStyleAttr, 0);
        int colorEntriesId = typedArray.getResourceId(R.styleable.ButtonView_backgroundColorEntries, NO_ID);
        int shapeValue = typedArray.getInt(R.styleable.ButtonView_shape, 1);
        mRadius = typedArray.getDimensionPixelSize(R.styleable.ButtonView_cornerRadius, 15);
        int drawableEntriesId = typedArray.getResourceId(R.styleable.ButtonView_drawableEntries, NO_ID);
        float strokeWidth = typedArray.getDimension(R.styleable.ButtonView_strokeWidth, 0);
        int strokeColor = typedArray.getColor(R.styleable.ButtonView_strokeColor, -1);
        typedArray.recycle();
        int[] backgroundColors = mResourcesHelper.getResourceIdArray(colorEntriesId);
        ButtonShape buttonShape = getButtonShape(shapeValue);
//        setBackground(buttonShape, backgroundColors);
        setBackground();
    }

    private ButtonShape getButtonShape(int shapeValue) {
        switch (shapeValue) {
            case 2:
                return ButtonShape.CIRCLE;
            case 3:
                return ButtonShape.ROUND_RECT;
            case 4:
                return ButtonShape.CIRCLE_RECT;
            default:
                return ButtonShape.RECTANGLE;
        }
    }

    private Shape getShape(ButtonShape shape) {
        switch (shape) {
            case CIRCLE:
                return ShapeFactory.createCircle();
            case ROUND_RECT:
                return ShapeFactory.createRoundRect(mRadius);
            case CIRCLE_RECT:
                return ShapeFactory.createCircleRect();
            default:
                return ShapeFactory.createRoundRect(0);
        }
    }

    public void setBackground(ButtonShape shape, @ColorRes int... colors) {
        if (colors == null || colors.length == 0 || colors.length > 3) {
            throw new IllegalArgumentException("颜色指数量必须在1到3之间：normal,pressed,selected");
        }
        Drawable[] drawables = new Drawable[colors.length];
        for (int i = 0; i < colors.length; i++) {
            drawables[i] = DrawableUtil.createDrawable(getShape(shape), mResourcesHelper.getColor(colors[i]));
        }
        ViewCompat.setBackground(this, SelectorFactory.createDrawableSelector(drawables));
    }

    public void setBackground(){
        int[][] states = new int[4][];
        states[0] = new int[] { android.R.attr.state_enabled, android.R.attr.state_pressed };
        states[1] = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };
        states[3] = new int[] { -android.R.attr.state_enabled};
        states[2] = new int[] { android.R.attr.state_enabled };

        StateListDrawable background = new StateListDrawable();
        background.addState(states[0], DrawableUtil.createDrawable(ShapeFactory.createCircleRect(), Color.BLUE));
        background.addState(states[1], DrawableUtil.createDrawable(ShapeFactory.createCircleRect(), Color.BLUE));
        background.addState(states[3], DrawableUtil.createDrawable(ShapeFactory.createCircleRect(), Color.RED));
        background.addState(states[2], DrawableUtil.createDrawable(ShapeFactory.createCircleRect(), Color.YELLOW));
        setBackgroundDrawable(background);
    }

}
