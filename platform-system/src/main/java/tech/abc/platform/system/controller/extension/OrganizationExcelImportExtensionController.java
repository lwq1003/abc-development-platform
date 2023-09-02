package tech.abc.platform.system.controller.extension;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.component.easyexcel.ReadDataListener;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.extension.ExcelImportExtension;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.system.constant.SystemConstant;
import tech.abc.platform.system.entity.Organization;
import tech.abc.platform.system.exception.OrganizationExceptionEnum;
import tech.abc.platform.system.service.OrganizationService;
import tech.abc.platform.system.vo.OrganizationForImportVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 实现Excel导入功能的组织机构控制器
 *
 * @author wqliu
 * @date 2023-05-17
 */
@RestController
@RequestMapping("/system/organization")
@Slf4j
public class OrganizationExcelImportExtensionController extends ExcelImportExtension<OrganizationForImportVO, Organization> {

    @Autowired
    private OrganizationService organizationService;


    @GetMapping("/downloadImportTemplate")
    @Override
    @PreAuthorize("hasPermission(null,'system:organization:downloadImportTemplate')")
    @SystemLog(value = "下载excel模板", logResponseData = false)
    public void downloadImportTemplate(HttpServletResponse response) {
        super.setImportTemplate("/template/system/organization/import.xlsx");
        super.downloadImportTemplate(response);
    }


    @PostMapping("/importExcel")
    @Override
    @PreAuthorize("hasPermission(null,'system:organization:import')")
    @SystemLog(value = "导入excel", logRequestParam = false)
    public ResponseEntity<Result> importExcel(MultipartFile file) {
        ReadDataListener readListener = new ReadDataListener<OrganizationForImportVO, Organization>(organizationService) {
            @Override
            public Organization convertData(OrganizationForImportVO vo) {
                return convert2EntityForExcel(vo);
            }

        };
        super.setReadListener(readListener);
        return super.importExcel(file);

    }


    @Override
    protected Organization convert2EntityForExcel(OrganizationForImportVO vo) {
        Organization entity = organizationService.init();
        BeanUtils.copyProperties(vo, entity);
        entity.setType(dictionaryUtil.getCodeByName("OrganizationType", vo.getTypeName()));

        // 处理上级
        if (StringUtils.isNotBlank(vo.getParentCode())) {
            // 优先判断上级编码是否存在，若存在，则根据编码找上级
            List<Organization> organizationList = organizationService.lambdaQuery()
                    .eq(Organization::getCode, vo.getParentCode()).list();
            if (organizationList.size() == 1) {
                // 找到数据，设置父级标识
                entity.setOrganization(organizationList.get(0).getId());
            } else {
                // 未找到，抛出异常
                throw new CustomException(OrganizationExceptionEnum.CODE_NOT_FOUND);
            }
        } else if (StringUtils.isNotBlank(vo.getParentName())) {

            // 将上级名称转换为标识
            List<Organization> organizationList = organizationService.lambdaQuery()
                    .eq(Organization::getName, vo.getParentName()).list();
            if (organizationList.size() == 1) {
                // 根据名称找到唯一的组织机构，设置组织机构标识
                entity.setOrganization(organizationList.get(0).getId());
            } else if (organizationList.size() == 0) {
                throw new CustomException(OrganizationExceptionEnum.NAME_NOT_FOUND);
            } else {
                // 找到多个同名组织机构，不抛异常，统一设置到预定义的未分配组织机构，转人工处理
                entity.setOrganization(SystemConstant.UNSIGNED_ORGANIZATION_ID);
            }

        } else {
            // 上级名称和编码都为空，数据不合法
            throw new CustomException(OrganizationExceptionEnum.PARENT_NAME_AND_CODE_CANOT_NULL);
        }

        return entity;
    }


}




