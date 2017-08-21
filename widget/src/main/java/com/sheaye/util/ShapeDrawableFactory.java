package com.sheaye.util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;

/**
 * Created by yexinyan on 2017/8/20.
 */

public class ShapeDrawableFactory {

    private static void drawRoundRect(Canvas canvas, Paint paint, float radius, int solidColor, int strokeWidth, int strokeColor) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        if (strokeWidth != 0) {
            paint.setColor(strokeColor);
            RectF outerRectF = new RectF(0, 0, width, height);
            canvas.drawRoundRect(outerRectF, radius, radius, paint);
        }
        RectF innerRectF = new RectF(strokeWidth, strokeWidth, width - strokeWidth, height - strokeWidth);
        paint.setColor(solidColor);
        canvas.drawRoundRect(innerRectF, radius - strokeWidth, radius - strokeWidth, paint);
    }

    public static ShapeDrawable createCircleRect(final int solidColor, final int strokeWidth, final int strokeColor) {
        Shape shape = new Shape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                drawRoundRect(canvas, paint, getHeight() / 2, solidColor, strokeWidth, strokeColor);
            }
        };
        return new ShapeDrawable(shape);
    }

    public static ShapeDrawable createRoundRect(final int solidColor, final int radius, final int strokeWidth, final int strokeColor) {
        Shape shape = new Shape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                drawRoundRect(canvas, paint, radius, solidColor, strokeWidth, strokeColor);
            }
        };
        return new ShapeDrawable(shape);
    }


    public static ShapeDrawable createCircle(final int solidColor, final int strokeWidth, final int strokeColor) {
        Shape shape = new Shape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                float cx = getWidth() / 2;
                float cy = getHeight() / 2;
                float radius = Math.min(cx, cy);
                if (strokeWidth != 0) {
                    paint.setColor(strokeColor);
                    canvas.drawCircle(cx, cy, radius, paint);
                }
                paint.setColor(solidColor);
                canvas.drawCircle(cx, cy, radius - strokeWidth, paint);
            }
        };
        return new ShapeDrawable(shape);
    }
}
