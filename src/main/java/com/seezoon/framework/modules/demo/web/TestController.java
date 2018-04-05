package com.seezoon.framework.modules.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seezoon.framework.common.context.beans.ResponeModel;

@Controller
public class TestController {

	@ResponseBody
	@RequestMapping("/t.do")
	public ResponeModel t() {
		return ResponeModel.ok();
	}
}
