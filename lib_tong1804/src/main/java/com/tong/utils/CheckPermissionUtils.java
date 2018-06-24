package com.tong.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZD-104 on 2017/7/13.
 */

public final class CheckPermissionUtils
{

    private CheckPermissionUtils()
    {
    }

    //检测权限
    private static String[] checkPermission(Context context, String[] permissions)
    {
        List<String> data = new ArrayList<>();//存储未申请的权限
        for (String permission : permissions)
        {
            int checkSelfPermission = ContextCompat.checkSelfPermission(context, permission);
            if (checkSelfPermission == PackageManager.PERMISSION_DENIED)
            {//未申请
                data.add(permission);
            }
        }
        return data.toArray(new String[data.size()]);
    }


    /**
     * 初始化权限事件
     */
    //需要申请的权限的书写格式
    String[] permissions1 = new String[]{
            Manifest.permission.CAMERA
    };
    public static void initPermission(Activity activity,String[] permissions1)
    {

        //检查权限
        String[] permissions = checkPermission(activity, permissions1);
        if (permissions.length == 0)
        {
            //权限都申请了
            //是否登录
        } else
        {
            //申请权限
            ActivityCompat.requestPermissions(activity, permissions, 100);
        }
    }

    /**
     *使用方法
     *调用initPermission ()
     *
     */
}
