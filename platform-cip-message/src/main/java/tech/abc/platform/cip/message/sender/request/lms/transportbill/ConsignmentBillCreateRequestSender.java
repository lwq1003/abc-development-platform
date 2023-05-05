package tech.abc.platform.cip.message.sender.request.lms.transportbill;


import tech.abc.platform.cip.entity.AppDataPermission;
import tech.abc.platform.cip.enums.DataRoleEnum;
import tech.abc.platform.cip.message.framework.sender.RequestMessageSender;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 委托单创建发送器
 *
 * @author wqliu
 * @date 2022-2-1 8:14
 **/
public class ConsignmentBillCreateRequestSender extends RequestMessageSender {
    public ConsignmentBillCreateRequestSender() {
        super("lms.transportbill.consignmentbill.create");
    }


    @Override
    protected boolean dataPermissionFilter(String content, String appCode) {
        // 获取业务单据标识
        String id = content;
        // 通过api调用，获取该业务单据的承运商编码,此处模拟为001
        String carrierCode = "001";

        // 查找当前应用拥有的承运商数据角色列表
        List<AppDataPermission> list = apiDataPermissionService.getPermissionByRoleCode(DataRoleEnum.CARRIER.name(), appCode);

        AtomicBoolean hasPermission = new AtomicBoolean(false);
        list.stream().forEach(x -> {
            // 如数据权限记录的业务编码与单据编码一致，或者使用了通配符，则有权限
            if (x.getBusinessCode().equals(carrierCode) || x.getBusinessCode().equals(DATA_PERMISSION_ALL)) {
                hasPermission.set(true);
                return;
            }

        });

        return hasPermission.get();
    }
}
