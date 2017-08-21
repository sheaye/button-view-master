package com.sheaye.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
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

    private int mRadius;
    protected ButtonShape mButtonShape;
    protected int mNormalSolidColor;
    protected int mPressedSolidColor;
    protected int mSelectedSolidColor;
    protected Drawable mNormalBackgroundDrawable;
    protected Drawable mPressedBackgroundDrawable;
    protected Drawable mSelectedBackgroundDrawable;
    protected int mNormalTextColor;
    protected int mPressedTextColor;

    public enum ButtonShape {
        RECTANGLE, CIRCLE, CIRCLE_RECT;

        private static ButtonShape getShape(int value) {
            switch (value) {
                case 2:
                    return ButtonShape.CIRCLE;
                case 3:
                    return ButtonShape.CIRCLE_RECT;
                default:
                    return ButtonShape.RECTANGLE;
            }
        }
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
        setClickable(true);
        setMinHeight(0);
        mContext = context;
        mResourcesHelper = new ResourcesHelper(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ButtonView, defStyleAttr, 0);

        setShape(ButtonShape.getShape(typedArray.getInt(R.styleable.ButtonView_shape, 1)));
        setRadius(typedArray.getDimensionPixelSize(R.styleable.ButtonView_cornerRadius, 0));

        int solidColorArrayId = typedArray.getResourceId(R.styleable.ButtonView_solidColorEntries, NO_ID);
        int[] solidColorResIds = mResourcesHelper.getResIdArray(solidColorArrayId);
        setSolidColors(solidColorResIds);

        int drawableArrayId = typedArray.getResourceId(R.styleable.ButtonView_drawableEntries, NO_ID);
        int[] drawableResIds = mResourcesHelper.getResIdArray(drawableArrayId);
        setBackgroundDrawables(drawableResIds);

        int textColorArrayId = typedArray.getResourceId(R.styleable.ButtonView_textColorEntries, NO_ID);
        int[] textColorResIds = mResourcesHelper.getResIdArray(textColorArrayId);
        setTextColors(textColorResIds);

        float strokeWidth = typedArray.getDimension(R.styleable.ButtonView_strokeWidth, 0);
        int strokeColor = typedArray.getColor(R.styleable.ButtonView_strokeColor, -1);

        typedArray.recycle();
        commit();
    }

    public ButtonView setShape(ButtonShape shape) {
        mButtonShape = shape;
        return this;
    }

    private Shape getShape(ButtonShape shape) {
        switch (shape) {
            case CIRCLE:
                return ShapeFactory.createCircle();
            case CIRCLE_RECT:
                return ShapeFactory.createCircleRect();
            default:
                return ShapeFactory.createRoundRect(mRadius);
        }
    }

    public ButtonView setRadius(int radius) {
        mRadius = radius;
        return this;
    }

    public ButtonView setNormalSolidColor(int color) {
        mNormalSolidColor = color;
        return this;
    }

    public ButtonView setPressedSolidColor(int color) {
        mPressedSolidColor = color;
        return this;
    }

    public ButtonView setSelectedSolidColor(int color) {
        mSelectedSolidColor = color;
        return this;
    }

    private void setSolidColors(@ColorRes int[] colors) {
        if (colors == null) {
            return;
        }
        if (colors.length > 3) {
            throw new IllegalArgumentException("solid 背景色(normal,pressed,selected)最多不能超过3个");
        }
        setNormalSolidColor(mResourcesHelper.getColor(colors[0]));
        if (colors.length > 1) {
            setPressedSolidColor(mResourcesHelper.getColor(colors[1]));
        }
        if (colors.length > 2) {
            setSelectedSolidColor(mResourcesHelper.getColor(colors[2]));
        }
    }

    private void setBackgroundDrawables(@DrawableRes int[] drawableResIds) {
        if (drawableResIds == null) {
            return;
        }
        if (drawableResIds.length > 3) {
            throw new IllegalArgumentException("solid 背景图(normal,pressed,selected)最多不能超过3个");
        }
        setNormalBackgroundDrawable(mResourcesHelper.geDrawable(drawableResIds[0]));
        if (drawableResIds.length > 1) {
            setPressedBackgroundDrawable(mResourcesHelper.geDrawable(drawableResIds[1]));
        }
        if (drawableResIds.length > 2) {
            setSelectedBackgroundDrawable(mResourcesHelper.geDrawable(drawableResIds[2]));
        }
    }

    private void setTextColors(@ColorRes int[] textColorResIds) {
        if (textColorResIds == null) {
            return;
        }
        int normal, pressed = 0, selected = 0;
        if (textColorResIds.length > 3) {
            throw new IllegalArgumentException("solid 背景图(normal,pressed,selected)最多不能超过3个");
        }
        normal = mResourcesHelper.getColor(textColorResIds[0]);
        if (textColorResIds.length > 1) {
            pressed = mResourcesHelper.getColor(textColorResIds[1]);
        }
        if (textColorResIds.length > 2) {
            selected = mResourcesHelper.getColor(textColorResIds[2]);
        }
        setTextColors(normal, pressed, selected);
    }

    public ButtonView setTextColors(int normal, int pressed, int selected) {
        setTextColor(SelectorFactory.createColorSelector(normal, pressed, selected));
        return this;
    }

    public ButtonView setNormalBackgroundDrawable(Drawable drawable) {
        if (mNormalBackgroundDrawable != drawable) {
            mNormalBackgroundDrawable = drawable;
        }
        return this;
    }

    public ButtonView setPressedBackgroundDrawable(Drawable drawable) {
        if (mPressedBackgroundDrawable != drawable) {
            mPressedBackgroundDrawable = drawable;
        }
        return this;
    }

    public ButtonView setSelectedBackgroundDrawable(Drawable drawable) {
        if (mSelectedBackgroundDrawable != drawable) {
            mSelectedBackgroundDrawable = drawable;
        }
        return this;
    }

    public void commit() {
        Shape shape = getShape(mButtonShape);
        if (mNormalBackgroundDrawable == null) {
            mNormalBackgroundDrawable = DrawableUtil.createDrawable(shape, mNormalSolidColor);
        }
        if (mPressedBackgroundDrawable == null) {
            mPressedBackgroundDrawable = DrawableUtil.createDrawable(shape, mPressedSolidColor);
        }
        if (mSelectedBackgroundDrawable == null) {
            mSelectedBackgroundDrawable = DrawableUtil.createDrawable(shape, mSelectedSolidColor);
        }
        StateListDrawable selector = SelectorFactory.createDrawableSelector(mNormalBackgroundDrawable, mPressedBackgroundDrawable, mSelectedBackgroundDrawable);
        ViewCompat.setBackground(this, selector);
    }


}
