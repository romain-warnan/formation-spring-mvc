package fr.insee.bar.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.insee.bar.dao.ClientDao;
import fr.insee.bar.exception.BarClientException;
import fr.insee.bar.model.Client;

@Component
public class ClientConverter implements Converter<String, Client> {

	@Autowired
	private ClientDao clientDao;

	@Override
	public Client convert(String id) {
		return clientDao
			.find(Short.valueOf(id))
			.orElseThrow(() -> new BarClientException(id));
	}
}
