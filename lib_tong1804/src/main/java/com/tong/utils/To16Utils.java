package com.tong.utils;

/**
 * Created by ZD-104 on 2017/10/26.
 */

public class To16Utils
{
    /**
     * 字符串转换成十六进制
     */
    public static byte[] hexStringToBytes(String hexString)
    {
        hexString = hexString.replace(" ", "");//去掉所有空格，包括收尾、中间
        if (hexString == null || hexString.equals(""))
        {
            return null;
        }
        // toUpperCase将字符串中的所有字符转换为大写
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        // toCharArray将此字符串转换为一个新的字符数组。
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++)
        {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    //charToByte返回在指定字符的第一个发生的字符串中的索引，即返回匹配字符
    private static byte charToByte(char c)
    {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


    /**
     * 将 byte 数组转化为十六进制字符串
     *
     * @param bytes byte[] 数组
     * @param begin 起始位置
     * @param end   结束位置
     * @return byte 数组的十六进制字符串表示
     */
    public static String bytesToHex(byte[] bytes, int begin, int end)
    {
        StringBuilder hexBuilder = new StringBuilder(2 * (end - begin));
        for (int i = begin; i < end; i++)
        {
            hexBuilder.append(Character.forDigit((bytes[i] & 0xF0) >> 4, 16)); // 转化高四位
            hexBuilder.append(Character.forDigit((bytes[i] & 0x0F), 16)); // 转化低四位
            hexBuilder.append(' '); // 加一个空格将每个字节分隔开
        }
        return hexBuilder.toString().toUpperCase();
    }

}
