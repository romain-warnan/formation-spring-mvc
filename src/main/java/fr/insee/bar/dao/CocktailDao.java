package fr.insee.bar.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import fr.insee.bar.model.Cocktail;
import fr.insee.bar.search.Search;

@Repository
public class CocktailDao {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private CocktailRowMapper rowMapper;

	private NamedParameterJdbcTemplate template;

	private static final String SQL_FIND = "select * from cocktails where id = :id";
	private static final String SQL_FIND_BY_NAME = "select * from cocktails where norm like :q";
	private static final String SQL_FIND_ALL = "select * from cocktails";

	@PostConstruct
	private void postConstruct() {
		this.template = new NamedParameterJdbcTemplate(dataSource);
	}

	public Optional<Cocktail> find(Short id) {
		try {
			Cocktail cocktail = template.queryForObject(SQL_FIND, ImmutableMap.of("id", id), rowMapper);
			return Optional.of(cocktail);

		}
		catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public Cocktail findByExample(Cocktail cocktail) {
		return template.queryForObject(SQL_FIND, ImmutableMap.of("id", cocktail.getId()), rowMapper);
	}

	public List<Cocktail> search(String search) {
		if (StringUtils.isBlank(search)) {
			return Lists.newArrayList();
		}
		String q = "%" + Search.normalize(search) + "%";
		return template.query(SQL_FIND_BY_NAME, ImmutableMap.of("q", q), rowMapper);
	}

	public List<Cocktail> findAll() {
		return template.query(SQL_FIND_ALL, rowMapper);
	}

	@Component
	public class CocktailRowMapper implements RowMapper<Cocktail> {

		@Override
		public Cocktail mapRow(ResultSet rs, int rowNum) throws SQLException {
			Cocktail cocktail = new Cocktail();
			cocktail.setId(rs.getShort("id"));
			cocktail.setNom(rs.getString("nom"));
			cocktail.setPrix(rs.getDouble("prix"));
			return cocktail;
		}

	}
}
