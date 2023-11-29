package tech.abc.platform.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.system.service.SystemManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统管理 前端控制器
 *
 * @author wqliu
 * @date 2023-03-08
 */
@RestController
@RequestMapping("/system/systemManage/")
@Slf4j
public class SystemManageController {

    @Autowired
    private SystemManageService systemManageService;

    /**
     * 重建缓存
     */

    @PutMapping("/rebuildSystemCache")
    public ResponseEntity<Result> rebuildSystemCache() {

        systemManageService.rebuildSystemCache();
        return ResultUtil.success();
    }

    /**
     * 重建缓存
     */

    @GetMapping("/getUniqueId")
    public ResponseEntity<Result> getUniqueId() {

        String uniqueId = systemManageService.getUniqueId();
        return ResultUtil.success(uniqueId);
    }


}
