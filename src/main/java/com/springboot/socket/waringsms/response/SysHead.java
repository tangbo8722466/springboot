package com.springboot.socket.waringsms.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"serviceCode","serviceScene","consumerId","orgConsumerId","consumerSeqNo","orgConsumerSeqNo",
       "tranDate","tranTime","returnStatus","result"})
public class SysHead {
    @XmlElement(name = "ServiceCode")
    private String serviceCode;
    @XmlElement(name = "ServiceScene")
    private String serviceScene;
    @XmlElement(name = "ConsumerId")
    private String consumerId;
    @XmlElement(name = "OrgConsumerId")
    private String orgConsumerId;
    @XmlElement(name = "ConsumerSeqNo")
    private String consumerSeqNo;
    @XmlElement(name = "OrgConsumerSeqNo")
    private String orgConsumerSeqNo;
    @XmlElement(name = "TranDate")
    private String tranDate;
    @XmlElement(name = "TranTime")
    private String tranTime;
    @XmlElement(name = "ReturnStatus")
    private String returnStatus;
    @XmlElementWrapper(name = "array", required = true)
    @XmlElement(name = "Ret")
    private List<Result> result;

    @Override
    public String toString() {
        return "SysHead{" +
                "serviceCode='" + serviceCode + '\'' +
                ", serviceScene='" + serviceScene + '\'' +
                ", consumerId='" + consumerId + '\'' +
                ", orgConsumerId='" + orgConsumerId + '\'' +
                ", consumerSeqNo='" + consumerSeqNo + '\'' +
                ", orgConsumerSeqNo='" + orgConsumerSeqNo + '\'' +
                ", tranDate='" + tranDate + '\'' +
                ", tranTime='" + tranTime + '\'' +
                ", returnStatus='" + returnStatus + '\'' +
                ", result=" + result +
                '}';
    }
}
