package fr.insee.bar.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Errors;

import fr.insee.bar.dao.ClientDao;
import fr.insee.bar.model.Client;
import fr.insee.bar.service.EmployeService;
import fr.insee.bar.validator.ClientValidator;

public class NouveauClientControllerTestCase {

	@Mock
	private ClientDao clientDao;

	@Mock
	private ClientValidator clientValidator;

	@Mock
	private EmployeService employeService;

	@InjectMocks
	private NouveauClientController nouveauClientController;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders
			.standaloneSetup(nouveauClientController)
			.build();
	}

	@Test
	public void nouveauClientPostSuccess() throws Exception {
		doNothing()
			.when(clientValidator)
			.validate(any(Client.class), any(Errors.class));
		when(clientDao.insert(any(Client.class))).thenReturn(null);
		this.mockMvc
			.perform(post("/client/nouveau")
				.param("nom", "Pr�nom Nom")
				.param("email", "prenom.nom@gmail.com")
				.param("dateNaissance", "21/04/1984"))
			.andExpect(status().isFound())
			.andExpect(model().attributeDoesNotExist("client"))
			.andExpect(model().hasNoErrors())
			.andExpect(redirectedUrl("/clients"))
			.andExpect(flash().attributeExists("nouveauClient"));
	}

	@Test
	public void nouveauClientPostError() throws Exception {
		doNothing()
			.when(clientValidator)
			.validate(any(Client.class), any(Errors.class));
		when(clientDao.insert(any(Client.class))).thenReturn(null);
		this.mockMvc
			.perform(post("/client/nouveau")
				.param("nom", "Pr�nom Nom")
				.param("email", "")
				.param("dateNaissance", "21/04/1984"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("client"))
			.andExpect(model().errorCount(1))
			.andExpect(view().name("nouveau-client"));
	}
}
