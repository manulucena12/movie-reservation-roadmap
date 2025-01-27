package com.manu.repositories;

import com.manu.entities.RoomEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

  @Modifying
  @Query(nativeQuery = true, value = "UPDATE rooms SET unavailable = ?1 WHERE id = ?2")
  void updateRoomById(int unavailable, Long id);

  Optional<RoomEntity> findByName(String name);
}
