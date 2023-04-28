package tech.abc.platform.system.api;

import tech.abc.platform.common.modules.system.api.LogApi;
import tech.abc.platform.common.modules.system.params.LogDTO;
import tech.abc.platform.system.entity.Log;
import tech.abc.platform.system.service.LogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: wqliu
 * @date 2020-03-26 19:46
 * :
 */
@Component
public class LogApiImpl implements LogApi {

    @Autowired
    private LogService logService;

    @Override
    public void add(LogDTO logDTO) {
        Log log = new Log();
        BeanUtils.copyProperties(logDTO, log);
        logService.add(log);

    }
}
