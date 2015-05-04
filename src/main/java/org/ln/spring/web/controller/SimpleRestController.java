package org.ln.spring.web.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleRestController {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@ResponseBody 
	@Secured("hasRole('ADMIN')")
	@RequestMapping(value = "/user/{id}", produces = "application/json")
	public User handleTest(@PathVariable("id") int userId, Model model, HttpSession session) {
		model.addAttribute("userId", userId);
		
		log.info("Request: uid={}, model={}, session={}", userId, model, session);

		return new User(userId);
	}
	
	public static class User {
		private long created = System.currentTimeMillis();
		private String name;
		private int id;
		
		public User(int id) {
			this.id = id;
			this.name = "user_" + id;
		}

		public long getCreated() {
			return created;
		}

		public void setCreated(long created) {
			this.created = created;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
	}
}
