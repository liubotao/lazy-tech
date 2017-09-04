package com.whh.common.mybatis.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by chunchun.xu on 2017/6/9.
 */
public interface BaseDao<Model,Example> {
	long countByExample(Example example);

	int deleteByExample(Example example);

	int deleteByPrimaryKey(Long userId);

	int insert(Model record);

	int insertSelective(Model record);

	List<Model> selectByExample(Example example);

	Model selectByPrimaryKey(Long userId);

	int updateByExampleSelective(@Param("record") Model record, @Param("example") Example example);

	int updateByExample(@Param("record") Model record, @Param("example") Example example);

	int updateByPrimaryKeySelective(Model record);

	int updateByPrimaryKey(Model record);
}
