package com.manu.repositories;

import com.manu.entities.UserEntity;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findByEmail(String email);

  @Modifying
  @Query(nativeQuery = true, value = "UPDATE users SET balance = ?1 WHERE id = ?2")
  @Transactional
  void updateUserBalance(double newBalance, Long id);
}
