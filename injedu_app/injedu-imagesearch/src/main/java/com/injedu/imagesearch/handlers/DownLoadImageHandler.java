package com.injedu.imagesearch.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.injedu.imagesearch.entity.DownloadTask;
import com.injedu.imagesearch.utils.DownLoadUtils;

/**
 * 
 * 文件下载处理
 *
 * @author joy.zhou
 * @date 2016年3月11日
 * @version 1.0
 *
 */
public class DownLoadImageHandler {

	/** 日志对象 */
	private static final Logger LOG = LoggerFactory.getLogger(DownLoadImageHandler.class);

	private ExecutorService executor;

	public DownLoadImageHandler() {
		executor = Executors.newFixedThreadPool(5);
	}

	public DownLoadImageHandler(int poolSize) {
		executor = Executors.newFixedThreadPool(poolSize);
	}

	/**
	 * 批量下载
	 * 
	 * @param urls
	 *            地址列表
	 * @param dir
	 *            下载目录
	 */
	public void download(Collection<DownloadTask> tasks, final String dir) {

		if (tasks == null || tasks.isEmpty()) {
			return;
		}

		List<Future<DownloadTask>> results = new ArrayList<Future<DownloadTask>>();

		for (final DownloadTask task : tasks) {

			results.add(executor.submit(new DownLoadCall(task, dir)));

		}

		int success = 0, faild = 0;

		for (Future<DownloadTask> result : results) {

			try {
				DownloadTask task = result.get();
				success++;
				LOG.debug(task.toString());
			} catch (InterruptedException | ExecutionException e) {
				LOG.error(e.getMessage());
				faild++;
			}
		}

		LOG.info(String.format("下载结束：成功[%d],失败[%d]", success, faild));

		executor.shutdown();
	}

	public static void main(String[] args) {

		System.out.println(Runtime.getRuntime().availableProcessors());

	}
}

class DownLoadCall implements Callable<DownloadTask> {

	/** 下载器 */
	private DownLoadUtils loader = new DownLoadUtils();
	/** 下载内容 */
	private DownloadTask task;
	/** 下载目录 */
	private String dir;

	public DownLoadCall(DownloadTask task, String dir) {

		this.task = task;
		this.dir = dir;
	}

	@Override
	public DownloadTask call() throws Exception {

		try {

			loader.download(task.getUrl(), dir);
		} catch (Exception e) {
			throw new Exception(task.toString());
		}

		return task;
	}

}
