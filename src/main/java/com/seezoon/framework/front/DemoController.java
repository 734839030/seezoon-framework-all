package com.seezoon.framework.front;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.front.session.FrontSubject;
import com.seezoon.framework.front.session.FrontUser;

@RestController
@RequestMapping("${front.path}/demo")
public class DemoController extends BaseController{

	@RequestMapping("/getUserInfo.do")
	public ResponeModel getUserInfo() {
		FrontUser frontUser = FrontSubject.get();
		return ResponeModel.ok(frontUser);
	}
}
