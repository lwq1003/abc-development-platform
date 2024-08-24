package tech.abc.platform.framework.extension;

import com.baomidou.mybatisplus.extension.plugins.handler.MultiDataPermissionHandler;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import org.apache.commons.lang3.StringUtils;
import tech.abc.platform.common.exception.SessionExpiredException;
import tech.abc.platform.common.utils.SpringUtil;
import tech.abc.platform.common.utils.UserUtil;
import tech.abc.platform.entityconfig.service.EntityModelDataPermissionService;

import java.util.List;

/**
 * 数据权限处理器
 *
 * @author wqliu
 * @date 2024-08-03
 */
@Slf4j
public class MyDataPermissionHandler implements MultiDataPermissionHandler {
    /**
     * 数据权限配置表名称
     */
    public static final String DATA_PERMISSION_CONFIG_TABLE_NAME = "cfg_entity_model_data_permission";

    @Override
    public Expression getSqlSegment(Table table, Expression where, String mappedStatementId) {


        // 数据权限相关的 SQL 片段
        String tableName = table.getName();
        // 排除数据权限配置表，否则会导致死循环
        if (tableName.equals(DATA_PERMISSION_CONFIG_TABLE_NAME)) {
            return null;
        }
        // 获取数据权限 SQL 片段
        EntityModelDataPermissionService entityModelDataPermissionService = SpringUtil.getBean(EntityModelDataPermissionService.class);
        String sqlSegment = entityModelDataPermissionService.getDataPermissionSqlPart(tableName);
        if (sqlSegment != null) {

            String userId = "";
            String departmentId = "";
            // 登录阶段，获取不到当前用户信息，视为不做数据权限过滤
            try {
                userId = UserUtil.getId();
                departmentId = UserUtil.getDepartmentId();

            } catch (SessionExpiredException e) {
                return null;
            }

            // 替换当前用户
            sqlSegment = StringUtils.replace(sqlSegment, "{@CurrentUser@}", userId);
            // 替换当前部门
            sqlSegment = StringUtils.replace(sqlSegment, "{@CurrentDepartment@}", departmentId);


            // 模拟获取当前用户的用户组列表
            List<String> userGroupList = UserUtil.getUserGroupList();
            StringBuffer sb = new StringBuffer();
            userGroupList.stream().forEach(item -> {
                sb.append("'").append(item).append("',");
            });
            String userGroupListString = sb.deleteCharAt(sb.length() - 1).toString();
            // 替换当前用户组
            sqlSegment = StringUtils.replace(sqlSegment, "'{@CurrentUserGroup@}'", userGroupListString);


            try {
                return CCJSqlParserUtil.parseCondExpression(sqlSegment);
            } catch (JSQLParserException e) {
                log.error("数据权限 SQL 片段解析失败", e);
                return null;
            }
        } else {
            return null;
        }
    }
}