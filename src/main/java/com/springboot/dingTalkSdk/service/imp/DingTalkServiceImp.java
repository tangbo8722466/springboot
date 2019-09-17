package com.springboot.dingTalkSdk.service.imp;

import com.springboot.Utils.RestResult;
import com.springboot.Vo.response.Empty;
import com.springboot.dingTalkSdk.biz.DingTalkBiz;
import com.springboot.dingTalkSdk.service.DingTalkService;
import com.springboot.dingTalkSdk.vo.request.LinkRequestVo;
import com.springboot.dingTalkSdk.vo.request.MarkDownRequestVo;
import com.springboot.dingTalkSdk.vo.request.TextRequestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DingTalkServiceImp implements DingTalkService {

    @Autowired
    DingTalkBiz dingTalkBiz;

    @Override
    public RestResult<Empty> sendTextIntoQueue(TextRequestVo requestVo) {
        return dingTalkBiz.sendTextIntoQueue(requestVo);
    }

    @Override
    public RestResult<Empty> sendLinkIntoQueue(LinkRequestVo requestVo) {
        return dingTalkBiz.sendLinkIntoQueue(requestVo);
    }

    @Override
    public RestResult<Empty> sendMarkdownIntoQueue(MarkDownRequestVo requestVo) {
        return dingTalkBiz.sendMarkdownIntoQueue(requestVo);
    }
}
