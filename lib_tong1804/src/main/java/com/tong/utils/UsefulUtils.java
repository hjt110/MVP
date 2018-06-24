package com.tong.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Base64;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by ZD-104 on 2017/8/15.
 */

public class UsefulUtils
{
    /***************************
     * 获取时间戳
     **************************************************/
    public static String getTime()
    {
        long time = System.currentTimeMillis() / 1000;
        String date = String.valueOf(time);
        return date;
    }

    /****************************
     * 字符串base64加密
     **************************************/
    public static String getBase64(String str)
    {
        String result = "";
        if (str != null)
        {
            try
            {
                result = new String(Base64.encode(str.getBytes("utf-8"), Base64.NO_WRAP), "utf-8");
            } catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**********************
     * 得到当前运行程序的sHA1
     ***************************************/
    public static String sHA1(Context context)
    {
        try
        {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++)
            {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    /***********************存储/读取String数据********************************************/
    //保存
//    public void save(String data)
//    {
//        FileOutputStream out = null;
//        PrintStream ps = null;
//        try
//        {
//            out = super.openFileOutput(Constance.FILE_NAME, Activity.MODE_APPEND);
//            ps = new PrintStream(out);
//            ps.println(data);
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        } finally
//        {
//            if (out != null)
//            {
//                try
//                {
//                    out.close();
//                    ps.close();
//                } catch (IOException e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }


    //读取
//    private StringBuffer read()
//    {
//        FileInputStream in = null;
//        Scanner s = null;
//        StringBuffer sb = new StringBuffer();
//        try
//        {
//            in = super.openFileInput(Constance.FILE_NAME);
//            s = new Scanner(in);
//            while (s.hasNext())
//            {
//                sb.append(s.next());
//            }
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return sb;
//    }
}
