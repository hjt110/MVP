package com.tong.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * app管理类 对应用的activity进行控制
 *
 * @author Huangnan
 */

public class AppManager
{

    private static AppManager manager;
    private Stack<Activity> activityStack;

    private AppManager()
    {
    }

    //单例
    public static AppManager getInstance()
    {

        if (manager == null)
        {
            synchronized (AppManager.class)
            {
                if (manager == null)
                {
                    manager = new AppManager();
                }
            }
        }
        return manager;
    }

    /**
     * 添加Activity到堆栈
     */
    // Stack <Activity> activityStack;
    public void addActivity(Activity activity)
    {
        if (activityStack == null)
        {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity()
    {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity()
    {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity)
    {
        if (activity != null)
        {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls)
    {
        for (Activity activity : activityStack)
        {
            if (activity.getClass().equals(cls))
            {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity()
    {
        for (int i = 0; i < activityStack.size(); i++)
        {
            if (null != activityStack.get(i))
            {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }


    /**
     * 使用方法
     * 写个父类来继承，在oncreat（）里添加AppManager.getInstance().addActivity(this);
     *
     */

}

