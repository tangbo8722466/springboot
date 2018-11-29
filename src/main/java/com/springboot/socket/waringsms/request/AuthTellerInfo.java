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
@XmlType(propOrder = {"authTellerNo", "authBranchId", "authTellerPassword", "authTellerLevel", "authTellerType"})
public class AuthTellerInfo {
    @XmlElement(name = "AuthTellerNo")
    private String authTellerNo;
    @XmlElement(name = "AuthBranchId")
    private String authBranchId;
    @XmlElement(name = "AuthTellerPassword")
    private String authTellerPassword;
    @XmlElement(name = "AuthTellerLevel")
    private String authTellerLevel;
    @XmlElement(name = "AuthTellerType")
    private String authTellerType;

    @Override
    public String toString() {
        return "AuthTellerInfo{" +
                "authTellerNo='" + authTellerNo + '\'' +
                ", authBranchId='" + authBranchId + '\'' +
                ", authTellerPassword='" + authTellerPassword + '\'' +
                ", authTellerLevel='" + authTellerLevel + '\'' +
                ", authTellerType='" + authTellerType + '\'' +
                '}';
    }
}
