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
import tech.abc.platform.system.controller.UserController;
import tech.abc.platform.system.entity.User;
import tech.abc.platform.system.service.UserService;
import tech.abc.platform.system.vo.UserVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 实现Excel导出功能的用户控制器
 *
 * @author wqliu
 * @date 2023-05-19
 */
@RestController
@RequestMapping("/system/user")
@Slf4j
public class UserExcelExportExtensionController extends ExcelExportExtension<UserVO, User> {

    @Autowired
    private UserService userService;

    @Autowired
    protected UserController userController;


    @GetMapping("/exportExcel")
    @PreAuthorize("hasPermission(null,'system:user:export')")
    @SystemLog(value = "导出excel", logResponseData = false)
    @Override
    public void exportExcel(UserVO vo, HttpServletResponse response) {

        // 设置模板名称
        super.setExportTemplate("/template/system/user/export.xlsx");

        // 当勾选查询所有复选框时，查询所有数据
        if (vo.getIgnoreParent() != null && vo.getIgnoreParent()) {
            vo.setOrganization(null);
        }
        // 导出到excel
        super.exportExcel(vo, response);

    }


    @Override
    public IPage<UserVO> getExportData(UserVO queryParam, long pageSize, long pageNum) {

        // 构造分页对象
        IPage<User> page = new Page<User>(pageNum, pageSize);
        // 构造查询条件
        QueryWrapper<User> queryWrapper = QueryGenerator.generateQueryWrapper(User.class, queryParam);

        // 排序
        queryWrapper.lambda().orderByAsc(User::getOrganization).orderByAsc(User::getOrderNo);


        userService.page(page, queryWrapper);


        // 转换vo
        IPage<UserVO> pageVO = mapperFacade.map(page, IPage.class);
        List<UserVO> userVOList = userController.convert2VO(page.getRecords());
        pageVO.setRecords(userVOList);
        return pageVO;
    }
}
