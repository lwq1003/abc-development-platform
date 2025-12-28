package tech.abc.platform.monitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.monitor.entity.SystemMonitor;
import tech.abc.platform.monitor.service.SystemMonitorService;
import tech.abc.platform.monitor.vo.SystemMonitorVO;


/**
 * 系统监控控制器
 *
 * @author wqliu
 * @date 2025-12-11
 */
@RestController
@RequestMapping("/monitor/systemMonitor")
public class SystemMonitorController extends BaseController {

    @Autowired
    private SystemMonitorService systemMonitorService;

    /**
     * 获取系统监控信息
     */
    @GetMapping("/info")
    @PreAuthorize("hasPermission(null,'monitor:systemMonitor')")
    public ResponseEntity<Result> getSystemMonitorInfo() {
        SystemMonitor systemMonitorInfo = systemMonitorService.getSystemMonitorInfo();
        SystemMonitorVO systemMonitorVO = mapperFacade.map(systemMonitorInfo, SystemMonitorVO.class);
        return ResultUtil.success(systemMonitorVO);
    }
}