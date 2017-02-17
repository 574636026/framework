package com.injedu.imagesearch.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 
 * 文件下载处理
 *
 * @author joy.zhou
 * @date 2016年3月11日
 * @version 1.0
 *
 */
public class DownLoadUtils {

	/**
	 * 
	 * 下载文件
	 * 
	 * @param urlPath
	 *            url地址
	 * @param dir
	 *            下载目录
	 */
	public void download(String urlPath, String dir) throws Exception {

		download(urlPath, dir, false);

	}

	/**
	 * 
	 * 下载文件
	 * 
	 * @param urlPath
	 *            url地址
	 * @param dir
	 *            下载目录
	 * @param ingore
	 *            是否忽略已经存在的文件
	 * @throws Exception
	 */
	public void download(String urlPath, String dir, boolean ingore) throws Exception {

		if (urlPath == null || "".equals(urlPath)) {
			return;
		}
		// 构造URL
		URL url = new URL(urlPath);
		// 打开连接
		URLConnection con = url.openConnection();
		// 设置请求超时为3s
		con.setConnectTimeout(5 * 1000);
		con.setRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322)");

		InputStream is = null;
		OutputStream os = null;

		try {
			// 输入流
			is = con.getInputStream();

			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			String path = url.getPath();
			// 输出的文件流
			String fileName = path.substring(path.lastIndexOf("/") + 1);
			String fileDir = dir + path.substring(0, path.lastIndexOf("/")).replace("/", File.separator);
			File sf = new File(fileDir);
			if (!sf.exists()) {
				sf.mkdirs();
			}
			File loadFile = new File(fileDir + File.separator + fileName);
			if (ingore && loadFile.exists()) {
				return;
			}
			os = new FileOutputStream(loadFile);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
		} finally {

			// 完毕，关闭所有链接
			if (is != null)
				is.close();
			if (os != null)
				os.close();
		}

	}
}
