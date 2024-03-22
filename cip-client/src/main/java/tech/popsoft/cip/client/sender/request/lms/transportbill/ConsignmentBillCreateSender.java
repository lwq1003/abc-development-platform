package tech.popsoft.cip.client.sender.request.lms.transportbill;

import tech.popsoft.cip.client.framework.sender.RequestMessageSender;

/**
 * 委托单创建发送器
 *
 * @author wqliu
 * @date 2022-1-18
 **/
public class ConsignmentBillCreateSender extends RequestMessageSender {

    public ConsignmentBillCreateSender() {
        super("lms.transportbill.consignmentbill.create");
    }

}
