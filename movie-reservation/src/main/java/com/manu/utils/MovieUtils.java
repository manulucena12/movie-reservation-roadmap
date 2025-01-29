package com.manu.utils;

import com.manu.entities.MovieEntity;
import com.manu.entities.RoomEntity;
import com.manu.entities.SeatEntity;
import com.manu.repositories.MovieRepository;
import com.manu.repositories.SeatRepository;
import java.util.List;

public class MovieUtils {

  private static String getSeatName(int i, int j) {
    return switch (i) {
      case 0 -> "A" + (j + 1);
      case 1 -> "B" + (j + 1);
      case 2 -> "C" + (j + 1);
      case 3 -> "D" + (j + 1);
      case 4 -> "E" + (j + 1);
      case 5 -> "F" + (j + 1);
      case 6 -> "G" + (j + 1);
      case 7 -> "H" + (j + 1);
      case 8 -> "I" + (j + 1);
      case 9 -> "J" + (j + 1);
      case 10 -> "K" + (j + 1);
      default -> "No-Name";
    };
  }

  public static List<SeatEntity> createSeats(
      int rows, int colums, double price, MovieEntity movie, SeatRepository seatRepository) {
    List<SeatEntity> seats = new java.util.ArrayList<>(List.of());
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < colums; j++) {
        var seatName = getSeatName(i, j);
        var newSeat = seatRepository.save(new SeatEntity(seatName, price, true, movie));
        seats.add(newSeat);
      }
    }
    return seats;
  }

  public static List<MovieEntity> createMovies(
      List<Integer> days,
      List<String> hours,
      int minutes,
      double price,
      String month,
      String name,
      RoomEntity room,
      MovieRepository movieRepository,
      SeatRepository seatRepository) {
    List<MovieEntity> movies = new java.util.ArrayList<>(List.of());
    for (int i = 0; i < days.size(); i++) {
      for (int j = 0; j < hours.size(); j++) {
        var movie =
            movieRepository.save(
                new MovieEntity(name, room, month + ", " + days.get(i), hours.get(j), minutes));
        var seats = createSeats(room.getRows(), room.getColums(), price, movie, seatRepository);
        movie.setSeats(seats);
        var newMovie = movieRepository.save(movie);
        movies.add(newMovie);
      }
    }
    return movies;
  }
}
