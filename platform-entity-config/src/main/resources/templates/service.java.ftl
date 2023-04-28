package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import java.util.List;
import java.util.Map;

/**
 * ${table.comment!} 服务接口类
 *
 * @author ${author}
 * @date ${date}
 */
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

   /**
   * 获取标识与名称的Map集合
   *
   * @param idList 标识列表
   * @return 集合
   */
   Map<String,String> getNameMap(List<String> idList);
}

