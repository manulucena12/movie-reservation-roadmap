package com.manu.repositories;

import com.manu.dtos.responses.movies.MovieDto;
import com.manu.entities.MovieEntity;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

  @Query(
      nativeQuery = true,
      value = "SELECT id, name, date, schedule, minutes FROM movies WHERE date = ?1")
  List<MovieDto> findAllByDate(String date);

  @Query(
      nativeQuery = true,
      value = "SELECT id, name, date, schedule, minutes FROM movies WHERE name = ?1")
  List<MovieDto> findAllByName(String name);

  @Modifying
  @Query(nativeQuery = true, value = "UPDATE movies SET name = ?1, minutes = ?2 WHERE name = ?3")
  @Transactional
  void updateByName(String name, int minutes, String old);

  @Modifying
  @Query(nativeQuery = true, value = "UPDATE movies SET date = ?1, schedule = ?2 WHERE id = ?3")
  @Transactional
  void updateById(String date, String schedule, Long id);

  @Modifying
  @Query(nativeQuery = true, value = "UPDATE seats SET price = ?1 WHERE movie = ?2")
  @Transactional
  void updatePriceById(double price, Long id);

  @Modifying
  @Query(nativeQuery = true, value = "DELETE movies WHERE date = ?1")
  @Transactional
  void deleteAllByDate(String date);

  @Modifying
  @Query(nativeQuery = true, value = "DELETE seats WHERE movie = ?1")
  @Transactional
  void deleteMovieSeats(Long id);

  @Modifying
  @Query(nativeQuery = true, value = "DELETE tickets WHERE movie = ?1")
  @Transactional
  void deleteMovieTickets(String name);
}
