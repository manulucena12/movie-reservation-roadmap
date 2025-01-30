package com.manu.services;

import com.manu.dtos.requests.NewMovieRequest;
import com.manu.dtos.requests.UpdateGeneralMovieInfoRequest;
import com.manu.dtos.requests.UpdateSeatsPricesRequest;
import com.manu.dtos.requests.UpdateSpecificInfoRequest;
import com.manu.dtos.responses.HttpCustomResponse;
import com.manu.dtos.responses.movies.MovieDto;
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

  public HttpCustomResponse<Object> updateGeneralInfo(UpdateGeneralMovieInfoRequest body) {
    try {
      movieRepository.updateByName(body.getName(), body.getMinutes(), body.getOld());
      var movies = movieRepository.findAllByName(body.getName());
      if (movies.isEmpty()) {
        return new HttpCustomResponse<>(400, null, "Movies not found");
      }
      return new HttpCustomResponse<>(200, movies, null);
    } catch (Exception e) {
      System.out.println(e);
      return new HttpCustomResponse<>(500, null, "Internal Server Error");
    }
  }

  public HttpCustomResponse<Object> updateSpecificInfo(UpdateSpecificInfoRequest body) {
    try {
      movieRepository.updateById(body.getDate(), body.getSchedule(), body.getId());
      var movie =
          movieRepository
              .findById(body.getId())
              .orElseThrow(() -> new ResourceNotFoundException("Nothing"));
      var movieDto =
          new MovieDto(
              movie.getId(),
              movie.getName(),
              movie.getSchedule(),
              movie.getDate(),
              movie.getMinutes());
      return new HttpCustomResponse<>(200, movieDto, null);
    } catch (Exception e) {
      if (e instanceof ResourceNotFoundException) {
        return new HttpCustomResponse<>(400, null, "Movie not found");
      }
      System.out.println(e);
      return new HttpCustomResponse<>(500, null, "Internal Server Error");
    }
  }

  public HttpCustomResponse<Object> updateDayPrices(String date, UpdateSeatsPricesRequest body) {
    try {
      var movies = movieRepository.findAllByDate(date);
      if (movies.isEmpty()) {
        return new HttpCustomResponse<>(400, null, "Movies not found");
      }
      for (int i = 0; i < movies.size(); i++) {
        movieRepository.updatePriceById(body.getPrice(), movies.get(i).getId());
      }
      return new HttpCustomResponse<>(
          200, null, "The prices for the day: " + date + " have been updated");
    } catch (Exception e) {
      System.out.println(e);
      return new HttpCustomResponse<>(500, null, "Internal Server Error");
    }
  }
}
