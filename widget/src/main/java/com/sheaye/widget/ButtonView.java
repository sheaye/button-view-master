package com.sheaye.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
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
 * <p>
 * 名词定义：“点击顺序”：数组中从左到右依次为state_normal,state_pressed,state_selected
 * <p>
 * R.styleable.ButtonView_cornerRadius 设置圆角半径，仅当shape为rectangle时有效
 * <p>
 * R.styleable.ButtonView_solidColor 填充部分的背景色，单色，不随state变化，默认为白色
 * <p>
 * R.styleable.ButtonView_solidColorEntries 填充部分的背景色集合，随state变化，最多3个,默认全为白色,遵循“点击顺序”
 * <p>
 * R.styleable.ButtonView_strokeColor 外围线框颜色，有了颜色才会出现外框，此为单色，不随state变化
 * <p>
 * R.styleable.ButtonView_strokeColorEntries 外围线框颜色集合，有了颜色才会出现外框，随state变化，遵循“点击顺序”
 * <p>
 * R.styleable.ButtonView_strokeWidth 外围线框的宽度，默认为1px
 * <p>
 * R.styleable.ButtonView_backgroundDrawableEntries 背景图集合，图片随state变化，遵循“点击顺序”
 * <p>
 * R.styleable.ButtonView_textColorEntries 文字颜色集合，颜色随state变化，遵循“点击顺序”
 * <p>
 * R.styleable.ButtonView_compoundIcon 附带的静态图片，不随state变化，
 * <p>
 * R.styleable.ButtonView_compoundIconWidth 附带图片的宽度，默认情况下，图文呈竖直方向时约等于文本宽度，水平方向时随高度等比缩放
 * <p>
 * R.styleable.ButtonView_compoundIconHeight 附带图片的高度，默认情况下，图片呈水平方向时约等于文本行高，竖直方向时随宽度等比缩放
 * <p>
 * R.styleable.ButtonView_compoundIconEntries 附带的图片集，随state变化，遵循“点击顺序”
 * <p>
 * R.styleable.ButtonView_compoundIconGravity 附带图片的位置，只有左、上、右、下四种位置，默认为左
 * <p>
 * R.styleable.ButtonView_compoundPadding 附带图片与文字的间隔
 */


public class ButtonView extends AppCompatButton {

    static final int SHAPE_RECTANGLE = 1;
    static final int SHAPE_CIRCLE = 2;
    static final int SHAPE_CIRCLE_RECT = 3;
    private static final int HORIZONTAL = 0;
    private static final int VERTICAL = 1;
    private int mRadius;
    private int[] mSolidColors = new int[3];
    private Drawable[] mBackgroundDrawables;
    private int mStrokeWidth;
    private int[] mStrokeColors = new int[3];
    private int mBackgroundShape;
    private ResourcesHelper mResourcesHelper;
    private Drawable[] mCompoundDrawables;
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
            @DrawableRes int[] resIdArray = mResourcesHelper.getResIdArray(compoundIconArrayId, 3);
            setCompoundIcons(gravity, resIdArray);
            return;
        }
        Drawable compoundIcon = typedArray.getDrawable(R.styleable.ButtonView_compoundIcon);
        if (compoundIcon != null) {
            setCompoundIcons(gravity, compoundIcon);
        }
    }

    private void setStrokeIfNeed(TypedArray typedArray) {
        int strokeColorArrayId = typedArray.getResourceId(R.styleable.ButtonView_strokeColorEntries, NULL);
        if (strokeColorArrayId != NULL) {
            mStrokeWidth = (int) typedArray.getDimension(R.styleable.ButtonView_strokeWidth, 1);
            int[] strokeColorsResIds = mResourcesHelper.getResIdArray(strokeColorArrayId, 3);
            setStrokeColor(mResourcesHelper.getColors(strokeColorsResIds));
        } else {
            int strokeColor = typedArray.getColor(R.styleable.ButtonView_strokeColor, NULL);
            if (strokeColor != NULL) {
                setStrokeWidth((int) typedArray.getDimension(R.styleable.ButtonView_strokeWidth, 1));
                setStrokeColor(strokeColor, strokeColor, strokeColor);
            }
        }
    }

    private void setTextColorsIfNeed(TypedArray typedArray) {
        int textColorArrayId = typedArray.getResourceId(R.styleable.ButtonView_textColorEntries, NULL);
        if (textColorArrayId != NULL) {
            int[] textColorResIds = mResourcesHelper.getResIdArray(textColorArrayId);
            setTextColors(mResourcesHelper.getColors(textColorResIds));
        }
    }

    private void setSolidColorIfNeed(TypedArray typedArray) {
        int solidColorArrayId = typedArray.getResourceId(R.styleable.ButtonView_solidColorEntries, NULL);
        if (solidColorArrayId != NULL) {
            int[] solidColorResIds = mResourcesHelper.getResIdArray(solidColorArrayId);
            if (solidColorResIds != null) {
                setSolidColor(mResourcesHelper.getColors(solidColorResIds));
            }
            return;
        }
        int color = typedArray.getColor(R.styleable.ButtonView_solidColor, Color.LTGRAY);
        setSolidColor(color);
    }

    private void setBackgroundDrawablesIfNeed(TypedArray typedArray) {
        int drawableArrayId = typedArray.getResourceId(R.styleable.ButtonView_backgroundDrawableEntries, NULL);
        if (drawableArrayId != NULL) {
            int[] drawableResIds = mResourcesHelper.getResIdArray(drawableArrayId, 3);
            setBackgroundDrawables(mResourcesHelper.getDrawables(drawableResIds));
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
        if (mCompoundDrawables != null) {
            setCompoundDrawables(mCompoundDrawables[0], mCompoundDrawables[1], mCompoundDrawables[2], mCompoundDrawables[3]);
        }
    }

    public ButtonView setCompoundIcons(int gravity, @DrawableRes int... drawableRes) {
        return setCompoundIcons(
                gravity, mResourcesHelper.getDrawables(drawableRes));

    }

    public ButtonView setCompoundIcons(int gravity, Drawable... drawable) {
        mCompoundDrawable = SelectorFactory.createDrawableSelector(drawable);
        mCompoundDrawables = new Drawable[4];
        switch (gravity) {
            case 1:// top
            case Gravity.TOP:
                mCompoundDrawables[1] = mCompoundDrawable;
                mCompoundOrientation = VERTICAL;
                break;
            case 2://right
            case Gravity.END:
            case Gravity.RIGHT:
                mCompoundDrawables[2] = mCompoundDrawable;
                mCompoundOrientation = HORIZONTAL;
                break;
            case 3://bottom
            case Gravity.BOTTOM:
                mCompoundDrawables[3] = mCompoundDrawable;
                mCompoundOrientation = VERTICAL;
                break;
            default:
                mCompoundDrawables[0] = mCompoundDrawable;
                mCompoundOrientation = HORIZONTAL;
                break;
        }
        return this;
    }

    public ButtonView setStrokeColor(@NonNull int... strokeColor) {
        if (strokeColor.length > 3) {
            throw new IllegalArgumentException("strokeColor值最多只支持3个");
        }
        for (int i = 0; i < mStrokeColors.length; i++) {
            mStrokeColors[i] = strokeColor[0];
        }
        System.arraycopy(strokeColor, 1, mStrokeColors, 1, strokeColor.length - 1);
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

    /**
     * @param color 最多支持3个，依次为state_normal,state_pressed,state_selected
     */
    public ButtonView setSolidColor(@NonNull int... color) {
        if (color.length > 3) {
            throw new IllegalArgumentException("solid color值最多只支持3个");
        }
//      初始化mSolidColors
        for (int i = 0; i < mSolidColors.length; i++) {
            mSolidColors[i] = color[0];
        }
        System.arraycopy(color, 1, mSolidColors, 1, color.length - 1);
        return this;
    }

    public ButtonView setTextColors(@NonNull int... color) {
        setTextColor(SelectorFactory.createColorSelector(color));
        return this;
    }

    /**
     * @param backgroundDrawables 背景图，最多不能超过3个
     * @return
     */
    public ButtonView setBackgroundDrawables(Drawable... backgroundDrawables) {
        if (backgroundDrawables.length > 3) {
            throw new IllegalArgumentException("backgroundDrawables 最多不能超过3个");
        }
        mBackgroundDrawables = backgroundDrawables;
        return this;
    }

    public ButtonView setBackgroundDrawbales(@DrawableRes int... drawableRes) {
        return setBackgroundDrawables(mResourcesHelper.getDrawables(drawableRes));
    }

    public void commit() {
        if (mBackgroundDrawables != null) {
            ViewCompat.setBackground(this, SelectorFactory.createDrawableSelector(mBackgroundDrawables));
            return;
        }
        if (getBackground()!=null) {
            return;
        }
        Drawable[] shapeDrawables = new Drawable[3];
        for (int i = 0; i < shapeDrawables.length; i++) {
            shapeDrawables[i] = getShape(mStrokeColors[i], mSolidColors[i]);
        }
        StateListDrawable selector = SelectorFactory.createDrawableSelector(shapeDrawables);
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
