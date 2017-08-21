package com.sheaye.util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;

/**
 * Created by yexinyan on 2017/8/20.
 */

public class ShapeFactory {

    /*public static Shape createCircleRect(final int solidColor, final int strokeColor, final int strokeWidth) {
        return new Shape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(strokeColor);
                float radius = getHeight() / 2;
                RectF outerRectF = new RectF(0, 0, getWidth(), getHeight());
                canvas.drawRoundRect(outerRectF, radius, radius, paint);
                if (strokeWidth != 0 && solidColor != strokeColor) {
                    paint.setColor(solidColor);
                    RectF innerRectF = new RectF(strokeWidth, strokeWidth, getWidth() - strokeWidth, getHeight() - strokeWidth);
                    canvas.drawRoundRect(innerRectF, radius - strokeWidth, radius - strokeWidth, paint);
                }
            }
        };
    }

    public static Shape createCircleRect(int solidColor) {
        return createCircleRect(solidColor, solidColor, 0);
    }*/

    public static Shape createCircleRect() {
        return new Shape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                float radius = getHeight() / 2;
                RectF outerRectF = new RectF(0, 0, getWidth(), getHeight());
                canvas.drawRoundRect(outerRectF, radius, radius, paint);
            }
        };
    }

    public static Shape createRoundRect(int radius) {
        float[] outerRadius = new float[8];
        for (int i = 0; i < outerRadius.length; i++) {
            outerRadius[i] = radius;
        }
        RoundRectShape shape = new RoundRectShape(outerRadius, null, null);
        return shape;
    }

    public static Shape createCircle() {
        return new ArcShape(0, 360);
    }
}
