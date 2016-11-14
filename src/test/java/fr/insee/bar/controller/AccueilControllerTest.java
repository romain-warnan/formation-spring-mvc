package fr.insee.bar.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml", "classpath:servlet-dispatcher.xml" })
public class AccueilControllerTest {

	@InjectMocks
	private AccueilController accueilController;

	private MockMvc mockMvc;

	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders
			.standaloneSetup(accueilController)
			.setViewResolvers(viewResolver())
			.build();
	}

	@Test
	public void welcome() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(status().is(HttpStatus.MOVED_PERMANENTLY.value()))
			.andExpect(model().attributeDoesNotExist("message"));
	}

	@Test
	public void hello() throws Exception {
		mockMvc.perform(get("/accueil"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("message"))
			.andExpect(view().name("accueil"))
			.andExpect(forwardedUrl("/WEB-INF/views/accueil.jsp"));
	}

	private static ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
}
