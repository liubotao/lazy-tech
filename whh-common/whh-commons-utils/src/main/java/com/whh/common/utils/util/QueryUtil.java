package com.whh.common.utils.util;

import com.whh.common.utils.db.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by huahui.wu on 2017/10/31.
 */
public class QueryUtil {
    private static Logger _log = LoggerFactory.getLogger(QueryUtil.class);

    public static  <T> T getIfForList(List<T> list, Predicate<? super T> filter){
        final Iterator<T> each = list.iterator();
        T t = null;
        while (each.hasNext()) {
            if (filter.test(t = each.next())) {
                break;
            }
        }
        return t;
    }

    public static boolean checkPageParam(BaseQuery baseQuery){
        if(baseQuery.getPage()<1 || baseQuery.getSize()<1){
            throw new IllegalArgumentException("page和size需大于0");
        }
        return true;
    }

    /**
     * 查询分页对象处理
     * <p>支持分页和排序功能<p/>
     * @param baseQuery 分页对象{@link BaseQuery}
     * @param page
     */
    public static void usePageQuery(BaseQuery baseQuery, PageQuery page){
        if (ObjectUtil.isNonNull(baseQuery)){
            checkPageParam(baseQuery);
            Integer offset = (baseQuery.getPage()-1)*baseQuery.getSize();
            page.limit(offset, baseQuery.getSize());
            if (StringUtils.isNotEmpty(baseQuery.getSort())){
                page.sort(baseQuery.getSort());
            }
        }
    }

    public interface PageQuery{
        void limit(Integer offset, Integer limit);
        void sort(String sort);
    }
}
