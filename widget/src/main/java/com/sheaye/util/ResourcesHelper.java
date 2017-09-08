package com.sheaye.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;

/**
 * Created by yexinyan on 2017/8/20.
 */

public class ResourcesHelper {

    private Resources mResources;

    public ResourcesHelper(Context context) {
        mResources = context.getResources();
    }

    /**
     * @param colorRes color的资源id
     * @return 颜色值
     */
    public int getColor(int colorRes) {
        if (colorRes <= 0) {
            return 0;
        }
        return ResourcesCompat.getColor(mResources, colorRes, null);
    }

    /**
     * @param arrayId color资源id的array的resId
     * @return 颜色值数组
     */
    public int[] getColorArray(@ArrayRes int arrayId) {
        if (arrayId <= 0) {
            return null;
        }
        TypedArray typedArray = mResources.obtainTypedArray(arrayId);
        int length = typedArray.length();
        int[] colors = new int[length];
        for (int i = 0; i < length; i++) {
            colors[i] = getColor(typedArray.getResourceId(i, 0));
        }
        typedArray.recycle();
        return colors;
    }

    /**
     * @param drawableRes drawable的资源id
     * @return drawable
     */
    public Drawable getDrawable(int drawableRes) {
        if (drawableRes <= 0) {
            return null;
        }
        return ResourcesCompat.getDrawable(mResources, drawableRes, null);
    }

    /**
     * @param arrayId drawable资源id的array的resId
     * @return drawable数组
     */
    public Drawable[] getDrawableArray(@ArrayRes int arrayId) {
        if (arrayId <= 0) {
            return null;
        }
        TypedArray typedArray = mResources.obtainTypedArray(arrayId);
        int length = typedArray.length();
        Drawable[] drawables = new Drawable[length];
        for (int i = 0; i < length; i++) {
            drawables[i] = getDrawable(typedArray.getResourceId(i, 0));
        }
        typedArray.recycle();
        return drawables;
    }

    /**
     * @param arrayId 资源id组成的array的resId
     * @return 资源id的数组
     */
    public int[] getResIdArray(@ArrayRes int arrayId) {
        if (arrayId <= 0) {
            return null;
        }
        TypedArray typedArray = mResources.obtainTypedArray(arrayId);
        int length = typedArray.length();
        int[] ids = new int[length];
        for (int i = 0; i < length; i++) {
            ids[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();
        return ids;
    }

}
