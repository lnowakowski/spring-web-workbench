package org.ln.spring.web.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ln.spring.web.config.DataSourceConfig;
import org.ln.spring.web.jpa.entities.SuperItem;
import org.ln.spring.web.jpa.repositories.SampleRepository;
import org.ln.spring.web.jpa.repositories.SuperItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
@ContextConfiguration(classes = {
	DataSourceConfig.class
})
public class SampleRepositoryTest extends
		AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private SuperItemRepository superItemRepository;

	@Autowired
	private SampleRepository<SuperItem> repository;

	@Before
	public void setUp() {
	}

	@BeforeTransaction
	public void beforeTransaction() {
		System.out.println("* beforeTransaction()");
	}
	
	@AfterTransaction
	public void afterTransaction() {
		System.out.println("* afterTransaction()");
	}
	
	@Test
	public void findAllItemsMatchingName() {
		int countRowsInTableWhere = countRowsInTableWhere("items",
				"name LIKE 'ITEM_%'");

		List<SuperItem> items = repository.getItems("item_");
		
		assertEquals("Unexpected number of rows", countRowsInTableWhere, items.size());
	}
	
	@Test
	public void findItemBySpecifiedDate() {
		SuperItem base = superItemRepository.findOne(0L);
		SuperItem item = repository.findByDate(new Date());
		
		assertNotNull("Required item was not found", item);
		assertThat("Unexpected item found", item, is(base));
		
		System.err.println(item);
	}
}
