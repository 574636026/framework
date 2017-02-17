package com.injedu.easycode.maintain.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.injedu.easycode.maintain.context.EasyCodeContext;

/**
 * 文件操作辅助类
 * 
 * User: liyd Date: 13-12-16 Time: 下午5:11
 */
public class FileUtils {

	/** 日志对象 */
	private static final Logger LOG = LoggerFactory.getLogger(FileUtils.class);

	/** 模板文件map */
	private static WeakHashMap<String, String> fileMap = new WeakHashMap<String, String>();

	/**
	 * 得到模板文件
	 *
	 * @param templateName
	 * @return
	 */
	public static String getTemplate(String templateName) {

		try {
			if (fileMap.get(templateName) == null) {
				String encoding = EasyCodeContext.getConstant("encoding");

				BufferedReader bfReader = new BufferedReader(
						new InputStreamReader(ClassUtils.getDefaultClassLoader().getResourceAsStream(templateName),
								encoding == null ? "UTF-8" : encoding));

				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = bfReader.readLine()) != null) {

					sb.append(line);
					String lineSeparator = System.getProperty("line.separator");
					sb.append(lineSeparator);
				}
				fileMap.put(templateName, new String(sb.toString().getBytes(encoding), encoding));
			}

		} catch (IOException e) {
			LOG.error("读取模板文件失败[vm=" + templateName + "]", e);
		}
		return fileMap.get(templateName);
	}

	/**
	 * 写入生成的代码文件
	 *
	 * @param filePath
	 * @param source
	 */
	public static void writeFile(String filePath, String source) {

		try {
			File targetFile = new File(filePath);
			File targetDir = new File(filePath.substring(0, filePath.lastIndexOf("/")));
			if (!targetDir.exists())
				targetDir.mkdirs();

			OutputStreamWriter targetFileWriter = new OutputStreamWriter(new FileOutputStream(targetFile), "UTF-8");
			targetFileWriter.write(source);
			targetFileWriter.close();

			LOG.info("生成文件：" + targetFile.getAbsoluteFile());
		} catch (IOException e) {
			LOG.error("生成代码文件失败", e);
		}
	}

	public static String readInStream(InputStream inStream) {
		ByteArrayOutputStream outStream = null;
		try {
			outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[512];
			int length = -1;
			while ((length = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, length);
			}

			return outStream.toString();
		} catch (IOException e) {
			LOG.error("读取文件失败", e);
		} finally {
			try {
				inStream.close();
				outStream.close();
			} catch (IOException e) {
				LOG.error("读取文件失败", e);
			}
		}
		return null;
	}
}
