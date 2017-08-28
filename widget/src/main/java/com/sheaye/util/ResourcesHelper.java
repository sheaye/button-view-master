package com.sheaye.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;

import static com.sheaye.util.Const.NULL;


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
        if (colorRes == NULL) {
            return NULL;
        }
        return ResourcesCompat.getColor(mResources, colorRes, null);
    }

    /**
     * @param arrayId color资源id的array的resId
     * @return 颜色值数组
     */
    public int[] getColorsFromArray(@ArrayRes int arrayId) {
        int[] resIdArray = getResIdArray(arrayId);
        return getColors(resIdArray);
    }

    /**
     * @param colorRes color的资源id
     * @return color值的数组
     */
    public int[] getColors(@NonNull int... colorRes) {
        int len = colorRes.length;
        int[] colors = new int[len];
        for (int i = 0; i < len; i++) {
            colors[i] = getColor(colorRes[i]);
        }
        return colors;
    }

    /**
     * @param drawableRes drawable的资源id
     * @return drawable
     */
    public Drawable getDrawable(int drawableRes) {
        if (drawableRes == NULL) {
            return null;
        }
        return ResourcesCompat.getDrawable(mResources, drawableRes, null);
    }

    /**
     * @param arrayId drawable资源id的array的resId
     * @return drawable数组
     */
    public Drawable[] getDrawablesFromArray(@ArrayRes int arrayId) {
        int[] resIdArray = getResIdArray(arrayId);
        return getDrawables(resIdArray);
    }

    /**
     * @param drawableRes drawable的资源id
     * @return drawable数组
     */
    public Drawable[] getDrawables(@NonNull int... drawableRes) {
        int len = drawableRes.length;
        Drawable[] drawables = new Drawable[len];
        for (int i = 0; i < len; i++) {
            drawables[i] = getDrawable(drawableRes[i]);
        }
        return drawables;
    }

    /**
     * @param arrayId 资源id组成的array的resId
     * @return 资源id的数组
     */
    public int[] getResIdArray(@ArrayRes int arrayId) {
        if (arrayId == NULL) {
            return null;
        }
        TypedArray typedArray = mResources.obtainTypedArray(arrayId);
        int length = typedArray.length();
        int[] ids = new int[length];
        for (int i = 0; i < length; i++) {
            ids[i] = typedArray.getResourceId(i, NULL);
        }
        typedArray.recycle();
        return ids;
    }

}
