package com.qiyun.qy.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class QiYunApi {

	@ResponseBody
	@RequestMapping(path = "/api")
	public String api() {
		return "test api";
	}
	
	
	
}
