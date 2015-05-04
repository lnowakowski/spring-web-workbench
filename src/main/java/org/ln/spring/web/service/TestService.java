package org.ln.spring.web.service;

import java.util.List;

import org.ln.spring.web.jpa.entities.SuperItem;

public interface TestService {
	List<SuperItem> getItems();

	SuperItem getItem(long id) throws NoSuchFieldException;
	
	void removeItem(long id) throws NoSuchFieldException;

	SuperItem saveItem(SuperItem item);
}
