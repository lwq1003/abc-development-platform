package tech.abc.platform.system.controller.extension;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.extension.ExcelExportExtension;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.system.controller.OrganizationController;
import tech.abc.platform.system.entity.Organization;
import tech.abc.platform.system.service.OrganizationService;
import tech.abc.platform.system.vo.OrganizationVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 实现Excel导出功能的组织机构控制器
 *
 * @author wqliu
 * @date 2023-05-19
 */
@RestController
@RequestMapping("/system/organization")
@Slf4j
public class OrganizationExcelExportExtensionController extends ExcelExportExtension<OrganizationVO, Organization> {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    protected OrganizationController organizationController;


    @GetMapping("/exportExcel")
    @Override
    @PreAuthorize("hasPermission(null,'system:organization:export')")
    @SystemLog(value = "导出excel", logResponseData = false)
    public void exportExcel(OrganizationVO vo, HttpServletResponse response) {


        // 设置模板名称
        super.setExportTemplate("/template/system/organization/export.xlsx");

        // 当勾选查询所有复选框时，查询所有数据
        if (vo.getIgnoreParent() != null && vo.getIgnoreParent()) {
            vo.setOrganization(null);
        }
        // 导出到excel
        super.exportExcel(vo, response);

    }

    @Override
    public IPage<OrganizationVO> getExportData(OrganizationVO queryParam, long pageSize, long pageNum) {

        // 构造分页对象
        IPage<Organization> page = new Page<Organization>(pageNum, pageSize);
        // 构造查询条件
        QueryWrapper<Organization> queryWrapper = QueryGenerator.generateQueryWrapper(Organization.class, queryParam);

        // 排序
        queryWrapper.lambda().orderByAsc(Organization::getOrganization).orderByAsc(Organization::getOrderNo);


        organizationService.page(page, queryWrapper);


        // 转换vo
        IPage<OrganizationVO> pageVO = mapperFacade.map(page, IPage.class);
        List<OrganizationVO> organizationVOList = organizationController.convert2VO(page.getRecords());
        pageVO.setRecords(organizationVOList);
        return pageVO;

    }
}
