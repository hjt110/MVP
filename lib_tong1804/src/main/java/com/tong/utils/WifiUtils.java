package com.tong.utils;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.List;

/**
 * Created by ZD-104 on 2017/12/21.
 */

public class WifiUtils
{
    // 定义WifiManager对象
    private static WifiManager mWifiManager;
    // 定义WifiInfo对象
    private static WifiInfo mWifiInfo;
    private Context context;
    private static List<ScanResult> scanResults;

    // 构造器
    public WifiUtils(Context context)
    {
        this.context = context;
        // 取得WifiManager对象
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        // 取得WifiInfo对象
        mWifiInfo = mWifiManager.getConnectionInfo();
    }

    public static WifiManager getWifiManager()
    {
        return mWifiManager;
    }

    public static WifiInfo getWifiInfo(){
        return mWifiInfo;
    }


    // 打开WIFI
    public static void openWifi()
    {
        if (!mWifiManager.isWifiEnabled())
        {
            mWifiManager.setWifiEnabled(true);
        } else if (mWifiManager.getWifiState() == 2)
        {
//            Toast.makeText(context, "Wifi正在开启，不用再开了", Toast.LENGTH_SHORT).show();
        } else
        {
//            Toast.makeText(context, "Wifi已经开启,不用再开了", Toast.LENGTH_SHORT).show();
        }
    }

    //获取扫描结果
    public static List<ScanResult> starScan()
    {
        mWifiManager.startScan();
        scanResults = mWifiManager.getScanResults();
        return scanResults;
    }

    // 关闭WIFI
    public static void closeWifi(Context context)
    {
        if (mWifiManager.isWifiEnabled())
        {
            mWifiManager.setWifiEnabled(false);
        } else if (mWifiManager.getWifiState() == 1)
        {
//            Toast.makeText(context, "Wifi已经关闭，不用再关了", Toast.LENGTH_SHORT).show();
        } else if (mWifiManager.getWifiState() == 0)
        {
//            Toast.makeText(context, "Wifi正在关闭，不用再关了", Toast.LENGTH_SHORT).show();
        } else
        {
//            Toast.makeText(context, "请重新关闭", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 获取热点的加密类型
     */
    public static int getType(ScanResult scanResult)
    {
        int type;
        if (scanResult.capabilities.contains("WPA"))
        {
            type = 2;
        } else if (scanResult.capabilities.contains("WEP"))
        {
            type = 1;
        } else
        {
            type = 0;
        }
        return type;
    }

    /**
     * 获取 WifiConfiguration
     *
     * @param SSID
     * @param password
     * @param type
     * @return
     */
    public static WifiConfiguration configWifiInfo(Context context, String SSID, String password, int type)
    {
        WifiConfiguration config = null;
        WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (mWifiManager != null)
        {
            List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
            for (WifiConfiguration existingConfig : existingConfigs)
            {
                if (existingConfig == null) continue;
                if (existingConfig.SSID.equals("\"" + SSID + "\"")  /*&&  existingConfig.preSharedKey.equals("\""  +  password  +  "\"")*/)
                {
                    config = existingConfig;
                    break;
                }
            }
        }
        if (config == null)
        {
            config = new WifiConfiguration();
        }
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";
        // 分为三种情况：0没有密码1用wep加密2用wpa加密
        if (type == 0)
        {// WIFICIPHER_NOPASSwifiCong.hiddenSSID = false;
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        } else if (type == 1)
        {  //  WIFICIPHER_WEP
            config.hiddenSSID = true;
            config.wepKeys[0] = "\"" + password + "\"";
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers
                    .set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        } else if (type == 2)
        {   // WIFICIPHER_WPA
            config.preSharedKey = "\"" + password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }
        return config;
    }

    public static int getNetId(WifiConfiguration configuration)
    {
        int netId = configuration.networkId;
        Log.e("netId", netId + "");
        if (netId == -1)
        {
            netId = mWifiManager.addNetwork(configuration);
        }
        return netId;
    }

    /**
     * 连接
     *
     * @param configuration
     */
    public static void connect(WifiConfiguration configuration)
    {
        int netId = configuration.networkId;
        Log.e("netId", netId + "");
        if (netId == -1)
        {
            netId = mWifiManager.addNetwork(configuration);
        }
        mWifiManager.enableNetwork(netId, true);
    }
}
