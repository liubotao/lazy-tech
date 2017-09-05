package com.whh.common.utils.db;

import java.util.Objects;

/**
 * Created by jingchun.zhang on 2017/8/10.
 */
public class ObjectUtil {

    /**
     * 判断是否存在空对象
     * @param objects
     * @return
     */
    public static boolean hasNull(Object ...objects ){
        for (Object o : objects){
            if (Objects.isNull(o)){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断所有元素都不为空
     * @param objects
     * @return
     */
    public static boolean isNonNull(Object ...objects ){
        return !hasNull(objects);
    }
}
