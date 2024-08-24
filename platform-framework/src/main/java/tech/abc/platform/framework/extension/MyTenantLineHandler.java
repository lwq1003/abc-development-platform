package tech.abc.platform.framework.extension;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.apache.commons.collections4.CollectionUtils;
import tech.abc.platform.common.utils.SpringUtil;
import tech.abc.platform.common.utils.UserUtil;
import tech.abc.platform.framework.config.TenantConfig;

/**
 * 多租户插件拦截器
 *
 * @author wqliu
 * @date 2024-08-06
 */

public class MyTenantLineHandler implements TenantLineHandler {


    @Override
    public Expression getTenantId() {

        return new LongValue(UserUtil.getTenantId());

    }


    /**
     * 返回 false 表示需要进行多租户隔离
     *
     * @param tableName 表名称
     * @return boolean
     */
    @Override
    public boolean ignoreTable(String tableName) {

        TenantConfig tenantConfig = SpringUtil.getBean(TenantConfig.class);

        if (CollectionUtils.isNotEmpty(tenantConfig.getTableName())) {
            if (tenantConfig.getTableName().contains(tableName.toLowerCase())) {
                return false;
            }
        }
        return true;
    }
}
