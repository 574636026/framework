package com.injedu.easycode.maintain.database;

import org.apache.commons.lang.StringUtils;

import com.injedu.easycode.maintain.context.EasyCodeContext;
import com.injedu.easycode.maintain.context.EasyCodeException;

/**
 * 数据库操作者工厂类
 *
 * User: liyd
 * Date: 13-12-6
 * Time: 下午4:20
 */
public class DatabaseProviderFactory {

    /**
     * 获取数据库操作对象
     * 
     * @return
     */
    public static DatabaseProvider buildProvider() {

        String dialect = EasyCodeContext.getJdbcConfig("dialect");

        if (StringUtils.equalsIgnoreCase(dialect, "oracle")) {
            return new OracleProvider();
        } else if (StringUtils.equalsIgnoreCase(dialect, "mysql")) {
            return new MysqlProvider();
        }

        throw new EasyCodeException("没有找到对应的数据库操作类,dialect:" + dialect);
    }
}
