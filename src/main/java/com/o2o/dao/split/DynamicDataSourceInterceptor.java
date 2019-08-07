package com.o2o.dao.split;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Properties;

/**
 * @author He
 * @Date 2019/8/6
 * @Time 18:34
 * @Description 拦截器，用于区分使用主库还是从库数据源
 **/

@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class})})
public class DynamicDataSourceInterceptor implements Interceptor {
    //写操作的正则表达式
    private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020";
    private Logger logger = LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //标识是否交由spring进行事务管理
        boolean synchronizationActive = TransactionSynchronizationManager.isActualTransactionActive();
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        String lookupKey = DynamicDataSourceHolder.DB_MASTER;
        if (!synchronizationActive) {
            //读方法
            if (mappedStatement.getSqlCommandType().equals(SqlCommandType.SELECT)) {
                if (mappedStatement.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
                    lookupKey = DynamicDataSourceHolder.DB_MASTER;
                } else {
                    BoundSql boundSql = mappedStatement.getBoundSql(args[1]);
                    String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", " ");
                    if (sql.matches(REGEX)) {
                        lookupKey = DynamicDataSourceHolder.DB_MASTER;
                    } else {
                        lookupKey = DynamicDataSourceHolder.DB_SLAVE;
                    }
                }
            }
        } else {
            lookupKey = DynamicDataSourceHolder.DB_MASTER;
        }

        logger.debug("设置方法[{}] use [{}] Strategy,SqlCommanType [{}]...", mappedStatement.getId(), lookupKey,
                mappedStatement.getSqlCommandType().name());
        DynamicDataSourceHolder.setDbType(lookupKey);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
