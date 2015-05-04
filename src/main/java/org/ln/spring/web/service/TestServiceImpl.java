package org.ln.spring.web.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.ln.spring.web.config.aop.Loggable;
import org.ln.spring.web.jpa.entities.SuperItem;
import org.ln.spring.web.jpa.repositories.SuperItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestServiceImpl implements TestService {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SuperItemRepository repository;

	@PostConstruct
	public void init() {
		validateDatabase();
	}
	
	@Loggable
	@Transactional(readOnly = true)
	private void validateDatabase() {
		log.debug("Count: {}", repository.count());
		repository.findAll().forEach(this::handleItem);
	}
	
	private void handleItem(SuperItem entity) {
		log.debug("Item: {}", entity);
	}
	
	@Override
	@Loggable
	@Transactional(readOnly = true)
	public List<SuperItem> getItems() {
		return repository.findAll(new Sort(Direction.ASC, "name"));
	}

	@Override
	@Transactional(readOnly = true)
	public SuperItem getItem(long id) throws NoSuchFieldException {
		SuperItem item = repository.findOne(id);
		
		if (item == null) {
			throw new NoSuchFieldException("" + id);
		}

		return item;
	}
	
	@Override
	@Loggable
	@Transactional
	public void removeItem(long id) throws NoSuchFieldException {
		if (!repository.exists(id)) {
			throw new NoSuchFieldException("" + id);
		}
		
		repository.delete(id);
	}

	@Override
	@Loggable
	@Transactional
	public SuperItem saveItem(SuperItem item) {
		return repository.save(item);
	}
}
