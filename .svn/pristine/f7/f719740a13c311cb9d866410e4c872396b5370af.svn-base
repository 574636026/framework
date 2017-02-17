package com.injedu.imagesearch.main.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;

import com.injedu.imagesearch.constant.AppConst;
import com.injedu.imagesearch.entity.DownloadTask;
import com.mysql.jdbc.StringUtils;

/**
 * 
 * @author joy.zhou
 * @date 2016年3月12日
 * @version 1.0
 *
 */
public class Subject implements Serializable {

	private static final long serialVersionUID = -2011564197270232799L;

	private Long id;

	private String content;

	private String option;

	private String answer;

	private String analysis;

	private String analysis2;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getAnalysis() {
		return analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}

	public String getAnalysis2() {
		return analysis2;
	}

	public void setAnalysis2(String analysis2) {
		this.analysis2 = analysis2;
	}

	public Map<String, DownloadTask> genTasks() {

		Map<String, DownloadTask> tasks = new LinkedHashMap<String, DownloadTask>();

		genTask(tasks, this.content, "subject_content");
		genTask(tasks, this.option, "subject_option");
		genTask(tasks, this.answer, "subject_content");
		genTask(tasks, this.analysis, "subject_analysis");
		genTask(tasks, this.analysis2, "subject_analysis2");

		return tasks;
	}

	private void genTask(Map<String, DownloadTask> tasks, String content, String column) {

		if (StringUtils.isNullOrEmpty(content)) {
			return;
		}

		Matcher matcher = AppConst.PATTERN_IMAGE_SRC.matcher(content);

		while (matcher.find()) {

			DownloadTask task = new DownloadTask();
			task.setId(id);
			task.setColumn(column);

			String url = matcher.group(1);

			if (url.startsWith("http://") || url.startsWith("file:") || url.startsWith("data:")) {
				task.setUrl(url);
			} else {
				if (url.startsWith("/")) {
					task.setUrl(AppConst.NETROOT_DEZHI + url);
				} else if (url.startsWith("../")) {
					task.setUrl(AppConst.NETROOT_DEZHI + "/" + url.replace("../", ""));
				} else {
					task.setUrl(AppConst.NETROOT_DEZHI + "/" + url);
				}
			}

			tasks.put(task.getUrl(), task);
		}

	}

}
