package spring.esla.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import spring.esla.beans.Member;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		// session검사
		HttpSession session = request.getSession();

		Member user =(Member) session.getAttribute("user");
			   if (user == null || user.getRank() < 100) {
			   // 처리를 끝냄 - 컨트롤로 요청이 가지 않음.
			   response.sendRedirect("/esla/exception");
			   return false;
			  }

		return true;
	}
}
