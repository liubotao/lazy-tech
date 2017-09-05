package com.whh.common.utils.enums;


import java.util.List;
import java.util.Map;

/**
 * 反枚举使用demo
 * Created by WJP on 2017/1/13.
 */
public class DemoEnum extends BasicEnum<Integer>{
    public static final DemoEnum DEMO1 = new DemoEnum("周一",1);
    public static final DemoEnum DEMO2 = new DemoEnum("周二",2);
    public static final DemoEnum DEMO3 = new DemoEnum("周三",3);
    public static final DemoEnum DEMO4 = new DemoEnum("周四",4);
    public static final DemoEnum DEMO5 = new DemoEnum("周五",5);
    public static final DemoEnum DEMO6 = new DemoEnum("周六",6);
    public static final DemoEnum DEMO7 = new DemoEnum("周七",7);

    protected DemoEnum(String name){
        super(name,null);
    }

    protected DemoEnum(String name, Integer value) {
        super(name, value);
    }

    /**
     * 根据name查找
     * @param name
     * @return
     */
    public static DemoEnum getEnum(String name) {
        return (DemoEnum) BasicEnum.getEnum(DemoEnum.class, name);
    }

    /**
     * 根据value查找
     * @param value
     * @return
     */
    public static DemoEnum getEnumByValue(Integer value) {
        return (DemoEnum) BasicEnum.getEnumByValue(DemoEnum.class, value);
    }

    /**
     * 查找当前类型的所有枚举类
     */
    public static List<DemoEnum> getEnumList(){
        return BasicEnum.getEnumList(DemoEnum.class);
    }

    /**
     * 查询当前类型所有的枚举类map
     * @return
     */
    public static Map getEnumMap(){
        return BasicEnum.getEnumMap(DemoEnum.class);
    }

}
