package com.springboot.socket.waringsms.service;

import com.springboot.socket.waringsms.response.Result;
import com.springboot.socket.waringsms.response.SMSResponsePacket;
import com.springboot.socket.waringsms.utils.JaxbUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;

public class TestSendSmsClient {
    public static void main(String[] args) {
        String smsHost = "128.160.12.216";
        Integer smsPort = 9171;
        String requestXml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><service version=\"2.0\"><SYS_HEAD><ServiceCode>12002000004</ServiceCode><ServiceScene>01</ServiceScene><ConsumerId>118001</ConsumerId><OrgConsumerId>118001</OrgConsumerId><SystemId>1</SystemId><ConsumerSeqNo>11800120181128ESPC014434664</ConsumerSeqNo><OrgConsumerSeqNo>11800120181128ESPC114434664</OrgConsumerSeqNo><ServSeqNo></ServSeqNo><TerminalCode></TerminalCode><OrgTerminalCode></OrgTerminalCode><ConsumerSvrId></ConsumerSvrId><OrgConsumerSvrId></OrgConsumerSvrId><DestSvrId></DestSvrId><TranMode>ONLINE</TranMode><HstDate></HstDate><HstTime></HstTime><TranDate>2018112</TranDate><TranTime>14434</TranTime><UserLang></UserLang></SYS_HEAD><APP_HEAD><TranTellerNo></TranTellerNo><TranBranchId></TranBranchId><TranTellerPassword></TranTellerPassword><TranTellerLevel></TranTellerLevel><TranTellerType></TranTellerType><ApprFlag></ApprFlag><array><ApprTellerArray><ApprTellerNo></ApprTellerNo><ApprBranchId></ApprBranchId><ApprTellerLevel></ApprTellerLevel><ApprTellerType></ApprTellerType><AuthFlag></AuthFlag></ApprTellerArray></array><array><AuthTellerInfo><AuthTellerNo></AuthTellerNo><AuthBranchId></AuthBranchId><AuthTellerPassword></AuthTellerPassword><AuthTellerLevel></AuthTellerLevel><AuthTellerType></AuthTellerType></AuthTellerInfo></array></APP_HEAD><BODY><TxnNo>820001</TxnNo><MblNo>15196635394</MblNo><MsgCntntInf>this is a test msg</MsgCntntInf></BODY></service>";
        Socket socket = null;
        try{
            socket = new Socket();
            try {
                socket.connect(new InetSocketAddress(smsHost, smsPort), 5000);
                socket.setSoTimeout(3000);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("connect to the sms(" + smsHost + ":" + smsPort + ") exception !");
            }
            if (!socket.isConnected()) {
                System.out.println("connect to the sms(" + smsHost + ":" + smsPort + ") failed ! isConnected: false");
            }
            //给服务端发送响应信息
            InputStream is = socket.getInputStream();
            //接受服务端消息并打印
            OutputStream os = socket.getOutputStream();
            System.out.println("send warning sms(host: " + smsHost + ",port:" + smsPort + ") parameter: \n" + requestXml.toString());
            //转UTF-8格式
            String request = new String(("00001417"+requestXml).getBytes(Charset.forName("UTF-8")), Charset.forName("UTF-8"));
            os.write(request.getBytes());
            os.flush();
            SMSResponsePacket responsePacket = null;
            while(true) {
                if (is.available() > 0) {
                    byte[] responsePacketHeader = new byte[8];
                    is.read(responsePacketHeader, 0, 8);
                    //int packetSize = Integer.parseInt(new String(responsePacketHeader));
                    byte[] body = new byte[is.available()];
                    is.read(body);
                    String response = new String(body, Charset.forName("UTF-8")).trim();
                    System.out.println("SMSwarning response parameters:  " + response);
                    responsePacket = JaxbUtil.converyToJavaBean(response, SMSResponsePacket.class);
                    break;
                }
            }
            is.close();
            os.close();
            if (responsePacket == null) {
                System.out.println("send warning sms error: the response is null!");
                return;
            }
            Result result = responsePacket.getSysHead().getResult().get(0);
            if (!"000000".equalsIgnoreCase(result.getReturnCode())) {
                System.out.println("send warning sms fail: ReturnCode: " + result.getReturnCode() + " ReturnMsg: " + result.getReturnMsg());
                return;
            }
            System.out.println("send warning sms success: ReturnCode: " + result.getReturnCode() + " ReturnMsg: " + result.getReturnMsg());
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (socket != null ) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
