package fr.insee.bar.resolver;

import fr.insee.bar.dao.ClientDao;
import fr.insee.bar.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Component
public class ClientsResolver implements HandlerMethodArgumentResolver {

	@Autowired
	private ClientDao clientDao;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (List.class.equals(parameter.getParameterType())) {
			Type type = parameter.getGenericParameterType();
			if (type instanceof ParameterizedType) {
				Type[] types = ((ParameterizedType) type).getActualTypeArguments();
				return types[0].getTypeName().equals(Client.class.getCanonicalName());
			}
		}
		return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		return clientDao.findAll();
	}

}
