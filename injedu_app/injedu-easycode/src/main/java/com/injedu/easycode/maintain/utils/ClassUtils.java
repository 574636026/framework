package com.injedu.easycode.maintain.utils;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.injedu.easycode.maintain.generator.EasyCodeGenerator;
import com.injedu.easycode.maintain.plugins.EasyCodePlugin;

/**
 * class辅助类
 * 
 * User: liyd
 * Date: 13-12-16
 * Time: 下午4:13
 */
public class ClassUtils {

    /** 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(ClassUtils.class);

    /**
     * 获取当前线程的classLoader
     * 
     * @return
     */
    public static ClassLoader getDefaultClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 获取生成代码类的实例
     * 
     * @param className
     * @return
     */
    public static EasyCodeGenerator getGeneratorInstance(String className) {

        try {
            Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            return (EasyCodeGenerator) clazz.newInstance();
        } catch (Exception e) {
            LOG.info("初始化类{}失败", className);
            return null;
        }
    }

    /**
     * 获取生成代码类的实例
     *
     * @param className
     * @return
     */
    public static EasyCodePlugin getPluginInstance(String className) {

        try {
            Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            return (EasyCodePlugin) clazz.newInstance();
        } catch (Exception e) {
            LOG.info("初始化类{}失败", className);
            return null;
        }
    }
}
