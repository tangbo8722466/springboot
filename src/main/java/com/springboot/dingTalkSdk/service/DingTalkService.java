package com.springboot.dingTalkSdk.service;

import com.springboot.Utils.RestResult;
import com.springboot.Vo.response.Empty;
import com.springboot.dingTalkSdk.vo.request.LinkRequestVo;
import com.springboot.dingTalkSdk.vo.request.MarkDownRequestVo;
import com.springboot.dingTalkSdk.vo.request.TextRequestVo;

public interface DingTalkService {
    /**
     * 发送text消息到队列中
     * @param requestVo
     * @return
     */
    RestResult<Empty> sendTextIntoQueue(TextRequestVo requestVo);

    /**
     * 发送link消息到队列中
     * @param requestVo
     * @return
     */
    RestResult<Empty> sendLinkIntoQueue(LinkRequestVo requestVo);

    /**
     * 发送mark消息到队列中
     * @param requestVo
     * @return
     */
    RestResult<Empty> sendMarkdownIntoQueue(MarkDownRequestVo requestVo);
}
