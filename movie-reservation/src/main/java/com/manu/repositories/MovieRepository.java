package com.manu.repositories;

import com.manu.dtos.responses.movies.MovieDto;
import com.manu.entities.MovieEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
