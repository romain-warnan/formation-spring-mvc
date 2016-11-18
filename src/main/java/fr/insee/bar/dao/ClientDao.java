package fr.insee.bar.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableMap;

import fr.insee.bar.model.Client;
import fr.insee.bar.model.Client.Titre;

@Repository
public class ClientDao {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private ClientRowMapper rowMapper;

	private NamedParameterJdbcTemplate template;

	private static final String SQL_FIND = "select * from clients where id = :id";
	private static final String SQL_FIND_BY_EMAIL = "select * from clients where email = :email";
	private static final String SQL_FIND_ALL = "select * from clients";
	private static final String SQL_INSERT = "insert into clients (id, nom, email, date_naissance, titre) values (next value for seq, :nom, :email, :dateNaissance, :titre)";
	private static final String SQL_UPDATE = "update clients set nom = :nom, email = :email, date_naissance = :dateNaissance, titre = :titre where id = :id";

	@PostConstruct
	private void postConstruct() {
		this.template = new NamedParameterJdbcTemplate(dataSource);
	}

	public Optional<Client> find(Short id) {
		try {
			Client client = template.queryForObject(SQL_FIND, ImmutableMap.of("id", id), rowMapper);
			return Optional.of(client);

		}
		catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public Optional<Client> findByEmail(String email) {
		try {
			Client client = template.queryForObject(SQL_FIND_BY_EMAIL, ImmutableMap.of("email", email), rowMapper);
			return Optional.of(client);

		}
		catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public List<Client> findAll() {
		return template.query(SQL_FIND_ALL, rowMapper);
	}

	public Client insert(Client client) {
		template.update(SQL_INSERT, ImmutableMap.of(
			"nom", client.getNom(),
			"email", client.getEmail(),
			"dateNaissance", client.getDateNaissance(),
			"titre", client.getTitre().getCode()));
		return this.findByEmail(client.getEmail()).get();
	}

	public Client update(Client client) {
		template.update(SQL_UPDATE, ImmutableMap.of(
			"nom", client.getNom(),
			"email", client.getEmail(),
			"dateNaissance", client.getDateNaissance(),
			"titre", client.getTitre().getCode(),
			"id", client.getId()));
		return client;
	}

	@Component
	public class ClientRowMapper implements RowMapper<Client> {

		@Override
		public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
			Client client = new Client();
			client.setId(rs.getShort("id"));
			client.setNom(rs.getString("nom"));
			client.setEmail(rs.getString("email"));
			client.setDateNaissance(rs.getDate("date_naissance"));
			client.setTitre(Titre.of(rs.getShort("titre")));
			return client;
		}

	}
}
