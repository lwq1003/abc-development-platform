package tech.abc.platform.common.extension;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.core.io.ClassPathResource;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.component.easyexcel.ExcelExceptionEnum;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.exception.FileException;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.net.URLEncoder;


/**
 * excel导出功能扩展
 *
 * @author wqliu
 * @date 2023-05-19
 */
public class ExcelExportExtension<V, E> extends BaseController {


    /**
     * 导出数据分页大小
     * TODO：可配置化
     */
    private static final long EXPORT_DATA_PAGE_SIZE = 200;

    /**
     * 导出模板
     */
    private String exportTemplate;

    /**
     * 设置导出模板
     */
    public void setExportTemplate(String exportTemplate) {
        this.exportTemplate = exportTemplate;
    }


    /**
     * 导出到excel
     *
     * @param queryParam
     * @param response
     * @throws Exception
     */
    public void exportExcel(V queryParam, HttpServletResponse response) {

        ClassPathResource classPathResource = new ClassPathResource(this.exportTemplate);
        try (InputStream inputStream = classPathResource.getInputStream();
             OutputStream outputStream = response.getOutputStream()) {

            // 设置响应信息
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码
            String fileName = URLEncoder.encode("导出数据.xlsx", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);


            // excel导出处理
            ExcelWriter excelWriter = EasyExcel.write(outputStream, this.getClazz())
                    .withTemplate(inputStream).build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();

            // 此处并没有获取数据总量后自行计算分页，而是利用了已有的分页查询功能
            long pageSize = EXPORT_DATA_PAGE_SIZE;

            // 开始第一次查询，并获取分页总数
            IPage<V> pagedResult = getExportData(queryParam, pageSize, 1);
            excelWriter.fill(pagedResult.getRecords(), writeSheet);
            // 读取后续数据
            for (int i = 2; i <= pagedResult.getPages(); i++) {
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                pagedResult = getExportData(queryParam, pageSize, i);
                excelWriter.fill(pagedResult.getRecords(), writeSheet);
            }
            // 关闭流
            excelWriter.finish();

        } catch (Exception exception) {
            throw new CustomException(FileException.EXCEL_EXPORT_FAILURE, exception.getMessage());
        }
    }


    /**
     * 获取导出数据
     *
     * @return
     * @throws Exception
     */
    public IPage<V> getExportData(V queryParam, long pageSize, long pageNum) {
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
