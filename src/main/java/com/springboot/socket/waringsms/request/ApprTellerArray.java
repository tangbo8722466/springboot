package com.springboot.socket.waringsms.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"apprTellerNo", "apprBranchId", "apprTellerLevel", "apprTellerType", "authFlag"})
public class ApprTellerArray {
    @XmlElement(name = "ApprTellerNo")
    private String apprTellerNo;
    @XmlElement(name = "ApprBranchId")
    private String apprBranchId;
    @XmlElement(name = "ApprTellerLevel")
    private String apprTellerLevel;
    @XmlElement(name = "ApprTellerType")
    private String apprTellerType;
    @XmlElement(name = "AuthFlag")
    private String authFlag;

    @Override
    public String toString() {
        return "ApprTellerArray{" +
                "apprTellerNo='" + apprTellerNo + '\'' +
                ", apprBranchId='" + apprBranchId + '\'' +
                ", apprTellerLevel='" + apprTellerLevel + '\'' +
                ", apprTellerType='" + apprTellerType + '\'' +
                ", authFlag='" + authFlag + '\'' +
                '}';
    }
}
