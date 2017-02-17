package com.injedu.mvc.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.injedu.core.domain.BaseSimpleDomain;
import com.injedu.core.dto.wrapper.TableRequestWrapper;
import com.injedu.core.service.IBaseService;

/**
 * 
 * 增删改查controller,继承此类则拥有crud功能
 * 
 * @author joy.zhou
 * @date 2015年12月1日
 * @version 1.0
 *
 */
public abstract class BaseCRUDController<T extends BaseSimpleDomain, M extends T> extends BaseSecurityController {

	/**
	 * 默认操作service
	 * 
	 * @return
	 */
	public abstract IBaseService<T> getService();

	/**
	 * 
	 * 获取列表
	 * 
	 * @param query
	 *            查询参数
	 * @param pager
	 *            分页参数
	 * @param order
	 *            排序参数
	 * 
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public Object list(@ModelAttribute TableRequestWrapper entity) {

		return getService().getListByPage(entity.getQuery(), entity.getPager(), entity.getOrder());
	}

	/**
	 * 
	 * 查询详情
	 * 
	 * @param id
	 *            ID
	 */
	@RequestMapping(value = "get/{id}", method = RequestMethod.GET)
	public Object detail(@PathVariable Long id) {
		return getService().getById(id);
	}

	/**
	 * 新增
	 * 
	 * @param model
	 *            数据
	 * @param operator
	 *            操作人
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Object save(@Validated M model) {

		getService().save(model, getOperationId());
		return success();
	}

	/**
	 * 编辑
	 * 
	 * @param id
	 *            ID
	 * @param model
	 *            数据
	 */
	@RequestMapping(value = "edit/{id}", method = RequestMethod.POST)
	public Object edit(@PathVariable Long id, @Validated M model) {

		model.setId(id);
		getService().update(model, getOperationId());
		return success();
	}

	/**
	 * 标记删除
	 * 
	 * @param ids
	 *            ID数组
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public Object delete(@RequestParam Long[] ids) {

		if (ids != null && ids.length > 0) {

			getService().delete(ids);

		}
		return success();
	}

	/**
	 * 物理删除
	 * 
	 * @param ids
	 *            ID数组
	 */
	@RequestMapping(value = "remove", method = RequestMethod.POST)
	public Object remove(@RequestParam Long[] ids) {

		if (ids != null && ids.length > 0) {

			getService().remove(ids);

		}
		return success();
	}

}
