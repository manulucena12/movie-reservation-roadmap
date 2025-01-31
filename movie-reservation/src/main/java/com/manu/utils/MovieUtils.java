package com.manu.utils;

import com.manu.entities.MovieEntity;
import com.manu.entities.RoomEntity;
import com.manu.entities.SeatEntity;
import com.manu.repositories.MovieRepository;
import com.manu.repositories.SeatRepository;
import java.time.LocalDate;
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
      int month,
      String name,
      RoomEntity room,
      MovieRepository movieRepository,
      SeatRepository seatRepository) {
    List<MovieEntity> movies = new java.util.ArrayList<>(List.of());
    for (int i = 0; i < days.size(); i++) {
      for (int j = 0; j < hours.size(); j++) {
        var year = LocalDate.now().getYear();
        var monthNumber = month < 10 ? "0" + month : String.valueOf(month);
        var dayNumber = days.get(i) < 10 ? "0" + days.get(i) : String.valueOf(days.get(i));
        var date = LocalDate.of(year, Integer.parseInt(monthNumber), Integer.parseInt(dayNumber));
        var movie =
            movieRepository.save(
                new MovieEntity(name, room, date.toString(), hours.get(j), minutes));
        var seats = createSeats(room.getRows(), room.getColums(), price, movie, seatRepository);
        movie.setSeats(seats);
        var newMovie = movieRepository.save(movie);
        movies.add(newMovie);
      }
    }
    return movies;
  }

  public static boolean checkDateBefore(String date) {
    var today = LocalDate.now();
    String year = date.substring(0, 4);
    String month = date.substring(5, 7);
    String day = date.substring(8);
    return today.isBefore(
        LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)));
  }
}
