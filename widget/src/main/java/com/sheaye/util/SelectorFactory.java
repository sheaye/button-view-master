package com.sheaye.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;

/**
 * Created by yexinyan on 2017/8/20.
 */

public class SelectorFactory {

    public static ColorStateList createColorSelector(int normalColor, int pressedColor, int selectedColor) {
        int[][] states = new int[3][];
        states[0] = new int[]{};
        states[1] = new int[]{android.R.attr.state_pressed};
        states[2] = new int[]{android.R.attr.state_selected};
        int[] colors = new int[]{normalColor, pressedColor, selectedColor};
        return new ColorStateList(states, colors);
    }

    public static StateListDrawable createDrawableSelector(Drawable... drawables) {
        if (drawables.length > 3) {
            throw new IllegalArgumentException("颜色最多三个：normal,pressed,selected");
        }
        StateListDrawable drawable = new StateListDrawable();
        if (drawables.length > 1) {
            drawable.addState(new int[]{android.R.attr.state_pressed}, drawables[1]);
        }
        if (drawables.length > 2) {
            drawable.addState(new int[]{android.R.attr.state_selected}, drawables[2]);
        }
        drawable.addState(new int[]{}, drawables[0]);
        return drawable;
    }

    public static StateListDrawable createDrawableSelector(Context context, @DrawableRes int normalRes, @DrawableRes int selectedRes, @DrawableRes int pressedRes) {
        Drawable normal = ContextCompat.getDrawable(context, normalRes);
        Drawable selected = ContextCompat.getDrawable(context, selectedRes);
        Drawable pressed = ContextCompat.getDrawable(context, pressedRes);
        return createDrawableSelector(normal, selected, pressed);
    }

}
