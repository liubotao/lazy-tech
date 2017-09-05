package com.whh.common.utils.db;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Created by jingchun.zhang on 2017/7/13.
 *  mybatis util
 */
@Component
public class MyBatisUtil {

    @Resource
    private SqlSessionFactory ccSqlSessionFactory;

    /**
     * sql批量处理
     * @param data
     * @param biFunction
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> int sqlBatch(final List<T> data, final BiFunction<T, SqlSession, Integer> biFunction) throws Exception{
        int count = 0;
        try(SqlSession batchSqlSession = ccSqlSessionFactory.openSession(ExecutorType.SIMPLE, false)) {
            for (T t : data) {
                count += biFunction.apply(t, batchSqlSession);
            }
            batchSqlSession.commit();
        }
        return count;
    }
}
