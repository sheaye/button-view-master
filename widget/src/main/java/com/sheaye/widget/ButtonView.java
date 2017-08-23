package com.sheaye.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.Gravity;

import com.sheaye.util.ResourcesHelper;
import com.sheaye.util.SelectorFactory;
import com.sheaye.util.ShapeDrawableFactory;

import static com.sheaye.util.Const.NULL;

/**
 * Created by yexinyan on 2017/8/20.
 *
 * 名词定义：“点击顺序”：数组中从左到右依次为state_normal,state_pressed,state_selected
 * 
 * R.styleable.ButtonView_cornerRadius 设置圆角半径，仅当shape为rectangle时有效
 *
 * R.styleable.ButtonView_solidColor 填充部分的背景色，单色，不随state变化，默认为白色
 *
 * R.styleable.ButtonView_solidColorEntries 填充部分的背景色集合，随state变化，最多3个,默认全为白色,遵循“点击顺序”
 *
 * R.styleable.ButtonView_strokeColor 外围线框颜色，有了颜色才会出现外框，此为单色，不随state变化
 *
 * R.styleable.ButtonView_strokeColorEntries 外围线框颜色集合，有了颜色才会出现外框，随state变化，遵循“点击顺序”
 *
 * R.styleable.ButtonView_strokeWidth 外围线框的宽度，默认为1px
 *
 * R.styleable.ButtonView_backgroundDrawableEntries 背景图集合，图片随state变化，遵循“点击顺序”
 *
 * R.styleable.ButtonView_textColorEntries 文字颜色集合，颜色随state变化，遵循“点击顺序”
 *
 * R.styleable.ButtonView_compoundIcon 附带的静态图片，不随state变化，
 *
 * R.styleable.ButtonView_compoundIconWidth 附带图片的宽度，默认情况下，图文呈竖直方向时约等于文本宽度，水平方向时随高度等比缩放
 *
 * R.styleable.ButtonView_compoundIconHeight 附带图片的高度，默认情况下，图片呈水平方向时约等于文本行高，竖直方向时随宽度等比缩放
 *
 * R.styleable.ButtonView_compoundIconEntries 附带的图片集，随state变化，遵循“点击顺序”
 *
 * R.styleable.ButtonView_compoundIconGravity 附带图片的位置，只有左、上、右、下四种位置，默认为左
 *
 * R.styleable.ButtonView_compoundPadding 附带图片与文字的间隔
 */


public class ButtonView extends AppCompatButton {

    static final int SHAPE_RECTANGLE = 1;
    static final int SHAPE_CIRCLE = 2;
    static final int SHAPE_CIRCLE_RECT = 3;

    private static final int HORIZONTAL = 0;
    private static final int VERTICAL = 1;

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

    private ResourcesHelper mResourcesHelper;
    private Drawable mDrawableLeft;
    private Drawable mDrawableTop;
    private Drawable mDrawableRight;
    private Drawable mDrawableBottom;
    private StateListDrawable mCompoundDrawable;
    private int mCompoundIconWidth;
    private int mCompoundIconHeight;
    private int mCompoundOrientation;

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

        mBackgroundShape = typedArray.getInt(R.styleable.ButtonView_shape, SHAPE_RECTANGLE);
        setRadius(typedArray.getDimensionPixelSize(R.styleable.ButtonView_cornerRadius, 0));

        setSolidColorIfNeed(typedArray);
        setStrokeIfNeed(typedArray);
        setBackgroundDrawablesIfNeed(typedArray);
        setTextColorsIfNeed(typedArray);
        setCompoundIconIfNeed(typedArray);

        typedArray.recycle();
        commit();
    }

    private void setCompoundIconIfNeed(TypedArray typedArray) {
        int compoundIconArrayId = typedArray.getResourceId(R.styleable.ButtonView_compoundIconEntries, NULL);
        int gravity = typedArray.getInt(R.styleable.ButtonView_compoundIconGravity, Gravity.LEFT);
        int compoundPadding = typedArray.getDimensionPixelSize(R.styleable.ButtonView_compoundPadding, 0);
        setCompoundDrawablePadding(compoundPadding);
        mCompoundIconWidth = typedArray.getDimensionPixelSize(R.styleable.ButtonView_compoundIconWidth, 0);
        mCompoundIconHeight = typedArray.getDimensionPixelSize(R.styleable.ButtonView_compoundIconHeight, 0);
        if (compoundIconArrayId != NULL) {
            int[] resIdArray = mResourcesHelper.getResIdArray(compoundIconArrayId, 3);
            setCompoundIcons(mResourcesHelper.getDrawable(resIdArray[0]),
                    mResourcesHelper.getDrawable(resIdArray[1]),
                    mResourcesHelper.getDrawable(resIdArray[2]), gravity);
            return;
        }
        Drawable compoundIcon = typedArray.getDrawable(R.styleable.ButtonView_compoundIcon);
        if (compoundIcon != null) {
            setCompoundIcons(compoundIcon, null, null, gravity);
        }
    }

    private void setStrokeIfNeed(TypedArray typedArray) {
        int strokeColorArrayId = typedArray.getResourceId(R.styleable.ButtonView_strokeColorEntries, NULL);
        if (strokeColorArrayId != NULL) {
            mStrokeWidth = (int) typedArray.getDimension(R.styleable.ButtonView_strokeWidth, 1);
            int[] strokeColorsResIds = mResourcesHelper.getResIdArray(strokeColorArrayId, 3);
            setStrokeColors(mResourcesHelper.getColor(strokeColorsResIds[0])
                    , mResourcesHelper.getColor(strokeColorsResIds[1]), mResourcesHelper.getColor(strokeColorsResIds[2]));
        } else {
            int strokeColor = typedArray.getColor(R.styleable.ButtonView_strokeColor, NULL);
            if (strokeColor != NULL) {
                setStrokeWidth((int) typedArray.getDimension(R.styleable.ButtonView_strokeWidth, 1));
                setStrokeColors(strokeColor, strokeColor, strokeColor);
            }
        }
    }

    private void setTextColorsIfNeed(TypedArray typedArray) {
        int textColorArrayId = typedArray.getResourceId(R.styleable.ButtonView_textColorEntries, NULL);
        if (textColorArrayId != NULL) {
            int[] textColorResIds = mResourcesHelper.getResIdArray(textColorArrayId, 3);
            setTextColors(mResourcesHelper.getColor(textColorResIds[0]),
                    mResourcesHelper.getColor(textColorResIds[1]), mResourcesHelper.getColor(textColorResIds[2]));
        }
    }

    private void setSolidColorIfNeed(TypedArray typedArray) {
        int solidColorArrayId = typedArray.getResourceId(R.styleable.ButtonView_solidColorEntries, NULL);
        if (solidColorArrayId != NULL) {
            int[] solidColorResIds = mResourcesHelper.getResIdArray(solidColorArrayId, 3);
            setSolidColors(mResourcesHelper.getColor(solidColorResIds[0]),
                    mResourcesHelper.getColor(solidColorResIds[1]), mResourcesHelper.getColor(solidColorResIds[2]));
            return;
        }
        int color = typedArray.getColor(R.styleable.ButtonView_solidColor, NULL);
        if (color != NULL) {
            setSolidColors(color, color, color);
        }
    }

    private void setBackgroundDrawablesIfNeed(TypedArray typedArray) {
        int drawableArrayId = typedArray.getResourceId(R.styleable.ButtonView_backgroundDrawableEntries, NULL);
        if (drawableArrayId != NULL) {
            int[] drawableResIds = mResourcesHelper.getResIdArray(drawableArrayId, 3);
            setBackgroundDrawables(mResourcesHelper.getDrawable(drawableResIds[0]),
                    mResourcesHelper.getDrawable(drawableResIds[1]),
                    mResourcesHelper.getDrawable(drawableResIds[2]));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mBackgroundShape == SHAPE_CIRCLE) {
            int diameter = Math.max(getMeasuredWidth(), getMeasuredHeight());
            setMeasuredDimension(diameter, diameter);
        }
        if (mCompoundDrawable != null) {
            if (mCompoundIconWidth == 0 || mCompoundIconHeight == 0) {
                if (mCompoundOrientation == HORIZONTAL) {
                    mCompoundIconHeight = getLineHeight();
                    float scale = mCompoundIconHeight * 1.0f / mCompoundDrawable.getIntrinsicHeight();
                    mCompoundIconWidth = (int) (scale * mCompoundDrawable.getIntrinsicWidth());
                } else {
                    mCompoundIconWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
                    float scale = mCompoundIconWidth * 1.0f / mCompoundDrawable.getIntrinsicWidth();
                    mCompoundIconHeight = (int) (scale * mCompoundDrawable.getIntrinsicHeight());
                }
            }
            mCompoundDrawable.setBounds(0, 0, mCompoundIconWidth, mCompoundIconHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setCompoundDrawables(mDrawableLeft, mDrawableTop, mDrawableRight, mDrawableBottom);
    }

    public ButtonView setCompoundIcons(@DrawableRes int normal, @DrawableRes int pressed, @DrawableRes int selected, int gravity) {
        return setCompoundIcons(
                mResourcesHelper.getDrawable(normal),
                mResourcesHelper.getDrawable(pressed),
                mResourcesHelper.getDrawable(selected),
                gravity);

    }

    public ButtonView setCompoundIcons(Drawable compoundIcon, Drawable compoundIconPressed, Drawable compoundIconSelected, int gravity) {
        mCompoundDrawable = SelectorFactory.createDrawableSelector(compoundIcon, compoundIconPressed, compoundIconSelected);
        switch (gravity) {
            case 1:// top
            case Gravity.TOP:
                mDrawableTop = mCompoundDrawable;
                mCompoundOrientation = VERTICAL;
                break;
            case 2://right
            case Gravity.END:
            case Gravity.RIGHT:
                mDrawableRight = mCompoundDrawable;
                mCompoundOrientation = HORIZONTAL;
                break;
            case 3://bottom
            case Gravity.BOTTOM:
                mDrawableBottom = mCompoundDrawable;
                mCompoundOrientation = VERTICAL;
                break;
            default:
                mDrawableLeft = mCompoundDrawable;
                mCompoundOrientation = HORIZONTAL;
                break;
        }
        setCompoundDrawables(mDrawableLeft, mDrawableTop, mDrawableRight, mDrawableBottom);
        return this;
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
        if (solidColor == NULL) {
            solidColor = Color.WHITE;
        }
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

    public ButtonView setBackgroundDrawbales(@DrawableRes int normal, @DrawableRes int pressed, @DrawableRes int selected) {
        return setBackgroundDrawables(
                mResourcesHelper.getDrawable(normal),
                mResourcesHelper.getDrawable(pressed),
                mResourcesHelper.getDrawable(selected));
    }

    public void commit() {
        Drawable normal = mNormalBackgroundDrawable != null ? mNormalBackgroundDrawable :
                getShape(mNormalStrokeColor, mNormalSolidColor);
        Drawable pressed = mPressedBackgroundDrawable != null ? mPressedBackgroundDrawable :
                getShape(mPressedStrokeColor, mPressedSolidColor);
        Drawable selected = mSelectedBackgroundDrawable != null ? mSelectedBackgroundDrawable :
                getShape(mSelectedStrokeColor, mSelectedSolidColor);
        StateListDrawable selector = SelectorFactory.createDrawableSelector(normal, pressed, selected);
        ViewCompat.setBackground(this, selector);
    }

    public ButtonView setStrokeWidth(int strokeWidth) {
        this.mStrokeWidth = strokeWidth;
        return this;
    }

    public ButtonView setCompoundPadding(int padding) {
        setCompoundDrawablePadding(padding);
        return this;
    }
}
