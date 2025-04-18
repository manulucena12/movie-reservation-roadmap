# Movie Reservation Backend For Roadmap.sh

[![Movie Pipeline](https://github.com/manulucena12/movie-reservation-roadmap/actions/workflows/pipeline.yml/badge.svg)](https://github.com/manulucena12/movie-reservation-roadmap/actions/workflows/pipeline.yml)

This project is created for the challenge of the roadmap website  
<https://roadmap.sh/projects/movie-reservation-system>

This challenge requires the implementation of some mandatory features, such as authentication, movie management, reservations, and permissions.

If you download the backend, you can test it directly without additional configuration since it is set up with an in-memory H2 database and includes a user with the role **admin** to test the endpoints. If you run it locally, you can find the documentation of this project at [http://localhost:3002/swagger-ui/index.html](http://localhost:3002/swagger-ui/index.html). You must specify the credentials of the user using env variables.

## What Can Admins Do?

Admins can administrate all the infrastructure of the application. They can:

- **Create Rooms:**  
  Define rooms by specifying the number of rows and columns, with the option to mark specific seats as unavailable in case of issues.

- **Movie Management:**  
  List the movies that will be streamed at the cinema. When a movie is linked with a room, it automatically creates a list of seats with a single name (for example, seat A1).  
  The seats have prices and can be associated with users via tickets. These tickets ensure that the booked seats are reserved exclusively for those users.

  ![Movie Management](https://raw.githubusercontent.com/manulucena12/movie-reservation-roadmap/refs/heads/main/public/create.jpg)

- **Schedule Movies:**  
  When a movie is listed, copies are created following a schedule (for example, from the 12th to the 14th of July, Spiderman will be streamed at 6 pm, 7:30 pm, and 9 pm). Although the movies have the same name, they are different because of their date and schedule.


## What Can Users Do?

Users have the following capabilities:

- **Account Management:**  
  Create an account and log in.

- **Add Cash:**  
  Deposit money into their accounts to purchase movie tickets.

- **Reservation Cancellation:**  
  Cancel a ticket reservation and have the money refunded, provided that the movie has not been streamed yet. When a reservation is cancelled, the associated seat becomes available for others.

    ![Reservartion](https://raw.githubusercontent.com/manulucena12/movie-reservation-roadmap/refs/heads/main/public/cancel.jpg)

- **Search Movies:**  
  Search for movies by various parameters such as day or name, and retrieve detailed information about a movie and its available seats.

  ![Search Movies](https://github.com/manulucena12/movie-reservation-roadmap/blob/main/public/search.jpg?raw=true)

- **View Tickets:**  
  If a seat has been booked, users can view their tickets for the movie they intend to watch, ensuring exclusive access to their reserved seat.

## What Resources Did I Use?

- **Authentication:**  
  I chose HTTP Basic Authentication with Basic Tokens (to experiment with an alternative to JWT).

  ![Authentication](https://github.com/manulucena12/movie-reservation-roadmap/blob/main/public/auth.jpg?raw=true)

- **Documentation:**  
  Swagger is used for API documentation.

  ![Swagger Documentation](https://github.com/manulucena12/movie-reservation-roadmap/blob/main/public/docs.jpg?raw=true)

- **Security:**  
  Spring Security manages permissions and endpoint protection.

- **Testing:**  
  WebTestClient is utilized to ensure that the backend operates correctly.