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
@XmlType(propOrder={"txnNo","mblNo","msgCntntInf"})
public class ResqBody {
    @XmlElement(name = "TxnNo")
    private String txnNo;
    @XmlElement(name = "MblNo")
    private String mblNo;
    @XmlElement(name = "MsgCntntInf")
    private String msgCntntInf;

    @Override
    public String toString() {
        return "ResqBody{" +
                "txnNo='" + txnNo + '\'' +
                ", mblNo='" + mblNo + '\'' +
                ", msgCntntInf='" + msgCntntInf + '\'' +
                '}';
    }
}
