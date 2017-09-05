package com.whh.common.utils.enums;

import java.lang.reflect.Method;

/**
 * @author jingchun.zhang
 * @version 1.0.0
 * @date 2017/9/1
 *
 *  enum util class
 */
public class EnumUtils {

    public static <T extends Enum<T>,E> T getEnumByCode(Class<T> clazz, E code){
        return getEnumByCode(clazz, code, "getCode");
    }

    public static <T extends Enum<T>,E> T getEnumByCode(Class<T> clazz, E code, String getCodeMethodName){
        T result = null;
        try{
            T[] arr = clazz.getEnumConstants();
            Method targetMethod = clazz.getDeclaredMethod(getCodeMethodName);
            String typeCodeVal = null;
            for(T entity:arr){
                typeCodeVal = targetMethod.invoke(entity).toString();
                if(typeCodeVal.contentEquals(String.valueOf(code))){
                    result = entity;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
