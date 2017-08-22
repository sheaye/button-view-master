package com.sheaye.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.sheaye.util.ResourcesHelper;
import com.sheaye.util.SelectorFactory;
import com.sheaye.util.ShapeDrawableFactory;

/**
 * Created by yexinyan on 2017/8/20.
 */

public class ButtonView extends AppCompatButton {

    static final int SHAPE_RECTANGLE = 1;
    static final int SHAPE_CIRCLE = 2;
    static final int SHAPE_CIRCLE_RECT = 3;

    private int mRadius;
    private int mNormalSolidColor;
    private int mPressedSolidColor;
    private int mSelectedSolidColor;
    private Drawable mNormalBackgroundDrawable;
    private Drawable mPressedBackgroundDrawable;
    private Drawable mSelectedBackgroundDrawable;
    private int mStrokeWidth;
    private int mNormalStrokeColor;
    private int mPressedStrokeColor;
    private int mSelectedStrokeColor;
    private int mBackgroundShape;

    protected ResourcesHelper mResourcesHelper;

    public ButtonView(Context context) {
        this(context, null);
    }

    public ButtonView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
        setMinHeight(0);
        mResourcesHelper = new ResourcesHelper(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ButtonView, defStyleAttr, 0);

        mBackgroundShape = typedArray.getInt(R.styleable.ButtonView_shape, 1);
        setRadius(typedArray.getDimensionPixelSize(R.styleable.ButtonView_cornerRadius, 0));

        int solidColorArrayId = typedArray.getResourceId(R.styleable.ButtonView_solidColorEntries, NO_ID);
        setSolidColorEntries(solidColorArrayId);

        int drawableArrayId = typedArray.getResourceId(R.styleable.ButtonView_drawableEntries, NO_ID);
        setDrawableEntries(drawableArrayId);

        int textColorArrayId = typedArray.getResourceId(R.styleable.ButtonView_textColorEntries, NO_ID);
        setTextColorEntries(textColorArrayId);

        mStrokeWidth = (int) typedArray.getDimension(R.styleable.ButtonView_strokeWidth, 0);
        if (mStrokeWidth != 0) {
            int strokeColorArrayId = typedArray.getResourceId(R.styleable.ButtonView_strokeColorEntries, NO_ID);
            setStrokeColorEntries(strokeColorArrayId);
        }

        typedArray.recycle();
        commit();
    }

    private void setSolidColorEntries(int solidColorArrayId) {
        if (solidColorArrayId != NO_ID) {
            int[] solidColorResIds = mResourcesHelper.getResIdArray(solidColorArrayId, 3);
            setSolidColors(mResourcesHelper.getColor(solidColorResIds[0]),
                    mResourcesHelper.getColor(solidColorResIds[1]), mResourcesHelper.getColor(solidColorResIds[2]));
        }
    }

    private void setDrawableEntries(int drawableArrayId) {
        if (drawableArrayId != NO_ID) {
            int[] drawableResIds = mResourcesHelper.getResIdArray(drawableArrayId, 3);
            setBackgroundDrawables(mResourcesHelper.getDrawable(drawableResIds[0]),
                    mResourcesHelper.getDrawable(drawableResIds[1]),
                    mResourcesHelper.getDrawable(drawableResIds[2]));
        }
    }

    private void setTextColorEntries(int textColorArrayId) {
        if (textColorArrayId != NO_ID) {
            int[] textColorResIds = mResourcesHelper.getResIdArray(textColorArrayId, 3);
            setTextColors(mResourcesHelper.getColor(textColorResIds[0]),
                    mResourcesHelper.getColor(textColorResIds[1]), mResourcesHelper.getColor(textColorResIds[2]));
        }
    }

    private void setStrokeColorEntries(int strokeColorArrayId) {
        if (strokeColorArrayId != NO_ID) {
            int[] strokeColorsResIds = mResourcesHelper.getResIdArray(strokeColorArrayId, 3);
            setStrokeColors(mResourcesHelper.getColor(strokeColorsResIds[0])
                    , mResourcesHelper.getColor(strokeColorsResIds[1]), mResourcesHelper.getColor(strokeColorsResIds[2]));
        }
    }

    public ButtonView setStrokeColors(int normal, int pressed, int selected) {
        mNormalStrokeColor = normal;
        mPressedStrokeColor = pressed;
        mSelectedStrokeColor = selected;
        return this;
    }


    public ButtonView setShape(ButtonShape shape) {
        mBackgroundShape = shape.getValue();
        return this;
    }

    private ShapeDrawable getShape(int strokeColor, int solidColor) {
        switch (mBackgroundShape) {
            case SHAPE_CIRCLE:
                return ShapeDrawableFactory.createCircle(solidColor, mStrokeWidth, strokeColor);
            case SHAPE_CIRCLE_RECT:
                return ShapeDrawableFactory.createCircleRect(solidColor, mStrokeWidth, strokeColor);
            default:
                return ShapeDrawableFactory.createRoundRect(mRadius, solidColor, mStrokeWidth, strokeColor);
        }
    }

    public ButtonView setRadius(int radius) {
        mRadius = radius;
        return this;
    }

    public ButtonView setSolidColors(int normal, int pressed, int selected) {
        mNormalSolidColor = normal;
        mPressedSolidColor = pressed;
        mSelectedSolidColor = selected;
        return this;
    }

    public ButtonView setTextColors(int normal, int pressed, int selected) {
        setTextColor(SelectorFactory.createColorSelector(normal, pressed, selected));
        return this;
    }

    public ButtonView setBackgroundDrawables(Drawable normal, Drawable pressed, Drawable selected) {
        mNormalBackgroundDrawable = normal;
        mPressedBackgroundDrawable = pressed;
        mSelectedBackgroundDrawable = selected;
        return this;
    }

    public void commit() {
        if (mNormalBackgroundDrawable == null) {
            mNormalBackgroundDrawable = getShape(mNormalStrokeColor, mNormalSolidColor);
        }
        if (mPressedBackgroundDrawable == null) {
            mPressedBackgroundDrawable = getShape(mPressedStrokeColor, mPressedSolidColor);
        }
        if (mSelectedBackgroundDrawable == null) {
            mSelectedBackgroundDrawable = getShape(mSelectedStrokeColor, mSelectedSolidColor);
        }
        StateListDrawable selector = SelectorFactory.createDrawableSelector(mNormalBackgroundDrawable, mPressedBackgroundDrawable, mSelectedBackgroundDrawable);
        ViewCompat.setBackground(this, selector);
    }

}
