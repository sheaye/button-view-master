package com.sheaye.util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;

import static com.sheaye.util.Const.NULL;

/**
 * Created by yexinyan on 2017/8/20.
 */

public class ShapeDrawableFactory {

    private static void drawRoundRect(Canvas canvas, Paint paint, float radius, int solidColor, int strokeWidth, int strokeColor) {
        int left = 0, top = 0;
        int right = canvas.getWidth();
        int bottom = canvas.getHeight();
        if (strokeWidth != 0 && strokeColor != NULL) {
            paint.setColor(strokeColor);
            RectF outerRectF = new RectF(left, top, right, bottom);
            canvas.drawRoundRect(outerRectF, radius, radius, paint);
            left += strokeWidth;
            top += strokeWidth;
            right -= strokeWidth;
            bottom -= strokeWidth;
            if (radius > 0) {
                radius -= strokeWidth;
            }
        }
        RectF innerRectF = new RectF(left, top, right, bottom);
        paint.setColor(solidColor);
        canvas.drawRoundRect(innerRectF, radius, radius, paint);
    }

    /**
     * @return 圆矩形的shape
     */
    public static ShapeDrawable createCircleRect(final int solidColor, final int strokeWidth, final int strokeColor) {
        Shape shape = new Shape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                drawRoundRect(canvas, paint, getHeight() / 2, solidColor, strokeWidth, strokeColor);
            }
        };
        return new ShapeDrawable(shape);
    }

    /**
     * @return 矩形的shape
     * @param radius 值为0时，返回直角矩形，大于0时返回圆角矩形
     */
    public static ShapeDrawable createRoundRect(final int radius, final int solidColor, final int strokeWidth, final int strokeColor) {
        Shape shape = new Shape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                drawRoundRect(canvas, paint, radius, solidColor, strokeWidth, strokeColor);
            }
        };
        return new ShapeDrawable(shape);
    }

    /**
     * @return 圆形的shape
     */
    public static ShapeDrawable createCircle(final int solidColor, final int strokeWidth, final int strokeColor) {
        Shape shape = new Shape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                float cx = getWidth() / 2;
                float cy = getHeight() / 2;
                float radius = Math.min(cx, cy);
                if (strokeWidth != 0 && strokeColor != NULL) {
                    paint.setColor(strokeColor);
                    canvas.drawCircle(cx, cy, radius, paint);
                    radius -= strokeWidth;
                }
                paint.setColor(solidColor);
                canvas.drawCircle(cx, cy, radius, paint);
            }
        };
        return new ShapeDrawable(shape);
    }
}
