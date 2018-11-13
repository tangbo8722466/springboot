package com.springboot.socket.waringsms.request;

import com.springboot.socket.waringsms.utils.JaxbUtil;
import org.springframework.util.StringUtils;

public class TestRequest {
    public static void main(String[] args) {
        System.out.println(objectToXml());
        System.out.println(xmlToObject(null).toString());
    }

    public static String objectToXml() {
        String xmlTranslate = JaxbUtil.convertToXml(xmlToObject(null)).replace("arrayReplace","array");
        System.out.println(xmlTranslate);
        return xmlTranslate;
    }

    public static SMSRequestPacket xmlToObject(String xml) {
        if (StringUtils.isEmpty(xml)) {
            xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<service version=\"2.0\">\n" +
                    "    <SYS_HEAD>\n" +
                    "        <ServiceCode>12002000004</ServiceCode>\n" +
                    "        <ServiceScene>01</ServiceScene>\n" +
                    "        <ConsumerId>118001</ConsumerId>\n" +
                    "        <OrgConsumerId>118001</OrgConsumerId>\n" +
                    "        <SystemId>1</SystemId>\n" +
                    "        <ConsumerSeqNo>10400120181107ESB0000000001</ConsumerSeqNo>\n" +
                    "        <OrgConsumerSeqNo>10400120181107ESB0000000001</OrgConsumerSeqNo>\n" +
                    "        <ServSeqNo/>\n" +
                    "        <TerminalCode/>\n" +
                    "        <OrgTerminalCode/>\n" +
                    "        <ConsumerSvrId/>\n" +
                    "        <OrgConsumerSvrId/>\n" +
                    "        <DestSvrId/>\n" +
                    "        <TranMode>ONLINE</TranMode>\n" +
                    "        <HstDate/>\n" +
                    "        <HstTime/>\n" +
                    "        <TranDate>20181107</TranDate>\n" +
                    "        <TranTime>180532</TranTime>\n" +
                    "        <UserLang/>\n" +
                    "    </SYS_HEAD>\n" +
                    "    <APP_HEAD>\n" +
                    "        <TranTellerNo/>\n" +
                    "        <TranBranchId/>\n" +
                    "        <TranTellerPassword/>\n" +
                    "        <TranTellerLevel/>\n" +
                    "        <TranTellerType/>\n" +
                    "        <ApprFlag/>\n" +
                    "        <array>\n" +
                    "            <ApprTellerArray>\n" +
                    "                <ApprTellerNo/>\n" +
                    "                <ApprBranchId/>\n" +
                    "                <ApprTellerLevel/>\n" +
                    "                <ApprTellerType/>\n" +
                    "                <AuthFlag/>\n" +
                    "            </ApprTellerArray>\n" +
                    "        </array>\n" +
                    "        <arrayReplace>\n" +
                    "            <AuthTellerInfo>\n" +
                    "                <AuthTellerNo/>\n" +
                    "                <AuthBranchId/>\n" +
                    "                <AuthTellerPassword/>\n" +
                    "                <AuthTellerLevel/>\n" +
                    "                <AuthTellerType/>\n" +
                    "            </AuthTellerInfo>\n" +
                    "        </arrayReplace>\n" +
                    "    </APP_HEAD>\n" +
                    "    <BODY>\n" +
                    "        <TxnNo>820001</TxnNo>\n" +
                    "        <MblNo>15702980795</MblNo>\n" +
                    "        <MsgCntntInf>亲爱的,您好!有一条新的预警产生.预警名:123;预警描述:123;请及时处理!</MsgCntntInf>\n" +
                    "    </BODY>\n" +
                    "</service>";
        }
        SMSRequestPacket smsRequestPacket = JaxbUtil.converyToJavaBean(xml, SMSRequestPacket.class);
        System.out.println(smsRequestPacket);
        return smsRequestPacket;
    }
}