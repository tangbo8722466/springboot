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
@XmlRootElement(name="service")
@XmlType(propOrder={"sysHead","appHead","body"})
public class SMSRequestPacket {
    @XmlAttribute(name = "version")
    private String version;
    @XmlElement(name = "SYS_HEAD")
    private SysHead sysHead;
    @XmlElement(name = "APP_HEAD")
    private AppHead appHead;
    @XmlElement(name = "BODY")
    private Body body;

    @Override
    public String toString() {
        return "SMSResponsePacket{" +
                "version='" + version.toString() + '\'' +
                ", sysHead=" + sysHead.toString() +
                ", appHead=" + appHead.toString() +
                ", body=" + body.toString() +
                '}';
    }
}
