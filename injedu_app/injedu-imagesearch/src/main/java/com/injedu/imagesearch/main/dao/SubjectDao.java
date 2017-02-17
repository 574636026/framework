package com.injedu.imagesearch.main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.injedu.imagesearch.main.entity.Subject;

/**
 * 
 *
 * @author joy.zhou
 * @date 2016年3月11日
 * @version 1.0
 *
 */
public class SubjectDao {

	/**
	 * 
	 * @param connection
	 * @return
	 * @throws Exception
	 */
	public List<Subject> getList(Connection connection) throws Exception {

		PreparedStatement pst = null;
		ResultSet rs = null;

		List<Subject> list = new ArrayList<Subject>();
		try {

			pst = connection.prepareStatement(
					"select id,subject_content,subject_option,subject_answer,subject_analysis,subject_analysis2 from vk_subject where subject_table = ?");

			pst.setString(1, "dezhi");

			rs = pst.executeQuery();

			while (rs.next()) {
				Subject subject = new Subject();
				subject.setId(rs.getLong("id"));
				subject.setContent(rs.getString("subject_content"));
				subject.setOption(rs.getString("subject_option"));
				subject.setAnswer(rs.getString("subject_answer"));
				subject.setAnalysis(rs.getString("subject_analysis"));
				subject.setAnalysis2(rs.getString("subject_analysis2"));
				list.add(subject);
			}

		} finally {

			if (pst != null)
				pst.close();

			if (rs != null) {
				rs.close();
			}
		}

		return list;
	}

}
