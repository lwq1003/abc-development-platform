package tech.abc.platform.common.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.utils.CacheUtil;

import java.util.List;

/**
 * 服务类基类，处理通用操作
 *
 * @author wqliu
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseMapEntity>
        extends ServiceImpl<M, T> implements BaseService<T> {

    @Autowired
    protected CacheUtil cacheUtil;

    @Autowired
    protected MapperFacade mapperFacade;

    @Override
    public T init() {

        return null;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean add(T entity) {

        beforeAdd(entity);
        beforeAddOrModifyOp(entity);
        boolean result = super.save(entity);
        afterAddOrModifyOp(entity);
        afterAdd(entity);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean modify(T entity) {
        // 将修改前数据查出来缓存下来，传入到修改后方法中，用于一些特殊逻辑处理，如某个值变化才进行
        T oldEntity = query(entity.getId());

        beforeModify(entity);
        beforeAddOrModifyOp(entity);
        if (entity instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) entity;
            baseEntity.setUpdateId(null);
            baseEntity.setUpdateTime(null);
        }

        boolean result = super.updateById(entity);
        afterAddOrModifyOp(entity);
        afterModify(entity, oldEntity);
        return result;
    }

    /**
     * 删除-通过id
     *
     * @param idListString id列表字符串，多个id用逗号间隔
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean remove(String idListString) {
        String[] idArray = StringUtils.split(idListString.toString(), ",");
        for (String item : idArray) {
            T entity = getById(item);
            if (entity == null) {
                // 删除环节对象为空时视为操作成功（批量删除时，如存在父子对象，且删除父对象时级联删除子对象，会导致运行到子对象删除环节找不到该对象）
                continue;
            }
            beforeRemove(entity);
            super.removeById(item);
            afterRemove(entity);
        }
        return true;
    }

    @Override
    public T query(String id) {
        beforeQuery(id);
        T result = getById(id);
        // 对象非空判断
        if (result == null) {
            throw new CustomException(CommonException.NOT_EXIST);
        }
        afterQuery(result);
        return result;
    }

    /**
     * 复制新增
     *
     * @param idListString id列表字符串，多个id用逗号间隔
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addByCopy(String idListString, String... value) {
        String[] idArray = StringUtils.split(idListString.toString(), ",");
        for (String item : idArray) {
            addSingleByCopy(item, value);

        }
        return true;
    }

    @Override
    public T addSingleByCopy(String id, String... value) {
        T entity = getEntity(id);
        T newEntity = init();
        mapperFacade.map(entity, newEntity);
        // 清空系统属性
        newEntity.setId(null);
        newEntity.setCreateId(null);
        newEntity.setCreateTime(null);
        newEntity.setUpdateId(null);
        newEntity.setUpdateTime(null);
        newEntity.setVersion(1);

        // 拷贝属性处理
        copyPropertyHandle(newEntity, value);
        beforeAddByCopy(entity, newEntity);
        super.save(newEntity);
        afterAddByCopy(entity, newEntity);
        return newEntity;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean batchAdd(List<T> list) {
        for (T entity : list) {
            add(entity);
        }
        return true;
    }


    @Override
    public boolean batchModify(List<T> list) {
        for (T entity : list) {
            modify(entity);
        }
        return true;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean remove(Wrapper<T> wrapper) {
        List<T> boList = super.list(wrapper);
        for (T entity : boList) {
            beforeRemove(entity);
        }
        boolean result = super.remove(wrapper);
        for (T entity : boList) {
            afterRemove(entity);
        }
        return result;
    }


    /**
     * 新增前
     *
     * @param entity 实体
     */
    protected void beforeAdd(T entity) {
        // 子类根据需要覆写
    }

    /**
     * 新增后
     *
     * @param entity 实体
     */
    protected void afterAdd(T entity) {
        // 子类根据需要覆写
    }


    /**
     * 修改前
     *
     * @param entity 实体
     */
    protected void beforeModify(T entity) {
        // 子类根据需要覆写
    }

    /**
     * 修改后
     *
     * @param entity 实体
     */
    protected void afterModify(T entity, T orginEntity) {
        // 子类根据需要覆写
    }


    /**
     * 新增和修改前公用操作
     */
    protected void beforeAddOrModifyOp(T entity) {
        // 子类根据需要覆写
    }

    /**
     * 新增和修改后公用操作
     */
    protected void afterAddOrModifyOp(T entity) {
        // 子类根据需要覆写
    }


    /**
     * 删除前
     *
     * @param entity 实体
     */
    protected void beforeRemove(T entity) {
        // 子类根据需要覆写

    }

    /**
     * 删除后
     *
     * @param entity 实体
     */
    protected void afterRemove(T entity) {
        // 子类根据需要覆写
    }


    /**
     * 查询前
     *
     * @param id id
     */
    protected void beforeQuery(String id) {
        // 子类根据需要覆写
    }

    /**
     * 查询后
     *
     * @param entity 实体
     */
    protected void afterQuery(T entity) {
        // 子类根据需要覆写
    }

    /**
     * 复制属性处理
     *
     * @param sourceEntity 源实体
     * @param value        属性值
     */
    protected void copyPropertyHandle(T entity, String... value) {

    }


    /**
     * 复制新增后处理
     *
     * @param sourceEntity 源实体
     * @param entity       实体
     */
    protected void afterAddByCopy(T sourceEntity, T entity) {
        // 子类根据需要覆写

    }


    /**
     * 复制新增前处理
     *
     * @param sourceEntity 源实体
     * @param entity       实体
     */
    protected void beforeAddByCopy(T sourceEntity, T entity) {
        // 子类根据需要覆写

    }


    /**
     * 获取实体
     *
     * @param id id
     * @return {@link T}
     */
    protected T getEntity(String id) {
        // 标识非空判断
        if (id == null) {
            throw new CustomException(CommonException.ID_EMPTY);
        }

        T entity = query(id);
        return entity;
    }


}
