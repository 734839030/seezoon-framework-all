package com.seezoon.framework.front;

import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.file.FileHandlerFactory;
import com.seezoon.framework.common.file.handler.FileHandler;
import com.seezoon.framework.common.utils.ZxingHelper;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.front.session.FrontSubject;
import com.seezoon.framework.front.session.FrontUser;
import com.seezoon.framework.front.wechat.service.WechatServiceAPI;

@RestController
@RequestMapping("${front.path}/demo")
public class DemoController extends BaseController{

	@Autowired
	private WechatServiceAPI wechatServiceAPI;
	
	@RequestMapping("/getUserInfo.do")
	public ResponeModel getUserInfo() {
		FrontUser frontUser = FrontSubject.get();
		return ResponeModel.ok(frontUser);
	}
	@RequestMapping("/jsPay.do")
	public ResponeModel jsPay() {
		FrontUser frontUser = FrontSubject.get();
		Map<String, Object> jsPay = wechatServiceAPI.jsPay("商品-测试", RandomStringUtils.randomNumeric(20), 10, frontUser.getUserId(),"demoPayHandler");
		return ResponeModel.ok(jsPay);
	}
	@RequestMapping("/qrPay1.do")
	public ResponeModel qrPay1() {
		String qrCodepay1 = wechatServiceAPI.qrCodePay1("商品-测试", RandomStringUtils.randomNumeric(20), 1,"demoPayHandler");
		//生成二维码
		return ResponeModel.ok(qrCodepay1);
	}
	@RequestMapping("/qrPay2.do")
	public ResponeModel qrPay2() {
		String payQrCode = wechatServiceAPI.getPayQrCode(RandomStringUtils.randomNumeric(20));
		//生成二维码
		return ResponeModel.ok(payQrCode);
	}
}
