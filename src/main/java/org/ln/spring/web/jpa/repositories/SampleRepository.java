package org.ln.spring.web.jpa.repositories;

import java.util.Date;
import java.util.List;

import org.ln.spring.web.jpa.entities.SuperItem;

public interface SampleRepository<T> {
	List<T> getItems(String name);

	SuperItem findByDate(Date date);
}
