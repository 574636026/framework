package com.injedu.easycode.maintain.plugins;


import org.apache.velocity.VelocityContext;

import com.injedu.easycode.maintain.config.Table;
import com.injedu.easycode.maintain.config.Task;

/**
 * EasyCode插件接口
 * 
 * User: liyd
 * Date: 13-12-24
 * Time: 下午7:08
 */
public interface EasyCodePlugin {

    /**
     * 插件执行方法
     * 
     * @param table 表配置信息
     * @param task 任务配置信息
     * @param templateText 模板内容
     * @param context velocity 上下文
     */
    public void execute(Table table, Task task, StringBuilder templateText, VelocityContext context);
}
