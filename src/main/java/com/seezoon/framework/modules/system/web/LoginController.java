package com.seezoon.framework.modules.system.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seezoon.framework.common.Constants;
import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.utils.IpUtils;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.system.service.SysLoginLogService;
import com.seezoon.framework.modules.system.shiro.ShiroUtils;
import com.seezoon.framework.modules.system.web.form.LoginForm;

@RestController
@RequestMapping("${admin.path}")
public class LoginController extends BaseController{

	@Autowired
	private SysLoginLogService sysLoginLogService;

	@PostMapping("/login.do")
	public ResponeModel login(@Validated LoginForm userForm,BindingResult bindingResult,HttpServletRequest request) {
		Subject subject = ShiroUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(userForm.getLoginName(), userForm.getPassword(),Constants.YES.equals(userForm.getRememberMe()));
		try {
			subject.login(token);
			//登录成功 记录日志
			sysLoginLogService.loginLog(ShiroUtils.getUserId(), IpUtils.getIpAddr(request), request.getHeader("User-Agent"));
		}catch (UnknownAccountException e) {
			return ResponeModel.error("账户密码错误");
		}catch (IncorrectCredentialsException e) {
			//账户密码错误
			return ResponeModel.error("账户密码错误");
		}catch (LockedAccountException e) {
			//账号已被锁定
			return ResponeModel.error("账号已被锁定");
		}catch (AuthenticationException e) {
			//账户密码错误
			return ResponeModel.error("账户密码错误");
		}
		return ResponeModel.ok();
	}
}
