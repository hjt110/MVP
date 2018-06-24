package com.tong.utils;

import android.util.Log;

/**
 * Created by ZD-104 on 2017/12/19.
 */

public class LogUtils
{
    private static boolean is2Log = true;

    public static void e(String TAG, String Msg)
    {
        if (is2Log)
        {
            Log.e(TAG, Msg);
        }
    }
}
