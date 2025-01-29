package com.manu.services;

import com.manu.dtos.requests.NewMovieRequest;
import com.manu.dtos.responses.HttpCustomResponse;
import com.manu.exceptions.ResourceNotFoundException;
import com.manu.repositories.MovieRepository;
import com.manu.repositories.RoomRepository;
import com.manu.repositories.SeatRepository;
import com.manu.utils.MovieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

  @Autowired private MovieRepository movieRepository;
  @Autowired private SeatRepository seatRepository;
  @Autowired private RoomRepository roomRepository;

  public HttpCustomResponse<Object> createMovie(NewMovieRequest body) {
    try {
      var room =
          roomRepository
              .findById(body.getRoom())
              .orElseThrow(() -> new ResourceNotFoundException("Nothing"));
      var movies =
          MovieUtils.createMovies(
              body.getDays(),
              body.getHours(),
              body.getMinutes(),
              body.getPrice(),
              body.getMonth(),
              body.getName(),
              room,
              movieRepository,
              seatRepository);
      return new HttpCustomResponse<>(201, movies, null);

    } catch (Exception e) {
      if (e instanceof ResourceNotFoundException) {
        return new HttpCustomResponse<>(400, null, "Room not found");
      }
      System.out.println(e);
      return new HttpCustomResponse<>(500, null, "Internal Server Error");
    }
  }

  public HttpCustomResponse<Object> getMovieById(Long id) {
    try {
      var movie =
          movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Nothing"));
      return new HttpCustomResponse<>(200, movie, null);
    } catch (Exception e) {
      if (e instanceof ResourceNotFoundException) {
        return new HttpCustomResponse<>(400, null, "Movie not found");
      }
      System.out.println(e);
      return new HttpCustomResponse<>(500, null, "Internal Server Error");
    }
  }

  public HttpCustomResponse<Object> getMoviesByDate(String date) {
    try {
      return new HttpCustomResponse<>(200, movieRepository.findAllByDate(date), null);
    } catch (Exception e) {
      if (e instanceof ResourceNotFoundException) {
        return new HttpCustomResponse<>(400, null, "Movies not found");
      }
      System.out.println(e);
      return new HttpCustomResponse<>(500, null, "Internal Server Error");
    }
  }

  public HttpCustomResponse<Object> getMoviesByName(String name) {
    try {
      return new HttpCustomResponse<>(200, movieRepository.findAllByName(name), null);
    } catch (Exception e) {
      if (e instanceof ResourceNotFoundException) {
        return new HttpCustomResponse<>(400, null, "Movies not found");
      }
      System.out.println(e);
      return new HttpCustomResponse<>(500, null, "Internal Server Error");
    }
  }
}
