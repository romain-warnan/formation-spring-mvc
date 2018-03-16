package fr.insee.bar.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import fr.insee.bar.model.Employe;
import fr.insee.bar.service.EmployeService;

@Component
public class DroitsInterceptor extends HandlerInterceptorAdapter implements HandlerInterceptor {

	@Autowired
	private EmployeService employeService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Employe employe = (Employe) request.getSession(true).getAttribute("employe");
		employeService.verifierResponsable(employe);
		return true;
	}
}
