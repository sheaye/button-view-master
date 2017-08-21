package com.sheaye.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;

/**
 * Created by yexinyan on 2017/8/20.
 */

public class ResourcesHelper {

    private Resources mResources;
    private Context mContext;

    public ResourcesHelper(Context context) {
        this.mContext = context;
        mResources = mContext.getResources();
    }

    public int getColor(@ColorRes int colorRes) {
        return ResourcesCompat.getColor(mResources, colorRes, null);
    }

    public Drawable geDrawbale(@DrawableRes int drawableRes) {
        return ResourcesCompat.getDrawable(mResources, drawableRes, null);
    }

    public int[] getResIdArray(@ArrayRes int arrayId) {
        if (arrayId == View.NO_ID) {
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
