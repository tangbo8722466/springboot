package com.springboot.socket.waringsms.service;


import com.springboot.socket.waringsms.request.*;
import com.springboot.socket.waringsms.response.Result;
import com.springboot.socket.waringsms.response.SMSResponsePacket;
import com.springboot.socket.waringsms.utils.JaxbUtil;
import com.springboot.socket.waringsms.vo.SmsBodyVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class SMSSendService {
    
    @Value("${sms.success}")
    private String SMS_SUCCESS;
    @Value("${service.code}")
    private String ServiceCode;
    @Value("${service.scene}")
    private String ServiceScene;
    @Value("${consumer.id}")
    private String ConsumerId;
    @Value("${orgconsumer.id}")
    private String OrgConsumerId;
    @Value("${system.id}")
    private String SystemId;
    @Value("${tran.mode}")
    private String TranMode;
    @Value("${txn.no}")
    private String TxnNo;

    public String getSMS_SUCCESS() {
        return SMS_SUCCESS;
    }

    public void setSMS_SUCCESS(String SMS_SUCCESS) {
        this.SMS_SUCCESS = SMS_SUCCESS;
    }

    public String getServiceCode() {
        return ServiceCode;
    }

    public void setServiceCode(String serviceCode) {
        ServiceCode = serviceCode;
    }

    public String getServiceScene() {
        return ServiceScene;
    }

    public void setServiceScene(String serviceScene) {
        ServiceScene = serviceScene;
    }

    public String getConsumerId() {
        return ConsumerId;
    }

    public void setConsumerId(String consumerId) {
        ConsumerId = consumerId;
    }

    public String getOrgConsumerId() {
        return OrgConsumerId;
    }

    public void setOrgConsumerId(String orgConsumerId) {
        OrgConsumerId = orgConsumerId;
    }

    public String getSystemId() {
        return SystemId;
    }

    public void setSystemId(String systemId) {
        SystemId = systemId;
    }

    public String getTranMode() {
        return TranMode;
    }

    public void setTranMode(String tranMode) {
        TranMode = tranMode;
    }

    public String getTxnNo() {
        return TxnNo;
    }

    public void setTxnNo(String txnNo) {
        TxnNo = txnNo;
    }

    private String smsHost;

    private Integer smsPort;

    public String getSmsHost() {
        return smsHost;
    }

    public void setSmsHost(String smsHost) {
        this.smsHost = smsHost;
    }

    public Integer getSmsPort() {
        return smsPort;
    }

    public void setSmsPort(Integer smsPort) {
        this.smsPort = smsPort;
    }

    private Logger logger = LoggerFactory.getLogger(SMSSendService.class);

    public void sendSMSwarning(List<SmsBodyVO> smsBodyVOList){
        Socket socket = null;
        boolean isSendSmsSuccess = false;
        try {
            socket = this.init();
            if (socket == null) {
                logger.error("connect the sms server failed !");
                return;
            }
            //给服务端发送响应信息
            InputStream is = socket.getInputStream();
            //接受服务端消息并打印
            OutputStream os = socket.getOutputStream();
            for (SmsBodyVO smsBodyVO : smsBodyVOList) {
                String[] smsAddresArray = smsBodyVO.getSmsAddressee().trim().split(",");
                List<String> smsAddresList = Arrays.asList(smsAddresArray);
                StringBuffer smsAddresss = new StringBuffer();
                for (String address : smsAddresList) {
                    if (smsAddresss.length() == 0) {
                        smsAddresss.append(address);
                    } else {
                        smsAddresss.append("|" + address);
                    }
                }
                SMSRequestPacket smsRequestPacket = format(TxnNo, smsAddresss.toString(), smsBodyVO.getSmsContent());
                SMSResponsePacket responsePacket = invokeSMSSender(is, os, smsRequestPacket);
                if (responsePacket == null) {
                    logger.error("send warning sms error: the response is null!");
                    continue;
                }
                Result result = responsePacket.getSysHead().getResult().get(0);
                if (!SMS_SUCCESS.equalsIgnoreCase(result.getReturnCode())) {
                    logger.error("send warning sms fail: ReturnCode: " + result.getReturnCode() + " ReturnMsg: " + result.getReturnMsg());
                    continue;
                }
                isSendSmsSuccess = true;
                logger.error("send warning sms success: ReturnCode: " + result.getReturnCode() + " ReturnMsg: " + result.getReturnMsg());
            }

            is.close();
            os.close();
            if (isSendSmsSuccess) {
                logger.info("send warning sms success !");
                return;
            }
            logger.info("send warning sms failed !");
            return;

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        finally {
            this.smsHost = null;
            this.smsPort = null;
            try {
                if (socket != null ) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Socket init(){
        try {
            //对服务端发起连接请求
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(this.smsHost, this.smsPort), 5000);
            socket.setSoTimeout(3000);
            if (!socket.isConnected()) {
                logger.error("connect to the sms(" + this.smsHost + ":" + this.smsPort + ") failed ! isConnected: false");
                return null;
            }
            return socket;
        }
        catch (Exception ex){
            logger.error("connect to the sms("+this.smsHost+":"+this.smsPort+") occur error: "+ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
    
    public SMSResponsePacket invokeSMSSender(InputStream is, OutputStream os, SMSRequestPacket requestPacket){
        try {
            String requestXml = convertRequestToXml(requestPacket);
            Integer length = requestXml.length();
            String requestPacketHeader = StringUtils.leftPad(String.valueOf(length),8, "0");
            //转UTF-8格式
            String request = new String((requestPacketHeader+requestXml).getBytes(Charset.forName("UTF-8")), Charset.forName("UTF-8"));
            logger.info("SMSwarning request parameters:  "+ request);
            os.write(request.getBytes());
            os.flush();
            while(true) {
                if (is.available() > 0) {
                    byte[] responsePacketHeader = new byte[8];
                    is.read(responsePacketHeader, 0, 8);
                    //int packetSize = Integer.parseInt(new String(responsePacketHeader));
                    byte[] body = new byte[is.available()];
                    is.read(body);
                    String response = new String(body, Charset.forName("UTF-8")).trim();
                    logger.info("SMSwarning response parameters:  " + response);
                    return convertXmlToResponse(new String(body).trim());
                }
            }
        } catch (IOException e) {
            logger.error("SMSwarning sender occur exception:  "+ e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public String convertRequestToXml(SMSRequestPacket requestPacket){
        try {
            return JaxbUtil.convertToXml(requestPacket).replace("arrayReplace","array");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public SMSResponsePacket convertXmlToResponse(String xml){
        try {
            return JaxbUtil.converyToJavaBean(xml, SMSResponsePacket.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public SMSRequestPacket format(String TxnNo,String MblNo, String MsgCntntInf){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String dateStamp = df.format(new Date());
        String TranDate = dateStamp.substring(0,7);
        String TranTime= dateStamp.substring(8,13);
        String ConsumerSeqNo= ConsumerId+dateStamp.substring(0,8)+"ESPC0"+dateStamp.substring(8,16);
        String OrgConsumerSeqNo= ConsumerId+dateStamp.substring(0,8)+"ESPC1"+dateStamp.substring(8,16);

        ResqSysHead sysHead = ResqSysHead.builder().serviceCode(ServiceCode).serviceScene(ServiceScene).consumerId(ConsumerId)
                .orgConsumerId(OrgConsumerId).systemId(SystemId).consumerSeqNo(ConsumerSeqNo).orgConsumerSeqNo(OrgConsumerSeqNo)
                .servSeqNo("").terminalCode("").orgTerminalCode("").consumerSvrId("").orgConsumerSvrId("").destSvrId("").tranMode(TranMode)
                .hstDate("").hstTime("").tranDate(TranDate).tranTime(TranTime).userLang("").build();
        List<ApprTellerArray> apprTellerArrayList = new ArrayList<ApprTellerArray>();
        ApprTellerArray apprTellerArray = ApprTellerArray.builder().apprTellerNo("").apprBranchId("").apprTellerLevel("").apprTellerType("").authFlag("").build();
        apprTellerArrayList.add(apprTellerArray);
        List<AuthTellerInfo> authTellerInfoList = new ArrayList<AuthTellerInfo>();
        AuthTellerInfo authTellerInfo = AuthTellerInfo.builder().authTellerNo("").authBranchId("").authTellerPassword("").authTellerLevel("").authTellerType("").build();
        authTellerInfoList.add(authTellerInfo);
        AppHead appHead = AppHead.builder().tranTellerNo("").tranBranchId("").tranTellerPassword("").tranTellerLevel("")
                .tranTellerType("").apprFlag("").apprTellerArray(apprTellerArrayList).authTellerInfo(authTellerInfoList).build();
        ResqBody body = ResqBody.builder().txnNo(TxnNo).mblNo(MblNo).msgCntntInf(MsgCntntInf).build();
        SMSRequestPacket requestPacket = SMSRequestPacket.builder().version("2.0").sysHead(sysHead).appHead(appHead).body(body).build();
        return requestPacket;
    }
}