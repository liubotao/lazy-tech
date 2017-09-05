package com.whh.common.utils.enums;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.enums.Enum;
import org.apache.commons.lang.enums.EnumUtils;

import java.util.*;

/**
 *
 * @Title 支持多形式的枚举基类
 *
 * @author WJP
 */
public abstract class BasicEnum<T extends Object> extends Enum {
    private final T value;

    protected BasicEnum(String name, T value) {
        super(name);
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    /**
     * 根据token查找
     * @param enumClass
     * @param token
     * @return
     */
    @SuppressWarnings("rawtypes")
    protected static Enum getEnum(Class enumClass, String token) {
        return Enum.getEnum(enumClass, token);
    }
    /**
     * 根据value查找
     * @param enumClass
     * @param value
     * @return
     */
    @SuppressWarnings("rawtypes")
    protected static Enum getEnumByValue(Class enumClass, Object value) {

        List list = Enum.getEnumList(enumClass);

        for (Iterator it = list.iterator(); it.hasNext();) {

            BasicEnum enumeration = (BasicEnum) it.next();
            if (enumeration.getValue() == value || value.equals(enumeration.getValue())) {
                return enumeration;
            }
        }
        return null;
    }

    /**
     * 查询当前类型所有的枚举类map
     * @param enumClass
     * @return
     */
    @SuppressWarnings("rawtypes")
    protected static Map getEnumMap(Class enumClass) {
          HashMap map = new HashMap();
          Iterator itr = EnumUtils.iterator(enumClass);
          while(itr.hasNext()) {
              BasicEnum enm = (BasicEnum) itr.next();
            map.put(enm.getName(), enm.getValue());
          }
          return map;
    }

    /**
     * 查询当前类型所有的枚举类list
     * @param enumClass
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List getEnumListByEnum(Class enumClass) {
         return new ArrayList( EnumSet.allOf(enumClass) );
    }

    @SuppressWarnings("rawtypes")
    public static Iterator iterator(Class enumClass) {
        return EnumUtils.getEnumList(enumClass).iterator();
    }


    public String toString() {
        if (iToString == null) {
            String shortName = ClassUtils.getShortClassName(getEnumClass());
            iToString = shortName + "[" + getName() + ", " + value + "]";
        }
        return iToString;
    }

}
