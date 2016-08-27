package com.lab516.web;

import java.net.URLDecoder;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lab516.base.BaseUtils;
import com.lab516.base.Consts;
import com.lab516.support.map.EhModelMap;
import com.lab516.support.request.EhWebRequest;

@Controller
public class Login {

	private final String FTL = "Login.ftl";

	@RequestMapping("login")
	public String loadPage(EhWebRequest req, EhModelMap map, HttpSession session) {
		String kickOutMsg = (String) session.getAttribute(Consts.KICKOUT_MSG);
		if (BaseUtils.isEmpty(kickOutMsg)) {
			map.putErrorMessage(kickOutMsg);
		}

		return FTL;
	}

	@RequestMapping("loginsubmit")
	public String onSubmit(EhWebRequest req, EhModelMap map, String user_id, String password, HttpSession session) {
		session.removeAttribute(Consts.KICKOUT_MSG);

		try {
			String uid = URLDecoder.decode(user_id, "UTF-8");
			String pwd = URLDecoder.decode(password, "UTF-8");

			UsernamePasswordToken token = new UsernamePasswordToken(uid, pwd);
			token.setRememberMe(false);

			Subject user = SecurityUtils.getSubject();
			user.login(token);
		} catch (Exception ex) {
			map.putModel("ex", ex); // 超时登陆
			return FTL;
		}

		return "redirect:home.do";
	}

	@RequestMapping("logout")
	public String logout(EhWebRequest req) {
		SecurityUtils.getSubject().logout();
		return FTL;
	}

}
