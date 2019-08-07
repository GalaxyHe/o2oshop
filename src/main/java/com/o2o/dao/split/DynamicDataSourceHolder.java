package com.o2o.dao.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author
 * @Date 2019/8/6
 * @Time 18:25
 * @Description TODO
 **/

public class DynamicDataSourceHolder {
    private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceHolder.class);
    //确保线程安全性
    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();
    public static final String DB_MASTER = "master";
    public static final String DB_SLAVE = "slave";


    public static String getDbType() {
        String db = contextHolder.get();
        if(db == null){
            db = DB_MASTER;
        }
        return db;
    }


     /**
      * @author He
      * @Description 设置线程的dbType
      * @Date
      * @Param
      * @return
      */
    public static void setDbType(String str){
        logger.debug("所使用的数据源为："+str);
        contextHolder.set(str);
    }

    /**
     * @author He
     * @Description 清理连接类型
     * @Date
     * @Param
     * @return
     */
    public static void clearDBType(){
        contextHolder.remove();
    }

}
