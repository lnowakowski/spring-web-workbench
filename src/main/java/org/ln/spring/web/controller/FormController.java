package org.ln.spring.web.controller;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.ConstraintViolationException;
import org.ln.spring.web.dto.SuperItemBuilder;
import org.ln.spring.web.jpa.entities.SuperItem;
import org.ln.spring.web.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/form")
@SessionAttributes("item")
public class FormController {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private TestService testService;

	@Autowired
	public void setTestService(TestService testService) {
		this.testService = testService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String handleItemSubmit(
			@Valid @ModelAttribute("item") SuperItem item, BindingResult result) {
		if (result.hasErrors()) {
			log.warn("Form with errors");
			return "form";
		}

		try {
			testService.saveItem(item);
		}
		catch (Exception e) {
//			boolean r = e.getCause().getCause() instanceof ConstraintViolationException;
			
			result.rejectValue("name", "error.not_unique");
			
			return "form";
		}
		
		return "redirect:form";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String mainView(Model model) {
		log.debug("Form called");

		model.addAttribute(
				"item",
				SuperItemBuilder.create()
						.withNumber((int) System.currentTimeMillis()).build());

		List<SuperItem> items = testService.getItems();
		
		Collections.sort(items);
		
		model.addAttribute("items", items);
		
		return "form";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String handleEdit(@PathVariable("id") long id, Model model) throws NoSuchFieldException {
		log.debug("Form called");
		
		model.addAttribute(
				"item",
				testService.getItem(id));
		
		return "form";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String handleDelete(@PathVariable("id") long id) throws NoSuchFieldException {
		testService.removeItem(id);
		
		return "redirect:/form";
	}
}
