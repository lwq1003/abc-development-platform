package tech.abc.platform.common.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 服务接口，定义通用操作
 *
 * @author wqliu
 * @date 2023-03-06
 */
public interface BaseService<T> extends IService<T> {

    /**
     * 初始化
     *
     * @return 实体对象
     */
    T init();


    /**
     * 新增
     *
     * @param entity 实体对象
     * @return ture:成功;false:失败
     */
    boolean add(T entity);


    /**
     * 修改
     *
     * @param entity 实体对象
     * @return ture:成功;false:失败
     */
    boolean modify(T entity);


    /**
     * 删除-通过id
     *
     * @param idListString id列表字符串，多个id用逗号间隔
     * @return
     */
    boolean remove(String idListString);


    /**
     * 批量新增
     *
     * @param list 实体列表
     * @return
     */
    boolean batchAdd(List<T> list);

    /**
     * 批量修改
     *
     * @param list 实体列表
     * @return
     */
    boolean batchModify(List<T> list);


    /**
     * 删除-通过条件表达式
     * 注意，删除大量数据会导致性能问题
     *
     * @param wrapper
     * @return
     */
    @Override
    boolean remove(Wrapper<T> wrapper);


    /**
     * 查询
     *
     * @param id 标识
     * @return
     */
    T query(String id);


    /**
     * 通过复制批量新增数据
     *
     * @param idListString id字符串列表
     * @param value        需替换的值
     * @return boolean
     */
    boolean addByCopy(String idListString, String... value);


    /**
     * 通过复制新增单条数据
     *
     * @param id    标识
     * @param value 需替换的值
     * @return T 新增后的对象
     */
    T addSingleByCopy(String id, String... value);
}
