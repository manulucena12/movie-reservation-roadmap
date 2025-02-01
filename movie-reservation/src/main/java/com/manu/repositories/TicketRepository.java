package com.manu.repositories;

import com.manu.entities.TicketEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

  @Query(nativeQuery = true, value = "SELECT * FROM tickets WHERE owner = ?1")
  List<TicketEntity> findByOwner(Long id);
}
