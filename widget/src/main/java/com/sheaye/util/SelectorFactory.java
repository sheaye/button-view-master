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

    public static StateListDrawable createDrawableSelector(Drawable normal, Drawable pressed, Drawable selected) {
        StateListDrawable drawable = new StateListDrawable();
        if (pressed != null) {
            drawable.addState(new int[]{android.R.attr.state_pressed}, pressed);
        }
        if (selected != null) {
            drawable.addState(new int[]{android.R.attr.state_selected}, selected);
        }
        drawable.addState(new int[]{}, normal);
        return drawable;
    }

    public static StateListDrawable createDrawableSelector(Context context, @DrawableRes int normalRes, @DrawableRes int selectedRes, @DrawableRes int pressedRes) {
        Drawable normal = ContextCompat.getDrawable(context, normalRes);
        Drawable selected = ContextCompat.getDrawable(context, selectedRes);
        Drawable pressed = ContextCompat.getDrawable(context, pressedRes);
        return createDrawableSelector(normal, selected, pressed);
    }

}
