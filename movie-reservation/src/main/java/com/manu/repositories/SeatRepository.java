package com.manu.repositories;

import com.manu.entities.SeatEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, Long> {

  @Modifying
  @Query(nativeQuery = true, value = "UPDATE seats SET available = false WHERE id = ?1")
  @Transactional
  void updateDisponibilityById(Long id);
}
