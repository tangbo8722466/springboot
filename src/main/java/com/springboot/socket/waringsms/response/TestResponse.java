package com.springboot.socket.waringsms.response;

import com.springboot.socket.waringsms.utils.JaxbUtil;
import org.springframework.util.StringUtils;

public class TestResponse {
    public static void main(String[] args) {
        System.out.println(objectToXml());
        System.out.println(xmlToObject(null).toString());
    }

    public static String objectToXml() {
        String xmlTranslate = JaxbUtil.convertToXml(xmlToObject(null));
        return xmlTranslate;
    }

    public static SMSResponsePacket xmlToObject(String xml) {
        if (StringUtils.isEmpty(xml)) {
            xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<service>\n" +
                    "    <SYS_HEAD>\n" +
                    "        <ServiceCode>12002000004</ServiceCode>\n" +
                    "        <ServiceScene>01</ServiceScene>\n" +
                    "        <ConsumerId>118001</ConsumerId>\n" +
                    "        <OrgConsumerId>118001</OrgConsumerId>\n" +
                    "        <ConsumerSeqNo>10400120181107ESB0000000001</ConsumerSeqNo>\n" +
                    "        <OrgConsumerSeqNo>10400120181107ESB0000000001</OrgConsumerSeqNo>\n" +
                    "        <TranDate>20181107</TranDate>\n" +
                    "        <TranTime>180532</TranTime>\n" +
                    "        <ReturnStatus>S</ReturnStatus>\n" +
                    "        <array>\n" +
                    "            <Ret>\n" +
                    "                <ReturnCode>000000</ReturnCode>\n" +
                    "                <ReturnMsg>交易成功</ReturnMsg>\n" +
                    "            </Ret>\n" +
                    "        </array>\n" +
                    "    </SYS_HEAD>\n" +
                    "    <APP_HEAD>\n" +
                    "        <array/>\n" +
                    "        <array/>\n" +
                    "    </APP_HEAD>\n" +
                    "    <BODY/>\n" +
                    "</service>";
        }
        SMSResponsePacket smsResponsePacket = JaxbUtil.converyToJavaBean(xml, SMSResponsePacket.class);
        return smsResponsePacket;
    }
}