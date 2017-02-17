package com.injedu.imagesearch;

import com.injedu.imagesearch.context.DownLoadContext;

/**
 * 
 *
 * @author joy.zhou
 * @date 2016年3月11日
 * @version 1.0
 *
 */
public class MainExecute {

	public static void main(String[] args) throws Exception {

		// 下载目录
		String downloadDir = System.getProperty("user.dir");
		// 线程数
		int poolSize = 5;

		if (args.length == 1) {
			downloadDir = args[0];
			poolSize = Runtime.getRuntime().availableProcessors() * 5;
		}

		if (args.length == 2) {
			downloadDir = args[0];
			poolSize = Integer.parseInt(args[1]);
		}

		DownLoadContext context = new DownLoadContext();

		context.execute(poolSize, downloadDir);

	}

}
