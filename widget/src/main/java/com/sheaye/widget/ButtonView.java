package com.sheaye.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatButton;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.Gravity;

import com.sheaye.util.ResourcesHelper;
import com.sheaye.util.SelectorFactory;
import com.sheaye.util.ShapeDrawableFactory;

/**
 * Created by Yun on 2017/9/7.
 */

public class ButtonView extends AppCompatButton {

    private int mCompoundDrawableWidth;
    private int mCompoundDrawableHeight;
    private int mCompoundDrawableGravity;
    private ResourcesHelper mResourceHelper;
    private BackgroundShape mBackgroundShape;
    private int mCornerRadius;
    private int[] mSolidColor;
    private int mStrokeWidth = 2;
    private int[] mStrokeColor;

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
        mResourceHelper = new ResourcesHelper(context);
        Drawable compoundDrawable = null;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ButtonView, defStyleAttr, 0);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.ButtonView_shape) {
                int shapeFlag = typedArray.getInt(attr, 0);
                mBackgroundShape = getShape(shapeFlag);
            } else if (attr == R.styleable.ButtonView_cornerRadius) {
                mCornerRadius = typedArray.getDimensionPixelSize(attr, 0);
            } else if (attr == R.styleable.ButtonView_solidColor) {
                mSolidColor = new int[]{typedArray.getColor(attr, 0)};
            } else if (attr == R.styleable.ButtonView_solidColorEntries) {
                int solidColorArrayId = typedArray.getResourceId(attr, 0);
                mSolidColor = mResourceHelper.getColorArray(solidColorArrayId);
            } else if (attr == R.styleable.ButtonView_strokeWidth) {
                mStrokeWidth = typedArray.getDimensionPixelSize(attr, 2);
            } else if (attr == R.styleable.ButtonView_strokeColor) {
                mStrokeColor = new int[]{typedArray.getColor(attr, 0)};
            } else if (attr == R.styleable.ButtonView_strokeColorEntries) {
                int strokeColorArrayId = typedArray.getResourceId(attr, 0);
                mStrokeColor = mResourceHelper.getColorArray(strokeColorArrayId);
            } else if (attr == R.styleable.ButtonView_backgroundDrawableEntries) {
                int backgroundDrawableArrayId = typedArray.getResourceId(attr, 0);
                Drawable[] drawables = mResourceHelper.getDrawableArray(backgroundDrawableArrayId);
                ViewCompat.setBackground(this, SelectorFactory.createDrawableSelector(drawables));
            } else if (attr == R.styleable.ButtonView_textColorEntries) {
                int textColorArrayId = typedArray.getResourceId(attr, 0);
                int[] colorArray = mResourceHelper.getColorArray(textColorArrayId);
                setTextColor(SelectorFactory.createColorSelector(colorArray));
            } else if (attr == R.styleable.ButtonView_compoundDrawable) {
                compoundDrawable = typedArray.getDrawable(attr);
            } else if (attr == R.styleable.ButtonView_compoundDrawableWidth) {
                mCompoundDrawableWidth = typedArray.getDimensionPixelSize(attr, 0);
            } else if (attr == R.styleable.ButtonView_compoundDrawableHeight) {
                mCompoundDrawableHeight = typedArray.getDimensionPixelSize(attr, 0);
            } else if (attr == R.styleable.ButtonView_compoundDrawableEntries) {
                int arrayId = typedArray.getResourceId(attr, 0);
                Drawable[] drawables = mResourceHelper.getDrawableArray(arrayId);
                compoundDrawable = SelectorFactory.createDrawableSelector(drawables);
            } else if (attr == R.styleable.ButtonView_compoundDrawableGravity) {
                mCompoundDrawableGravity = getCompoundDrawableGravity(typedArray.getInt(attr, 0));
            } else if (attr == R.styleable.ButtonView_compoundPadding) {
                int compoundPadding = typedArray.getDimensionPixelSize(attr, 0);
                setCompoundDrawablePadding(compoundPadding);
            }
        }
        typedArray.recycle();
        if (getBackground() == null && mBackgroundShape != null) {
            setBackgroundShape(mBackgroundShape, mSolidColor);
        }
        if (compoundDrawable != null) {
            setCompoundDrawable(compoundDrawable);
        }

    }

    private int getColor(int[] colors, int index) {
        if (colors == null || index >= colors.length) {
            return 0;
        }
        return colors[index];
    }

    public ButtonView setCornerRadius(int cornerRadius) {
        mCornerRadius = cornerRadius;
        return this;
    }

    public ButtonView setStrokeWidth(int strokeWidth) {
        mStrokeWidth = strokeWidth;
        return this;
    }

    public ButtonView setStrokeColor(int[] strokeColor) {
        mStrokeColor = strokeColor;
        return this;
    }

    public ButtonView setBackgroundShape(BackgroundShape backgroundShape, int[] solidColors) {
        if (solidColors == null) {
            solidColors = new int[]{Color.LTGRAY};
        }
        int len = Math.max(solidColors.length, mStrokeColor != null ? mStrokeColor.length : 0);
        Drawable[] drawables = new Drawable[len];
        for (int i = 0; i < len; i++) {
            int solidColor = getColor(solidColors, i);
            int strokeColor = getColor(mStrokeColor, i);
            switch (backgroundShape) {
                case CIRCLE:
                    drawables[i] = ShapeDrawableFactory.createCircle(solidColor, mStrokeWidth, strokeColor);
                    break;
                case CIRCLE_RECT:
                    drawables[i] = ShapeDrawableFactory.createCircleRect(solidColor, mStrokeWidth, strokeColor);
                    break;
                default:
                    drawables[i] = ShapeDrawableFactory.createRoundRect(mCornerRadius, solidColor, mStrokeWidth, strokeColor);
                    break;
            }
        }
        ViewCompat.setBackground(this, SelectorFactory.createDrawableSelector(drawables));
        return this;
    }


    private BackgroundShape getShape(int shapeFlag) {
        switch (shapeFlag) {
            case 2:
                return BackgroundShape.CIRCLE;
            case 3:
                return BackgroundShape.CIRCLE_RECT;
            default:
                return BackgroundShape.RECTANGLE;
        }
    }

    private int getCompoundDrawableGravity(int gravityFlag) {
        switch (gravityFlag) {
            case 1://top
                return Gravity.TOP;
            case 2://right
                return Gravity.RIGHT;
            case 3://bottom
                return Gravity.BOTTOM;
            default://left
                return Gravity.LEFT;
        }
    }

    public void setCompoundDrawable(Drawable compoundDrawable) {
        boolean isHorizontal = true;
        Drawable[] drawables = new Drawable[4];
        switch (mCompoundDrawableGravity) {
            case Gravity.TOP:
                drawables[1] = compoundDrawable;
                isHorizontal = false;
                break;
            case Gravity.RIGHT:
                drawables[2] = compoundDrawable;
                break;
            case Gravity.BOTTOM:
                drawables[3] = compoundDrawable;
                isHorizontal = false;
                break;
            default:
                drawables[0] = compoundDrawable;
                break;
        }
        if (mCompoundDrawableWidth == 0 || mCompoundDrawableHeight == 0) {
            float ratio = compoundDrawable.getIntrinsicWidth() * 1f / compoundDrawable.getIntrinsicHeight();
            if (isHorizontal) {
                mCompoundDrawableHeight = (int) getTextSize();
                mCompoundDrawableWidth = (int) (mCompoundDrawableHeight * ratio);
            } else {
                mCompoundDrawableWidth = (int) Layout.getDesiredWidth(getText(), getPaint());
                mCompoundDrawableHeight = (int) (mCompoundDrawableWidth / ratio);
            }
        }
        compoundDrawable.setBounds(0, 0, mCompoundDrawableWidth, mCompoundDrawableHeight);
        setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

    public void setCompoundDrawableWidth(int compoundDrawableWidth) {
        mCompoundDrawableWidth = compoundDrawableWidth;
    }

    public void setCompoundDrawableHeight(int compoundDrawableHeight) {
        mCompoundDrawableHeight = compoundDrawableHeight;
    }

    public void setCompoundDrawableGravity(int gravity) {
        mCompoundDrawableGravity = gravity;
    }

    public static enum BackgroundShape {

        RECTANGLE(1), CIRCLE(2), CIRCLE_RECT(3);

        private int value;

        BackgroundShape(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }
}
