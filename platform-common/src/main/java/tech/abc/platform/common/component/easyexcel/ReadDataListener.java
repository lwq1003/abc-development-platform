package tech.abc.platform.common.component.easyexcel;

// 有个很重要的点 CommonExcelDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


/**
 * 读取数据监听器
 *
 * @author wqliu
 * @date 2023-05-17
 */
@Slf4j
public class ReadDataListener<V, E> implements ReadListener<V> {


    /**
     * 当前处理行号
     */
    private long currentRowNo = 0;
    /**
     * 服务
     */
    private BaseService service;

    /**
     * 获取当前行号
     *
     * @return long 行号
     */
    public long getCurrentRowNo() {
        return currentRowNo;
    }

    /**
     * 构造方法
     * 每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param service
     */
    public ReadDataListener(BaseService service) {
        this.service = service;
    }

    /**
     * 解析数据
     *
     * @param data    单行记录
     * @param context
     */
    @Override
    public void invoke(V data, AnalysisContext context) {
        currentRowNo++;
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        // 处理数据
        E entity = handleData(data);
        // 保存数据
        service.add(entity);

    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成！");
    }

    /**
     * 数据预处理，可转化实体类中的字典数据，也可以设置默认字段
     *
     * @param data
     */
    public E handleData(V vo) {

        // 数据校验
        validateData(vo);
        // 设置默认值
        setDefaultValue(vo);
        // 数据转换
        return convertData(vo);
    }

    private void validateData(V vo) {
        // 进行数据验证
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();
        Set<ConstraintViolation<V>> set = validator.validate(vo);
        for (ConstraintViolation<V> constraintViolation : set) {
            throw new CustomException(CommonException.DATA_VALIDATE_FAILURE, constraintViolation.getMessage());
        }

    }


    /**
     * 设置默认值
     *
     * @param vo 视图对象
     */
    protected void setDefaultValue(V vo) {
        // 如无需设置，则该方法可为空

    }


    /**
     * 转换数据
     *
     * @param vo 视图对象
     * @return {@link E}
     */
    protected E convertData(V vo) {
        throw new CustomException(ExcelExceptionEnum.EXPORT_METHOD_UNIMPLEMENTED);
    }


}