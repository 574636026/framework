package com.injedu.core.tools;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.injedu.core.dao.BaseDao;
import com.injedu.core.domain.BaseSimpleDomain;
import com.injedu.core.dto.MergeDto;
import com.injedu.core.dto.pager.BasePagerDto;
import com.injedu.core.dto.wrapper.ApiPageResponseWrapper;
import com.injedu.core.exception.AppException;
import com.injedu.utils.log.LogUtils;

/**
 * 
 * 公共数据处理器
 *
 * @author joy.zhou
 * @date 2014年12月29日
 * @version 1.0
 *
 */
public class EntityTransform {

	/**
	 * 实体转换
	 * 
	 * @param source
	 *            源对象
	 * @param clazz
	 *            转换类型
	 * @return
	 */
	public static <T, K> K trans(T source, Class<K> clazz) {

		K result = null;
		try {
			result = clazz.newInstance();
			BeanUtils.copyProperties(source, result);
		} catch (Exception e) {
			LogUtils.e(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 实体转换
	 * 
	 * @param sources
	 *            源对象
	 * @param clazz
	 *            转换类型
	 * @return
	 */
	public static <T, K> List<K> trans(Collection<T> sources, Class<K> clazz) {

		List<K> results = new ArrayList<K>();

		if (sources == null) {
			return results;
		}

		for (T source : sources) {

			results.add(trans(source, clazz));
		}

		return results;

	}

	/**
	 * 
	 * 实体转为map
	 * 
	 * @param source
	 *            实体对象
	 * @param cls
	 *            转换类型
	 * @return
	 */
	public static <T> Map<String, Object> transMap(final T source) {

		final Map<String, Object> map = new HashMap<String, Object>();

		ReflectionUtils.doWithFields(source.getClass(), new ReflectionUtils.FieldCallback() {
			@Override
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {

				ReflectionUtils.makeAccessible(field);
				map.put(field.getName(), field.get(source));

			}
		}, ReflectionUtils.COPYABLE_FIELDS);

		return map;
	}

	public static <T extends BaseSimpleDomain> Map<Long, T> transMap(final List<T> sources) {
		Map<Long, T> map = new HashMap<>();
		if (sources != null && sources.size() > 0) {
			for (T entity : sources) {
				map.put(entity.getId(), entity);
			}
		}
		return map;
	}

	/**
	 * 转换api pager
	 * 
	 * @param pager
	 * @param cls
	 * @return
	 */
	public static <T> ApiPageResponseWrapper<T> transPager(BasePagerDto<T> pager) {

		ApiPageResponseWrapper<T> res = new ApiPageResponseWrapper<T>();
		if (pager == null) {
			return res;
		}
		res.setTotalPages(pager.getPager().getTotalPages());
		res.setTotalSize(pager.getPager().getTotalSize());
		res.setResult(pager.getResultList());

		return res;
	}

	/**
	 * 转换api pager
	 * 
	 * @param pager
	 * @param cls
	 * @return
	 */
	public static <T, K> ApiPageResponseWrapper<K> transPager(BasePagerDto<T> pager, Class<K> cls) {

		ApiPageResponseWrapper<K> res = new ApiPageResponseWrapper<K>();
		if (pager == null) {
			return res;
		}
		res.setTotalPages(pager.getPager().getTotalPages());
		res.setTotalSize(pager.getPager().getTotalSize());
		res.setResult(trans(pager.getResultList(), cls));

		return res;
	}

	/**
	 * 获取实体ID列表
	 * 
	 * @param list
	 *            实体列表
	 * @return
	 */
	public static <T extends BaseSimpleDomain> List<Long> transIds(List<T> list) {

		if (list == null || list.isEmpty()) {
			return null;
		}

		return Lists.transform(list, new Function<T, Long>() {
			@Override
			public Long apply(T entity) {
				return entity.getId();
			}
		});
	}

	/**
	 * 
	 * 合并数据处理(处理级联数据)
	 * 
	 * @param sources
	 *            源数据
	 * @param targets
	 *            目标数据
	 * @throws AppException
	 */
	public static <T extends BaseSimpleDomain> void meargeDataProcess(final Collection<T> sources,
			final Collection<T> targets, BaseDao<T> dao) {

		MergeDto<T> mergeDto = merge(sources, targets);
		// 处理删除数据
		if (mergeDto.getDeleteData() != null && !mergeDto.getDeleteData().isEmpty()) {

			for (T entity : mergeDto.getDeleteData()) {
				dao.remove(entity.getId());
			}

		}
		// 处理新增数据
		if (mergeDto.getSaveData() != null && !mergeDto.getSaveData().isEmpty()) {
			for (T entity : mergeDto.getSaveData()) {
				dao.save(entity);
			}
		}

	}

	/**
	 * 
	 * 求出需要删除与新增的数据
	 * 
	 * (使用contains方法比较实体,按需求重写equals方法)
	 * 
	 * @param source
	 *            源数据列表
	 * @param target
	 *            目标数据列表
	 * @return
	 */
	public static <T> MergeDto<T> merge(final Collection<T> sources, final Collection<T> targets) {

		MergeDto<T> dto = new MergeDto<T>();

		if (sources == null && targets == null) {
			return dto;
		}

		if (sources == null && targets != null) {
			dto.getSaveData().addAll(targets);
			return dto;
		}

		if (sources != null && targets == null) {
			dto.getDeleteData().addAll(sources);
			return dto;
		}

		// 需要删除的数据
		for (T source : sources) {
			if (!targets.contains(source)) {
				dto.getDeleteData().add(source);
			}
		}
		// 需要新增的时候
		for (T target : targets) {
			if (!sources.contains(target)) {
				dto.getSaveData().add(target);
			}
		}

		return dto;
	}

}
