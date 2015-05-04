package org.ln.spring.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ln.spring.web.config.MvcConfiguration;
import org.ln.spring.web.dto.SuperItemBuilder;
import org.ln.spring.web.jpa.entities.SuperItem;
import org.ln.spring.web.service.TestService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes = {
	MvcConfiguration.class
})
public class FormControllerTest {
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	@InjectMocks
	private FormController formController;

	@Mock
	private TestService testService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void testRequest() throws Exception {
		SuperItem item = SuperItemBuilder.create().withId(1L).build();

		when(testService.getItems()).thenReturn(Arrays.asList(item));

		mockMvc.perform(get("/form"))
				.andExpect(status().isOk())
				.andExpect(view().name("form"))
				.andExpect(forwardedUrl("/WEB-INF/jsp/form.jsp"))
				.andExpect(model().attributeExists("items"))
				.andExpect(model().attribute("items", containsInAnyOrder(item)))
				.andExpect(
						model().attribute("items",
								hasItem(hasProperty("id", is(1L)))));

		verify(testService, times(1)).getItems();
		verifyNoMoreInteractions(testService);
	}
}
