package org.ln.spring.web.jpa.repositories;

import org.ln.spring.web.jpa.entities.SuperItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperItemRepository extends JpaRepository<SuperItem, Long> {

}
