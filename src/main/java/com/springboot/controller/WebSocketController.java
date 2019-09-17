package com.springboot.controller;


import com.springboot.Utils.RestResult;
import com.springboot.constant.RestResultCodeEnum;
import com.springboot.websocket.WebSocketServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;


@Controller
@RequestMapping("/websocket")
public class WebSocketController {
	//页面请求
	@RequestMapping(value = "/socket/page/{cid}", method = RequestMethod.GET)
	public ModelAndView pageSocket(@PathVariable String cid) {
		ModelAndView mav=new ModelAndView("/socket");
		mav.addObject("cid", cid);
		return mav;
	}

	//页面请求
	@RequestMapping(value = "/socket/{cid}", method = RequestMethod.GET)
	@ResponseBody
	public  RestResult<String> socket(@PathVariable String cid) {
		return new RestResult(RestResultCodeEnum.SUCCESS.code(), "ok.", cid);
	}
	//推送数据接口
	@RequestMapping(value = "/socket/push/{cid}", method = RequestMethod.GET)
	@ResponseBody
	public RestResult<String> pushToWeb(@PathVariable String cid, String message) {
		try {
			WebSocketServer.sendInfo(message,cid);
		} catch (IOException e) {
			e.printStackTrace();
			return new RestResult(RestResultCodeEnum.FAIL.code(), e.getMessage());
		}
		return new RestResult(RestResultCodeEnum.SUCCESS.code(), "ok.", cid);
	}

	//推送数据接口
	@RequestMapping(value = "/socket/pushall", method = RequestMethod.GET)
	@ResponseBody
	public RestResult<String> pushToAllWeb(String message) {
		try {
			WebSocketServer.sendInfo(message,null);
		} catch (IOException e) {
			e.printStackTrace();
			return new RestResult(RestResultCodeEnum.FAIL.code(), e.getMessage());
		}
		return new RestResult(RestResultCodeEnum.SUCCESS.code(), "ok.", "success");
	}
}