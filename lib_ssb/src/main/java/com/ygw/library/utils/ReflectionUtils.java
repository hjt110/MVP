package com.ygw.library.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ReflectionUtils {

    /**
     * 工具方法入口
     *
     * @param target
     * @param methodName
     * @param args
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T methodInvoker(Object target, String methodName, Object[] args) throws Exception {
        Object result = null;
        Class clazz = target.getClass();
        Class[] argtypes = new Class[0];
        if (args != null) {
            argtypes = new Class[args.length];
            ArrayList invoker = new ArrayList();
            int argLength = args.length;
            for (int i = 0; i < argLength; i++) {
                Object arg = args[i];
                invoker.add(arg == null ? null : arg.getClass());
            }
            invoker.toArray(argtypes);
        }
        Method method = getMethod(clazz, methodName, argtypes);
        return method == null ? null : (T) method.invoke(target, args);
    }

    /**
     * 获取对象的属性
     *
     * @param fieldName
     * @param target
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T getFieldValueByName(String fieldName, Object target) throws Exception {
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + fieldName.substring(1);
        Method method = target.getClass().getMethod(getter, new Class[0]);
        Object e = method.invoke(target, new Object[0]);
        return (T) e;
    }

    /**
     * 获取所有字段名字
     *
     * @param target
     * @return
     */
    public static String[] getFiledName(Object target) throws Exception {
        Field[] fields = target.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; ++i) {
            System.out.println(fields[i].getType());
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 获取所有属性的值
     *
     * @param target
     * @return
     * @throws Exception
     */
    public static Object[] getFiledValues(Object target) throws Exception {
        String[] fieldNames = getFiledName(target);
        Object[] value = new Object[fieldNames.length];
        for (int i = 0; i < fieldNames.length; ++i) {
            value[i] = getFieldValueByName(fieldNames[i], target);
        }
        return value;
    }

    /**
     * 递归获取方法引用
     *
     * @param target
     * @param methodName
     * @param argTypes
     * @return
     */
    private static Method getMethod(Class<?> target, String methodName, Class<?>[] argTypes) {
        Method method = null;
        try {
            method = target.getDeclaredMethod(methodName, argTypes);
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            method = getCatchMethod(target, methodName, argTypes);
        }
        if (method == null && target != Object.class) {
            return getMethod(target.getSuperclass(), methodName, argTypes);
        }
        return method;
    }

    /**
     * 当含有基础类型抛出NoSuchMethodException异常循环所有方法
     *
     * @param target
     * @param methodName
     * @param argTypes
     * @return
     */
    private static Method getCatchMethod(Class<?> target, String methodName, Class<?>[] argTypes) {
        Method method = null;
        Method[] methods = target.getDeclaredMethods();
        int methodsLength = methods.length;
        for (int i = 0; i < methodsLength; i++) {
            Method methodTmp = methods[i];
            int argsLength = methodTmp.getParameterTypes() == null ? 0 : methodTmp.getParameterTypes().length;
            if (methodTmp.getName().equals(methodName) && argsLength == argTypes.length) {
                methodTmp.setAccessible(true);
                method = methodTmp;
                break;
            }
        }
        return method;
    }
}