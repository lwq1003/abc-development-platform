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
import tech.abc.platform.system.entity.User;
import tech.abc.platform.system.exception.UserExceptionEnum;
import tech.abc.platform.system.service.OrganizationService;
import tech.abc.platform.system.service.UserService;
import tech.abc.platform.system.vo.UserForImportVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 实现Excel导入功能的用户控制器
 *
 * @author wqliu
 * @date 2023-05-19
 */
@RestController
@RequestMapping("/system/user")
@Slf4j
public class UserExcelImportExtensionController extends ExcelImportExtension<UserForImportVO, User> {

    @Autowired
    private UserService userService;


    @Autowired
    private OrganizationService organizationService;


    @GetMapping("/downloadImportTemplate")
    @Override
    @PreAuthorize("hasPermission(null,'system:user:downloadImportTemplate')")
    @SystemLog(value = "下载excel模板", logResponseData = false)
    public void downloadImportTemplate(HttpServletResponse response) {
        super.setImportTemplate("/template/system/user/import.xlsx");
        super.downloadImportTemplate(response);
    }


    @PostMapping("/importExcel")
    @Override
    @PreAuthorize("hasPermission(null,'system:user:import')")
    @SystemLog(value = "导入excel", logRequestParam = false)
    @Transactional
    public ResponseEntity<Result> importExcel(MultipartFile file) {
        ReadDataListener readListener = new ReadDataListener<UserForImportVO, User>(userService) {
            @Override
            public User convertData(UserForImportVO vo) {
                return convert2EntityForExcel(vo);
            }

        };
        super.setReadListener(readListener);
        return super.importExcel(file);

    }


    @Override
    protected User convert2EntityForExcel(UserForImportVO vo) {

        User entity = userService.init();
        BeanUtils.copyProperties(vo, entity);
        entity.setGender(dictionaryUtil.getCodeByName("Gender", vo.getGenderName()));

        // 处理组织机构
        if (StringUtils.isNotBlank(vo.getOrganizationCode())) {
            // 优先判断组织机构编码是否存在，若存在，则根据编码找组织机构
            List<Organization> organizationList = organizationService.lambdaQuery()
                    .eq(Organization::getCode, vo.getOrganizationCode()).list();
            if (organizationList.size() == 1) {
                // 找到数据，设置组织机构标识
                entity.setOrganization(organizationList.get(0).getId());
            } else {
                // 未找到，抛出异常
                throw new CustomException(UserExceptionEnum.ORGANIZATION_CODE_NOT_FOUND);
            }
        } else if (StringUtils.isNotBlank(vo.getOrganizationName())) {

            // 将组织机构名称转换为标识
            List<Organization> organizationList = organizationService.lambdaQuery()
                    .eq(Organization::getName, vo.getOrganizationName()).list();
            if (organizationList.size() == 1) {
                // 根据名称找到唯一的组织机构，设置组织机构标识
                entity.setOrganization(organizationList.get(0).getId());
            } else if (organizationList.size() == 0) {
                throw new CustomException(UserExceptionEnum.ORGANIZATION_NAME_NOT_FOUND);
            } else {
                // 找到多个同名组织机构，不抛异常，统一设置到预定义的未分配组织机构，转人工处理
                entity.setOrganization(SystemConstant.UNSIGNED_ORGANIZATION_ID);
            }

        } else {
            // 组织机构名称和编码都为空，数据不合法
            throw new CustomException(UserExceptionEnum.ORGANIZATION_NAME_AND_CODE_CANOT_NULL);
        }
        return entity;
    }
}




