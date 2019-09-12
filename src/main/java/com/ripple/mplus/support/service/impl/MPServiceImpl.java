package com.ripple.mplus.support.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.enums.SqlMethod;
import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.toolkit.ReflectionKit;
import com.ripple.mplus.support.service.IMPService;

/**
 * 类MPServiceImpl.java的实现描述：mp service实现
 * 
 * @author wanglei 2018年4月17日 上午10:28:06
 */
public class MPServiceImpl<M extends BaseMapper<T>, T> implements IMPService<T> {

	@Autowired
	protected M baseMapper;

	/**
	 * <p>
	 * 判断数据库操作是否成功
	 * </p>
	 * <p>
	 * 注意！！ 该方法为 Integer 判断，不可传入 int 基本类型
	 * </p>
	 *
	 * @param result
	 *            数据库操作返回影响条数
	 * @return boolean
	 */
	protected static boolean retBool(Integer result) {
		return SqlHelper.retBool(result);
	}

	@SuppressWarnings("unchecked")
	protected Class<T> currentModelClass() {
		return ReflectionKit.getSuperClassGenricType(getClass(), 1);
	}

	/**
	 * <p>
	 * 批量操作 SqlSession
	 * </p>
	 */
	protected SqlSession sqlSessionBatch() {
		return SqlHelper.sqlSessionBatch(currentModelClass());
	}

	/**
	 * 获取SqlStatement
	 *
	 * @param sqlMethod
	 * @return
	 */
	protected String sqlStatement(SqlMethod sqlMethod) {
		return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
	}

	@Override
	public boolean insert(T entity) {
		return retBool(baseMapper.insert(entity));
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean insertBatch(List<T> entityList) {
		return insertBatch(entityList, 30);
	}

	/**
	 * 批量插入
	 *
	 * @param entityList
	 * @param batchSize
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean insertBatch(List<T> entityList, int batchSize) {
		if (CollectionUtils.isEmpty(entityList)) {
			throw new IllegalArgumentException("Error: entityList must not be empty");
		}
		try (SqlSession batchSqlSession = sqlSessionBatch()) {
			int size = entityList.size();
			String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
			for (int i = 0; i < size; i++) {
				batchSqlSession.insert(sqlStatement, entityList.get(i));
				if (i >= 1 && i % batchSize == 0) {
					batchSqlSession.flushStatements();
				}
			}
			batchSqlSession.flushStatements();
		} catch (Throwable e) {
			throw new MybatisPlusException("Error: Cannot execute insertBatch Method. Cause", e);
		}
		return true;
	}

	@Override
	public boolean deleteById(Serializable id) {
		return SqlHelper.delBool(baseMapper.deleteById(id));
	}

	@Override
	public boolean delete(Wrapper<T> wrapper) {
		return SqlHelper.delBool(baseMapper.delete(wrapper));
	}

	@Override
	public boolean deleteBatchIds(Collection<? extends Serializable> idList) {
		return SqlHelper.delBool(baseMapper.deleteBatchIds(idList));
	}

	@Override
	public boolean updateById(T entity) {
		return retBool(baseMapper.updateById(entity));
	}

	@Override
	public boolean updateByWrapper(T entity, Wrapper<T> wrapper) {
		return retBool(baseMapper.update(entity, wrapper));
	}

	@Override
	public T selectById(Serializable id) {
		return baseMapper.selectById(id);
	}

	@Override
	public List<T> selectBatchIds(Collection<? extends Serializable> idList) {
		return baseMapper.selectBatchIds(idList);
	}

	@Override
	public T selectOne(Wrapper<T> wrapper) {
		return SqlHelper.getObject(baseMapper.selectList(wrapper));
	}

	@Override
	public int selectCount(Wrapper<T> wrapper) {
		return SqlHelper.retCount(baseMapper.selectCount(wrapper));
	}

	@Override
	public List<T> selectList(Wrapper<T> wrapper) {
		return baseMapper.selectList(wrapper);
	}

	@Override
	public Page<T> selectPage(Page<T> page) {
		return selectPage(page, Condition.EMPTY);
	}

	@Override
	public Page<T> selectPage(Page<T> page, Wrapper<T> wrapper) {
		wrapper = (Wrapper<T>) SqlHelper.fillWrapper(page, wrapper);
		page.setRecords(baseMapper.selectPage(page, wrapper));
		return page;
	}

}
