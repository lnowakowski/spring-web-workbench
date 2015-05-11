package org.ln.spring.web.jpa.repositories;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.ln.spring.web.jpa.entities.SuperItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

@TestRepository
public class SampleOtherRepository implements SampleRepository<SuperItem> {
	private JdbcTemplate jdbcTemplate;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<SuperItem> getItems(String name) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<SuperItem> query = cb.createQuery(SuperItem.class);
		Root<SuperItem> root = query.from(SuperItem.class);

		return entityManager.createQuery(
				query.where(cb.like(cb.lower(root.get("name")),
						StringUtils.lowerCase(name) + '%'))).getResultList();
	}
	
	public SuperItem findByDate(Date date) {
		return jdbcTemplate.queryForObject("SELECT * FROM items WHERE created > ? ORDER BY name ASC LIMIT 1", new Object[] {
				DateUtils.addHours(date, -1),
		}, BeanPropertyRowMapper.newInstance(SuperItem.class) /* new RowMapper<SuperItem>() {
			@Override
			public SuperItem mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				if (rowNum > 1) {
					throw new SQLException("Unexpected number of result rows: " + rowNum);
				}
				else {
					SuperItem si = new SuperItem();
					
					si.setId(rs.getLong("id"));
					si.setName(rs.getString("name"));
					si.setCreated(rs.getDate("created"));
					
					return si;
				}
				
//				return null;
			}
		} */);
	}
}
