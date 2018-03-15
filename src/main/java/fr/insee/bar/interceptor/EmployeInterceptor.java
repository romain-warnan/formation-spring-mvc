package fr.insee.bar.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import fr.insee.bar.model.Employe;
import fr.insee.bar.provider.EmployeProvider;

@Component
public class EmployeInterceptor extends HandlerInterceptorAdapter implements HandlerInterceptor {

	@Autowired
	private EmployeProvider employeProvider;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(request.getRequestURI().equals("/")) {
			return true;
		}
		HttpSession session = request.getSession(true);
		Employe employe = (Employe) session.getAttribute("employe");
		if (employe == null) {
			employe = employeProvider.provide();
			session.setAttribute("employe", employe);
		}
		return true;
	}
}
