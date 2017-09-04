package com.whh.common.mybatis.base;

import com.whh.common.mybatis.db.DataSourceEnum;
import com.whh.common.mybatis.db.DynamicDataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * 实现BaseManager抽象类
 * Created by ZhangShuzheng on 2017/01/07.
 */
public abstract class BaseManagerImpl<Mapper, RecordDTO, RecordDO, Example> implements BaseManager<RecordDTO, Example> {

	public abstract Mapper getMapper();

	public RecordDO convertDO(RecordDTO fromDTO) {
        try {
            Class<RecordDO> clazz = (Class<RecordDO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
            RecordDO toDO = clazz.newInstance();
            BeanUtils.copyProperties(fromDTO, toDO);
            return toDO;
        } catch (InstantiationException e) {

        } catch (IllegalAccessException e) {

        }
        return null;
	}

	public RecordDTO convertDTO(RecordDO fromDO) {
        try {
            Class<RecordDTO> clazz = (Class<RecordDTO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
            RecordDTO toDTO = clazz.newInstance();
            BeanUtils.copyProperties(fromDO, toDTO);
            return toDTO;
        } catch (InstantiationException e) {

        } catch (IllegalAccessException e) {

        }
        return null;
	}

    protected List<RecordDTO> convertDTOList(List<RecordDO> doList){
        List<RecordDTO> dtolist = new ArrayList<>();
        if (doList == null) {
            return dtolist;
        }
        for (RecordDO recordDO : doList) {
            RecordDTO recordDTO = convertDTO(recordDO);
            dtolist.add(recordDTO);
        }
        return dtolist;
    }

	@Override
	public int countByExample(Example example) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
			Method countByExample = getMapper().getClass().getDeclaredMethod("countByExample", example.getClass());
			Object result = countByExample.invoke(getMapper(), example);
			return Integer.parseInt(String.valueOf(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
		DynamicDataSource.clearDataSource();
		return 0;
	}

	@Override
	public int deleteByExample(Example example) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
			Method deleteByExample = getMapper().getClass().getDeclaredMethod("deleteByExample", example.getClass());
			Object result = deleteByExample.invoke(getMapper(), example);
			return Integer.parseInt(String.valueOf(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
		DynamicDataSource.clearDataSource();
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
			Method deleteByPrimaryKey = getMapper().getClass().getDeclaredMethod("deleteByPrimaryKey", id.getClass());
			Object result = deleteByPrimaryKey.invoke(getMapper(), id);
			return Integer.parseInt(String.valueOf(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
		DynamicDataSource.clearDataSource();
		return 0;
	}

	@Override
	public int insert(RecordDTO recordDTO) {
		try {
			RecordDO record = convertDO(recordDTO);;

			DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
			Method insert = getMapper().getClass().getDeclaredMethod("insert", record.getClass());
			Object result = insert.invoke(getMapper(), record);
			return Integer.parseInt(String.valueOf(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
		DynamicDataSource.clearDataSource();
		return 0;
	}

	@Override
	public int insertSelective(RecordDTO recordDTO) {
		try {
			RecordDO record = convertDO(recordDTO);
			DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
			Method insertSelective = getMapper().getClass().getDeclaredMethod("insertSelective", record.getClass());
			Object result = insertSelective.invoke(getMapper(), record);
			return Integer.parseInt(String.valueOf(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
		DynamicDataSource.clearDataSource();
		return 0;
	}

	@Override
	public List<RecordDTO> selectByExampleWithBLOBs(Example example) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
			Method selectByExampleWithBLOBs = getMapper().getClass().getDeclaredMethod("selectByExampleWithBLOBs", example.getClass());
			Object result = selectByExampleWithBLOBs.invoke(getMapper(), example);
            return convertDTOList((List<RecordDO>) result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		DynamicDataSource.clearDataSource();
		return null;
	}

	@Override
	public List<RecordDTO> selectByExample(Example example) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
			Method selectByExample = getMapper().getClass().getDeclaredMethod("selectByExample", example.getClass());
			Object result = selectByExample.invoke(getMapper(), example);
			return convertDTOList((List<RecordDO>) result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		DynamicDataSource.clearDataSource();
		return null;
	}

	@Override
	public RecordDTO selectFirstByExample(Example example) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
			Method selectByExample = getMapper().getClass().getDeclaredMethod("selectByExample", example.getClass());
			List<RecordDO> result = (List<RecordDO>) selectByExample.invoke(getMapper(), example);
			if (null != result && result.size() > 0) {
				return convertDTO(result.get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		DynamicDataSource.clearDataSource();
		return null;
	}

	@Override
	public RecordDTO selectFirstByExampleWithBLOBs(Example example) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
			Method selectByExampleWithBLOBs = getMapper().getClass().getDeclaredMethod("selectByExampleWithBLOBs", example.getClass());
			List<RecordDO> result = (List<RecordDO>) selectByExampleWithBLOBs.invoke(getMapper(), example);
			if (null != result && result.size() > 0) {
				return convertDTO(result.get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		DynamicDataSource.clearDataSource();
		return null;
	}

	@Override
	public RecordDTO selectByPrimaryKey(Long id) {
		try {
			DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
			Method selectByPrimaryKey = getMapper().getClass().getDeclaredMethod("selectByPrimaryKey", id.getClass());
			Object result = selectByPrimaryKey.invoke(getMapper(), id);
			return convertDTO((RecordDO) result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		DynamicDataSource.clearDataSource();
		return null;
	}

	@Override
	public int updateByExampleSelective(@Param("record") RecordDTO recordDTO, @Param("example") Example example) {
		try {
            RecordDO record = convertDO(recordDTO);
			DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
			Method updateByExampleSelective = getMapper().getClass().getDeclaredMethod("updateByExampleSelective", record.getClass(), example.getClass());
			Object result = updateByExampleSelective.invoke(getMapper(), record, example);
			return Integer.parseInt(String.valueOf(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
		DynamicDataSource.clearDataSource();
		return 0;
	}

	@Override
	public int updateByExampleWithBLOBs(@Param("record") RecordDTO recordDTO, @Param("example") Example example) {
		try {
            RecordDO record = convertDO(recordDTO);
			DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
			Method updateByExampleWithBLOBs = getMapper().getClass().getDeclaredMethod("updateByExampleWithBLOBs", record.getClass(), example.getClass());
			Object result = updateByExampleWithBLOBs.invoke(getMapper(), record, example);
			return Integer.parseInt(String.valueOf(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
		DynamicDataSource.clearDataSource();
		return 0;
	}

	@Override
	public int updateByExample(@Param("record") RecordDTO recordDTO, @Param("example") Example example) {
		try {
            RecordDO record = convertDO(recordDTO);
			DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
			Method updateByExample = getMapper().getClass().getDeclaredMethod("updateByExample", record.getClass(), example.getClass());
			Object result = updateByExample.invoke(getMapper(), record, example);
			return Integer.parseInt(String.valueOf(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
		DynamicDataSource.clearDataSource();
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(RecordDTO recordDTO) {
		try {
            RecordDO record = convertDO(recordDTO);
			DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
			Method updateByPrimaryKeySelective = getMapper().getClass().getDeclaredMethod("updateByPrimaryKeySelective", record.getClass());
			Object result = updateByPrimaryKeySelective.invoke(getMapper(), record);
			return Integer.parseInt(String.valueOf(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
		DynamicDataSource.clearDataSource();
		return 0;
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(RecordDTO recordDTO) {
		try {
            RecordDO record = convertDO(recordDTO);
			DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
			Method updateByPrimaryKeyWithBLOBs = getMapper().getClass().getDeclaredMethod("updateByPrimaryKeyWithBLOBs", record.getClass());
			Object result = updateByPrimaryKeyWithBLOBs.invoke(getMapper(), record);
			return Integer.parseInt(String.valueOf(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
		DynamicDataSource.clearDataSource();
		return 0;
	}

	@Override
	public int updateByPrimaryKey(RecordDTO recordDTO) {
		try {
            RecordDO record = convertDO(recordDTO);
			DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
			Method updateByPrimaryKey = getMapper().getClass().getDeclaredMethod("updateByPrimaryKey", record.getClass());
			Object result = updateByPrimaryKey.invoke(getMapper(), record);
			return Integer.parseInt(String.valueOf(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
		DynamicDataSource.clearDataSource();
		return 0;
	}

	@Override
	public int deleteByPrimaryKeys(String ids) {
		try {
			if (StringUtils.isBlank(ids)) {
				return 0;
			}
			DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
			String[] idArray = ids.split("-");
			int count = 0;
			for (String idStr : idArray) {
				if (StringUtils.isBlank(idStr)) {
					continue;
				}
				Integer id = Integer.parseInt(idStr);
				Method deleteByPrimaryKey = getMapper().getClass().getDeclaredMethod("deleteByPrimaryKey", id.getClass());
				Object result = deleteByPrimaryKey.invoke(getMapper(), id);
				count += Integer.parseInt(String.valueOf(result));
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		}
		DynamicDataSource.clearDataSource();
		return 0;
	}

	/**
	 * 获取类泛型class
	 * @return
	 */
	public Class<Mapper> getMapperClass() {
		return (Class<Mapper>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
}