package com.sheaye.util;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.NonNull;

/**
 * Created by yexinyan on 2017/8/20.
 */

public class SelectorFactory {

    /**
     * @param color 最多支持3个，依次为state_normal,state_pressed,state_selected
     */
    public static ColorStateList createColorSelector(@NonNull int... color) {
        if (color.length > 3) {
            throw new IllegalArgumentException("createColorSelector 最多只支持3个state状态的color");
        }
        int len = color.length;
        int normal = color[0];
        int[][] states = new int[len][];
        if (states.length > 1) {
            states[0] = new int[]{android.R.attr.state_pressed};
            color[0] = color[1];
            if (states.length > 2) {
                states[1] = new int[]{android.R.attr.state_selected};
                color[1] = color[2];
            }
        }
        states[len-1] = new int[]{};
        color[len-1] = normal;
        return new ColorStateList(states, color);
    }

    /**
     * 创建Drawable的selector
     *
     * @param drawables 最多支持3个，依次为state_normal,state_pressed,state_selected
     */
    public static StateListDrawable createDrawableSelector(@NonNull Drawable... drawables) {
        if (drawables.length > 3) {
            throw new IllegalArgumentException("createDrawableSelector 最多只支持3个state状态的drawable");
        }
        StateListDrawable drawable = new StateListDrawable();
        if (drawables.length > 1) {
            drawable.addState(new int[]{android.R.attr.state_pressed}, drawables[1]);
            if (drawables.length > 2) {
                drawable.addState(new int[]{android.R.attr.state_selected}, drawables[2]);
            }
        }
        drawable.addState(new int[]{}, drawables[0]);
        return drawable;
    }

}
