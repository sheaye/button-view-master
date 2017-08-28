package com.sheaye.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
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
    private ButtonShape mButtonShape;
    private ResourcesHelper mResourcesHelper;
    private Drawable[] mCompoundDrawables;
    private Drawable mCompoundDrawable;
    private int mCompoundDrawableWidth;
    private int mCompoundDrawableHeight;
    private int mCompoundOrientation;
    private Drawable mBackgroundDrawable;

    public ButtonView(Context context) {
        this(context, null);
    }

    public ButtonView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.CENTER);
        setClickable(true);
        setMinHeight(0);
        mBackgroundDrawable = getBackground();
        mResourcesHelper = new ResourcesHelper(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ButtonView, defStyleAttr, 0);
        if (mBackgroundDrawable == null) {
            setBackgroundWithDrawables(typedArray);
        }
        if (mBackgroundDrawable == null) {
            setBackgroundWithShape(typedArray);
        }
        setCompoundDrawables(typedArray);
        setTextColors(typedArray);
        typedArray.recycle();
    }

    private void setBackgroundWithDrawables(TypedArray typedArray) {
        int drawableArrayId = typedArray.getResourceId(R.styleable.ButtonView_backgroundDrawableEntries, NULL);
        if (drawableArrayId != NULL) {
            setBackgroundSelector(mResourcesHelper.getDrawablesFromArray(drawableArrayId));
        }
    }

    /**
     * @param backgroundDrawables 背景图，最多不能超过3个
     * @return
     */
    public ButtonView setBackgroundSelector(Drawable... backgroundDrawables) {
        if (backgroundDrawables.length > 3) {
            throw new IllegalArgumentException("backgroundDrawables 最多不能超过3个");
        }
        mBackgroundDrawable = SelectorFactory.createDrawableSelector(backgroundDrawables);
        ViewCompat.setBackground(this, mBackgroundDrawable);
        return this;
    }

    public ButtonView setBackgroundSelector(@DrawableRes int... drawableRes) {
        return setBackgroundSelector(mResourcesHelper.getDrawables(drawableRes));
    }

    private void setBackgroundWithShape(TypedArray typedArray) {
        int strokeWidth = (int) typedArray.getDimension(R.styleable.ButtonView_strokeWidth, 3);
        int radius = typedArray.getDimensionPixelSize(R.styleable.ButtonView_cornerRadius, 0);
        ShapeSelector selector = new ShapeSelector(getShape(typedArray))
                .setRadius(radius)
                .setSolidColors(getSolidColors(typedArray))
                .setStrokeWidth(strokeWidth)
                .setStrokeColors(getStrokeColors(typedArray));
        setBackgroundSelector(selector);
    }

    public void setBackgroundSelector(ShapeSelector selector) {
        mButtonShape = selector.mButtonShape;
        int strokeWidth = selector.mStrokeWidth;
        int[] strokeColors = selector.mStrokeColors;
        int[] solidColors = selector.mSolidColors;
        ShapeDrawable[] drawables = new ShapeDrawable[solidColors.length];
        for (int i = 0; i < solidColors.length; i++) {
            switch (selector.mButtonShape) {
                case CIRCLE:
                    drawables[i] = ShapeDrawableFactory.createCircle(solidColors[i], strokeWidth, strokeColors[i]);
                    break;
                case CIRCLE_RECT:
                    drawables[i] = ShapeDrawableFactory.createCircleRect(solidColors[i], strokeWidth, strokeColors[i]);
                    break;
                default:
                    drawables[i] = ShapeDrawableFactory.createRoundRect(selector.mRadius, solidColors[i], strokeWidth, strokeColors[i]);
                    break;
            }
        }
        mBackgroundDrawable = SelectorFactory.createDrawableSelector(drawables);
        ViewCompat.setBackground(this, mBackgroundDrawable);
    }

    private ButtonShape getShape(TypedArray typedArray) {
        int shapeValue = typedArray.getInt(R.styleable.ButtonView_shape, SHAPE_RECTANGLE);
        switch (shapeValue) {
            case SHAPE_CIRCLE_RECT:
                return ButtonShape.CIRCLE_RECT;
            case SHAPE_CIRCLE:
                return ButtonShape.CIRCLE;
            default:
                return ButtonShape.RECTANGLE;
        }

    }

    private int[] getSolidColors(TypedArray typedArray) {
        int[] colors;
        int arrayId = typedArray.getResourceId(R.styleable.ButtonView_solidColorEntries, NULL);
        if (arrayId != NULL) {
            colors = mResourcesHelper.getColorsFromArray(arrayId);
        } else {
            int color = typedArray.getColor(R.styleable.ButtonView_solidColor, Color.LTGRAY);
            colors = new int[]{color, color, color};
        }
        return colors;
    }

    private int[] getStrokeColors(TypedArray typedArray) {
        int[] colors = null;
        int arrayId = typedArray.getResourceId(R.styleable.ButtonView_strokeColorEntries, NULL);
        if (arrayId != NULL) {
            colors = mResourcesHelper.getColorsFromArray(arrayId);
        } else {
            int color = typedArray.getColor(R.styleable.ButtonView_strokeColor, NULL);
            if (color != NULL) {
                colors = new int[]{color, color, color};
            }
        }
        return colors;
    }

    private void setTextColors(TypedArray typedArray) {
        int textColorArrayId = typedArray.getResourceId(R.styleable.ButtonView_textColorEntries, NULL);
        if (textColorArrayId != NULL) {
            setTextColors(mResourcesHelper.getColorsFromArray(textColorArrayId));
        }
    }

    public ButtonView setTextColors(@NonNull int... color) {
        setTextColor(SelectorFactory.createColorSelector(color));
        return this;
    }

    private void setCompoundDrawables(TypedArray typedArray) {
        int arrayId = typedArray.getResourceId(R.styleable.ButtonView_compoundIconEntries, NULL);
        Drawable compoundIcon = typedArray.getDrawable(R.styleable.ButtonView_compoundIcon);
        if (arrayId == NULL && compoundIcon == null) {
            return;
        }
        int gravity = typedArray.getInt(R.styleable.ButtonView_compoundIconGravity, Gravity.LEFT);
        int compoundPadding = typedArray.getDimensionPixelSize(R.styleable.ButtonView_compoundPadding, 0);
        int width = typedArray.getDimensionPixelSize(R.styleable.ButtonView_compoundIconWidth, 0);
        int height = typedArray.getDimensionPixelSize(R.styleable.ButtonView_compoundIconHeight, 0);
        CompoundSelector selector = new CompoundSelector(gravity)
                .setPadding(compoundPadding)
                .setWidth(width)
                .setHeight(height);
        if (arrayId != NULL) {
            selector.setDrawables(mResourcesHelper.getDrawablesFromArray(arrayId));
            setCompoundSelector(selector);
            return;
        }
        selector.setDrawables(compoundIcon);
        setCompoundSelector(selector);
    }

    public ButtonView setCompoundSelector(CompoundSelector selector) {
        setBackgroundColor(Color.TRANSPARENT);
        setCompoundDrawablePadding(selector.mPadding);
        mCompoundDrawable = selector.mDrawable;
        mCompoundDrawableHeight = selector.mHeight;
        mCompoundDrawableWidth = selector.mWidth;
        mCompoundDrawables = new Drawable[4];
        switch (selector.mGravity) {
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mButtonShape == ButtonShape.CIRCLE) {
            int diameter = Math.max(getMeasuredWidth(), getMeasuredHeight());
            setMeasuredDimension(diameter, diameter);
        }
        if (mCompoundDrawable != null) {
            if (mCompoundDrawableWidth == 0 || mCompoundDrawableHeight == 0) {
                if (mCompoundOrientation == HORIZONTAL) {
                    mCompoundDrawableHeight = getLineHeight();
                    float scale = mCompoundDrawableHeight * 1.0f / mCompoundDrawable.getIntrinsicHeight();
                    mCompoundDrawableWidth = (int) (scale * mCompoundDrawable.getIntrinsicWidth());
                } else {
                    mCompoundDrawableWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
                    float scale = mCompoundDrawableWidth * 1.0f / mCompoundDrawable.getIntrinsicWidth();
                    mCompoundDrawableHeight = (int) (scale * mCompoundDrawable.getIntrinsicHeight());
                }
            }
            mCompoundDrawable.setBounds(0, 0, mCompoundDrawableWidth, mCompoundDrawableHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCompoundDrawables != null) {
            setCompoundDrawables(mCompoundDrawables[0], mCompoundDrawables[1], mCompoundDrawables[2], mCompoundDrawables[3]);
        }
    }

    public static class ShapeSelector {
        private ButtonShape mButtonShape;
        private int mRadius;
        private int[] mSolidColors;
        private int mStrokeWidth;
        private int[] mStrokeColors;

        public ShapeSelector(ButtonShape buttonShape) {
            mButtonShape = buttonShape;
        }

        public ShapeSelector setRadius(int radius) {
            mRadius = radius;
            return this;
        }

        public ShapeSelector setSolidColors(int[] solidColors) {
            mSolidColors = decorateColors(solidColors, Color.LTGRAY);
            return this;
        }

        public ShapeSelector setStrokeWidth(int strokeWidth) {
            mStrokeWidth = strokeWidth;
            return this;
        }

        public ShapeSelector setStrokeColors(int[] strokeColors) {
            mStrokeColors = decorateColors(strokeColors, Color.WHITE);
            ;
            return this;
        }

        private int[] decorateColors(int[] colors, int defaultColor) {
            if (colors == null || colors.length == 0) {
                return new int[]{defaultColor, defaultColor, defaultColor};
            }
            switch (colors.length) {
                case 1:
                    defaultColor = colors[0];
                    return new int[]{defaultColor, defaultColor, defaultColor};
                case 2:
                    return new int[]{colors[0], colors[1], colors[0]};
                case 3:
                    return colors;
                default:
                    throw new IllegalArgumentException("颜色值不能超过3个");
            }
        }
    }

    public static class CompoundSelector {
        private int mGravity;
        private int mPadding;
        private Drawable mDrawable;
        private int mWidth;
        private int mHeight;

        public CompoundSelector(int gravity) {
            mGravity = gravity;
        }

        public CompoundSelector setDrawables(Drawable... drawables) {
            mDrawable = SelectorFactory.createDrawableSelector(drawables);
            return this;
        }

        public CompoundSelector setPadding(int padding) {
            mPadding = padding;
            return this;
        }

        public CompoundSelector setWidth(int width) {
            mWidth = width;
            return this;
        }

        public CompoundSelector setHeight(int height) {
            mHeight = height;
            return this;
        }
    }

}
