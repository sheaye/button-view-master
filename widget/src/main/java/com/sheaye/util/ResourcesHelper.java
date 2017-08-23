package com.sheaye.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.IdRes;
import android.support.v4.content.res.ResourcesCompat;

import static com.sheaye.util.Const.NULL;


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

    public int getColor(int colorRes) {
        if (colorRes == NULL) {
            return NULL;
        }
        return ResourcesCompat.getColor(mResources, colorRes, null);
    }

    public Drawable getDrawable(int drawableRes) {
        if (drawableRes == NULL) {
            return null;
        }
        return ResourcesCompat.getDrawable(mResources, drawableRes, null);
    }

    public int[] getResIdArray(@ArrayRes int arrayId) {
        if (arrayId != NULL) {
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

    public
    @IdRes
    int[] getResIdArray(@ArrayRes int arrayId, int count) {
        if (arrayId == NULL) {
            return null;
        }
        TypedArray typedArray = mResources.obtainTypedArray(arrayId);
        @IdRes int[] ids = new int[count];
        int length = Math.min(typedArray.length(), count);
        for (int i = 0; i < length; i++) {
            ids[i] = typedArray.getResourceId(i, NULL);
        }
        typedArray.recycle();
        return ids;
    }

}
