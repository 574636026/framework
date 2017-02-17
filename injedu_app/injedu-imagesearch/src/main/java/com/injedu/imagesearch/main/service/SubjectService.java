package com.injedu.imagesearch.main.service;

import java.sql.Connection;
import java.util.List;

import com.injedu.imagesearch.main.dao.SubjectDao;
import com.injedu.imagesearch.main.entity.Subject;
import com.injedu.imagesearch.utils.DBUtils;

/**
 * 
 *
 * @author joy.zhou
 * @date 2016年3月11日
 * @version 1.0
 *
 */
public class SubjectService {

	private SubjectDao subjectDao = new SubjectDao();

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Subject> getList() throws Exception {

		Connection conn = null;

		try {

			conn = DBUtils.getDefaultConnection();

			return subjectDao.getList(conn);

		} finally {

			DBUtils.closeConnection();
		}

	}

}
