package com.springboot.socket.waringsms.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"serviceCode","serviceScene","consumerId","orgConsumerId","systemId","consumerSeqNo","orgConsumerSeqNo",
        "servSeqNo","terminalCode","orgTerminalCode","consumerSvrId","orgConsumerSvrId","destSvrId","tranMode","hstDate",
        "hstTime","tranDate","tranTime","userLang"})
public class ResqSysHead {
    @XmlElement(name = "ServiceCode")
    private String serviceCode;
    @XmlElement(name = "ServiceScene")
    private String serviceScene;
    @XmlElement(name = "ConsumerId")
    private String consumerId;
    @XmlElement(name = "OrgConsumerId")
    private String orgConsumerId;
    @XmlElement(name = "SystemId")
    private String systemId;
    @XmlElement(name = "ConsumerSeqNo")
    private String consumerSeqNo;
    @XmlElement(name = "OrgConsumerSeqNo")
    private String orgConsumerSeqNo;
    @XmlElement(name = "ServSeqNo")
    private String servSeqNo;
    @XmlElement(name = "TerminalCode")
    private String terminalCode;
    @XmlElement(name = "OrgTerminalCode")
    private String orgTerminalCode;
    @XmlElement(name = "ConsumerSvrId")
    private String consumerSvrId;
    @XmlElement(name = "OrgConsumerSvrId")
    private String orgConsumerSvrId;
    @XmlElement(name = "DestSvrId")
    private String destSvrId;
    @XmlElement(name = "TranMode")
    private String tranMode;
    @XmlElement(name = "HstDate")
    private String hstDate;
    @XmlElement(name = "HstTime")
    private String hstTime;
    @XmlElement(name = "TranDate")
    private String tranDate;
    @XmlElement(name = "TranTime")
    private String tranTime;
    @XmlElement(name = "UserLang")
    private String userLang;

    @Override
    public String toString() {
        return "ResqSysHead{" +
                "serviceCode='" + serviceCode + '\'' +
                ", serviceScene='" + serviceScene + '\'' +
                ", consumerId='" + consumerId + '\'' +
                ", orgConsumerId='" + orgConsumerId + '\'' +
                ", systemId='" + systemId + '\'' +
                ", consumerSeqNo='" + consumerSeqNo + '\'' +
                ", orgConsumerSeqNo='" + orgConsumerSeqNo + '\'' +
                ", servSeqNo='" + servSeqNo + '\'' +
                ", terminalCode='" + terminalCode + '\'' +
                ", orgTerminalCode='" + orgTerminalCode + '\'' +
                ", consumerSvrId='" + consumerSvrId + '\'' +
                ", orgConsumerSvrId='" + orgConsumerSvrId + '\'' +
                ", destSvrId='" + destSvrId + '\'' +
                ", tranMode='" + tranMode + '\'' +
                ", hstDate='" + hstDate + '\'' +
                ", hstTime='" + hstTime + '\'' +
                ", tranDate='" + tranDate + '\'' +
                ", tranTime='" + tranTime + '\'' +
                ", userLang='" + userLang + '\'' +
                '}';
    }
}
