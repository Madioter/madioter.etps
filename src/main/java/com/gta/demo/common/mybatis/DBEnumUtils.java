package com.gta.demo.common.mybatis;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by yi.wang1 on 2015/5/12.
 */
public class DBEnumUtils {
    public static Object getEnumInstance(Class type, int i) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = type.getMethod("get", new Class[]{int.class});
        return m.invoke(type, new Object[]{i});
    }
}
