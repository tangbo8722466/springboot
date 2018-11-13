package com.springboot.socket.waringsms.request;

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
@XmlType(propOrder = {"tranTellerNo", "tranBranchId", "tranTellerPassword", "tranTellerLevel","tranTellerType", "apprFlag", "apprTellerArray", "authTellerInfo"})
public class AppHead {
    @XmlElement(name = "TranTellerNo")
    private String tranTellerNo;
    @XmlElement(name = "TranBranchId")
    private String tranBranchId;
    @XmlElement(name = "TranTellerPassword")
    private String tranTellerPassword;
    @XmlElement(name = "TranTellerLevel")
    private String tranTellerLevel;
    @XmlElement(name = "TranTellerType")
    private String tranTellerType;
    @XmlElement(name = "ApprFlag")
    private String apprFlag;
    @XmlElementWrapper(name = "array")//@XmlElementWrapper注解表示生成一个包装 XML 表示形式的包装器元素。 此元素主要用于生成一个包装集合的包装器 XML 元素
    @XmlElement(name = "ApprTellerArray", required = true)
    private List<ApprTellerArray> apprTellerArray;
    @XmlElementWrapper(name = "arrayReplace")//@XmlElementWrapper注解表示生成一个包装 XML 表示形式的包装器元素。 此元素主要用于生成一个包装集合的包装器 XML 元素
    @XmlElement(name = "AuthTellerInfo", required = true)
    private List<AuthTellerInfo> authTellerInfo;

    @Override
    public String toString() {
        return "AppHead{" +
                "tranTellerNo='" + tranTellerNo + '\'' +
                ", tranBranchId='" + tranBranchId + '\'' +
                ", tranTellerPassword='" + tranTellerPassword + '\'' +
                ", tranTellerLevel='" + tranTellerLevel + '\'' +
                ", tranTellerType='" + tranTellerType + '\'' +
                ", apprFlag='" + apprFlag + '\'' +
                ", apprTellerArray=" + apprTellerArray +
                ", authTellerInfo=" + authTellerInfo +
                '}';
    }
}
