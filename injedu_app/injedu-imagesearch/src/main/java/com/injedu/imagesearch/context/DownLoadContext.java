package com.injedu.imagesearch.context;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.injedu.imagesearch.entity.DownloadTask;
import com.injedu.imagesearch.handlers.DownLoadImageHandler;
import com.injedu.imagesearch.main.entity.Subject;
import com.injedu.imagesearch.main.service.SubjectService;

/**
 * 
 *
 * @author joy.zhou
 * @date 2016年3月12日
 * @version 1.0
 *
 */
public class DownLoadContext {

	/** 日志对象 */
	private static final Logger LOG = LoggerFactory.getLogger(DownLoadContext.class);

	public void execute(int poolSize, String downloadDir) throws Exception {

		// 图片下载处理
		DownLoadImageHandler handler = new DownLoadImageHandler(poolSize);

		LOG.info("获取数据中...");

		Collection<DownloadTask> tasks = getDownLoadPath();

		LOG.info("开始下载..");

		LOG.info(String.format("下载目录：%s", downloadDir));

		LOG.info(String.format("下载数量：%d", tasks.size()));

		handler.download(tasks, downloadDir);
	}

	/**
	 * 获取需要下载的图片数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public Collection<DownloadTask> getDownLoadPath() throws Exception {

		SubjectService service = new SubjectService();

		Map<String, DownloadTask> maps = new LinkedHashMap<String, DownloadTask>();

		for (Subject entity : service.getList()) {

			maps.putAll(entity.genTasks());

		}

		return maps.values();
	}

}
