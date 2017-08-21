package com.sheaye.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;

/**
 * Created by yexinyan on 2017/8/20.
 */

public class ResourcesHelper {

    private Context mContext;

    public ResourcesHelper(Context context) {
        this.mContext = context;
    }

    public Resources getResources() {
        return mContext.getResources();
    }

    public int getColor(@ColorRes int colorRes) {
        return ResourcesCompat.getColor(getResources(), colorRes, null);
    }

    public int[] getResourceIdArray(@ArrayRes int arrayId) {
        if (arrayId == View.NO_ID) {
            return null;
        }
        TypedArray typedArray = getResources().obtainTypedArray(arrayId);
        int length = typedArray.length();
        int[] ids = new int[length];
        for (int i = 0; i < length; i++) {
            ids[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();
        return ids;
    }

}
