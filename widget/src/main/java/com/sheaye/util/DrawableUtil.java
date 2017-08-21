package com.sheaye.util;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;

/**
 * Created by yexinyan on 2017/8/20.
 */

public class DrawableUtil {

    public static Drawable createDrawable(Shape shape, int color) {
        ShapeDrawable drawable = new ShapeDrawable(shape);
        drawable.getPaint().setColor(color);
        return drawable;
    }
}
