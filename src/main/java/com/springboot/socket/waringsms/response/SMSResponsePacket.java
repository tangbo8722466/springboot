package com.springboot.socket.waringsms.response;


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
@XmlRootElement(name="service")
@XmlType(propOrder={"sysHead"})
public class SMSResponsePacket{
    @XmlElement(name = "SYS_HEAD")
    private RespSysHead sysHead;

    @Override
    public String toString() {
        return "SMSResponsePacket{" +
                "sysHead=" + sysHead +
                '}';
    }
}
