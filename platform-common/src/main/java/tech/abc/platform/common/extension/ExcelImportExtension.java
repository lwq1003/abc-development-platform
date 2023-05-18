package tech.abc.platform.common.extension;

import com.alibaba.excel.EasyExcel;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.component.easyexcel.ExcelExceptionEnum;
import tech.abc.platform.common.component.easyexcel.ReadDataListener;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.exception.FileException;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.net.URLEncoder;


/**
 * excel导入功能扩展
 *
 * @author wqliu
 * @date 2023-05-17
 */
public class ExcelImportExtension<V, E> extends BaseController {

    /**
     * 数据监听器
     */
    private ReadDataListener readListener;

    /**
     * 导入模板
     */
    private String importTemplate;


    /**
     * 设置数据监听器
     *
     * @param readListener
     */
    public void setReadListener(ReadDataListener readListener) {
        this.readListener = readListener;
    }


    /**
     * 设置导入模板
     */
    protected void setImportTemplate(String importTemplate) {
        this.importTemplate = importTemplate;
    }

    /**
     * 下载导入模板
     */
    public void downloadImportTemplate(HttpServletResponse response) {

        ClassPathResource classPathResource = new ClassPathResource(importTemplate);
        try (InputStream inputStream = classPathResource.getInputStream();
             OutputStream outputStream = response.getOutputStream()) {

            // 设置响应信息
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码
            String fileName = URLEncoder.encode("导入模板.xlsx", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            IOUtils.copy(inputStream, outputStream);

        } catch (Exception exception) {
            throw new CustomException(FileException.DOWNLOAD_FAILURE, exception.getMessage());
        }

    }

    /**
     * excel上传
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Result> importExcel(MultipartFile file) {

        if (file == null) {
            throw new CustomException(FileException.UPLOAD_IS_NULL);
        }
        ResponseEntity<Result> result = null;

        try {
            EasyExcel.read(file.getInputStream(), this.getClazz(), this.readListener).sheet().doRead();
            return ResultUtil.success();
        } catch (Exception exception) {
            long currentRowNo = this.readListener.getCurrentRowNo();
            Throwable throwable = exception;
            while (throwable.getCause() != null) {
                throwable = throwable.getCause();

            }
            throw new CustomException(FileException.EXCEL_IMPORT_FAILURE, currentRowNo, throwable.getMessage());

        }
    }


    /**
     * 数据转换
     */
    protected E convert2EntityForExcel(V vo) {
        throw new CustomException(ExcelExceptionEnum.EXPORT_METHOD_UNIMPLEMENTED);
    }


    /**
     * 通过父类获取运行时泛型类型
     *
     * @return
     */
    private Class<V> getClazz() {
        // 获得当前类型的带有泛型类型的父类
        Class subclass;
        ParameterizedType pd = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class<V> clazz = (Class) pd.getActualTypeArguments()[0];
        return clazz;
    }


}
