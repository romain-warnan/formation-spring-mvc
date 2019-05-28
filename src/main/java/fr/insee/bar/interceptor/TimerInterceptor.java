package fr.insee.bar.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.base.Stopwatch;

@Component
public class TimerInterceptor extends HandlerInterceptorAdapter implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Stopwatch stopwatch = Stopwatch.createStarted();
		request.setAttribute("stopwatch", stopwatch);
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler, Exception ex) throws Exception {
		Stopwatch stopwatch = (Stopwatch) request.getAttribute("stopwatch");
		System.out.println(request.getRequestURI() + " - " + stopwatch.toString());
		stopwatch.stop();
	}

}
