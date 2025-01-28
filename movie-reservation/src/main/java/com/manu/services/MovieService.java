package com.manu.services;

import com.manu.dtos.requests.NewMovieRequest;
import com.manu.dtos.responses.HttpCustomResponse;
import com.manu.entities.MovieEntity;
import com.manu.exceptions.ResourceNotFoundException;
import com.manu.repositories.MovieRepository;
import com.manu.repositories.RoomRepository;
import com.manu.repositories.SeatRepository;
import com.manu.utils.MovieUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

  @Autowired private MovieRepository movieRepository;
  @Autowired private SeatRepository seatRepository;
  @Autowired private RoomRepository roomRepository;

  public HttpCustomResponse<Object> createMovie(NewMovieRequest body) {
    try {
      List<MovieEntity> movies = new java.util.ArrayList<>(List.of());
      var room =
          roomRepository
              .findById(body.getRoom())
              .orElseThrow(() -> new ResourceNotFoundException("Nothing"));
      for (int i = 0; i < body.getDays().size(); i++) {
        var movie =
            movieRepository.save(
                new MovieEntity(
                    body.getName(), room, body.getMonth() + ", " + body.getDays().get(i)));
        var seats =
            MovieUtils.createSeats(
                room.getRows(), room.getColums(), body.getPrice(), movie, seatRepository);
        movie.setSeats(seats);
        var newMovie = movieRepository.save(movie);
        movies.add(newMovie);
      }
      return new HttpCustomResponse<>(201, movies, null);

    } catch (Exception e) {
      if (e instanceof ResourceNotFoundException) {
        return new HttpCustomResponse<>(400, null, "Room not found");
      }
      System.out.println(e);
      return new HttpCustomResponse<>(400, null, "Internal Server Error");
    }
  }
}
