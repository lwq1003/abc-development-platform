package tech.abc.platform.framework.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.abc.platform.common.utils.UserUtil;


/**
 * MyBatisPlus配置
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Configuration
@Slf4j
public class MybatisPlusConfig {


    @Autowired
    private TenantConfig tenantConfig;


    /**
     * 插件配置
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {

                return new LongValue(UserUtil.getTenantId());

            }

            // 返回 false 表示需要进行多租户隔离
            @Override
            public boolean ignoreTable(String tableName) {
                if (CollectionUtils.isNotEmpty(tenantConfig.getTableName())) {
                    if (tenantConfig.getTableName().contains(tableName.toLowerCase())) {
                        return false;
                    }
                }
                return true;
            }
        }));


        // 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 乐观锁
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }


}
