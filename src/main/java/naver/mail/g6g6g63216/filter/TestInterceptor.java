package naver.mail.g6g6g63216.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import naver.mail.g6g6g63216.vo.UserVO;

public class TestInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("[INTERCEPTOR] " + request.getRequestURI());
		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO) session.getAttribute("user");

		if (loginUser == null) {

			session.setAttribute("nextUri", request.getRequestURI());
			// return "redirect:/login";
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		} else {
			
			return true;
		}

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}
	/*
	 * @Override public void afterConcurrentHandlingStarted(HttpServletRequest
	 * request, HttpServletResponse response, Object handler) throws Exception {
	 * super.afterConcurrentHandlingStarted(request, response, handler); }
	 */
}
