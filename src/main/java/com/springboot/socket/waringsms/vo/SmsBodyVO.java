package com.springboot.socket.waringsms.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmsBodyVO {
    private String smsAddressee;
    private String smsContent;
}
