package tech.abc.platform.cip.api.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.abc.platform.cip.api.service.ApiRestService;
import tech.abc.platform.cip.common.entity.ApiRequest;
import tech.abc.platform.cip.common.entity.ApiResponse;
import tech.abc.platform.common.annotation.AllowAll;


/**
 * api服务 控制器
 *
 * @author wqliu
 * @date 2021-8-19
 **/
@RestController
@RequestMapping("/api")
@Slf4j
public class ApiRestController {

    @Autowired
    private ApiRestService apiRestService;

    @PostMapping("/rest")
    @AllowAll
    public ResponseEntity<ApiResponse> post(@RequestBody ApiRequest apiRequest) {
        ApiResponse apiResponse = apiRestService.handle(apiRequest);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

}
