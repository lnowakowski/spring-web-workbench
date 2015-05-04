package org.ln.spring.web.jpa.entities;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistListener {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@PrePersist
	public void prePersist(SuperItem item) {
		log.debug("prePersist() -> {}", item);
		
		if (item.getCreated() == null) {
			item.setCreated(new Date());
		}
	}

	@PreUpdate
	public void preUpdate(SuperItem item) {
		log.debug("preUpdate() -> {}", item);
	}
}
