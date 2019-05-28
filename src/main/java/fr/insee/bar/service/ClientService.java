package fr.insee.bar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;

import fr.insee.bar.dao.ClientDao;
import fr.insee.bar.model.Client;

@Service
public class ClientService {

	@Autowired
	private ClientDao clientDao;

	public boolean emailDejaUtilise(Client client) {
		return clientDao.findByEmail(client.getEmail())
			.map(Client::getId)
			.filter(id -> !Objects.equal(id, client.getId()))
			.isPresent();
	}
}
