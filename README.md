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

  ![Movie Management](https://private-user-images.githubusercontent.com/167058363/411248631-b649e5af-5c82-497f-89d9-27dbce67c57b.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MzkwMjk4ODgsIm5iZiI6MTczOTAyOTU4OCwicGF0aCI6Ii8xNjcwNTgzNjMvNDExMjQ4NjMxLWI2NDllNWFmLTVjODItNDk3Zi04OWQ5LTI3ZGJjZTY3YzU3Yi5wbmc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjUwMjA4JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI1MDIwOFQxNTQ2MjhaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT02NDMxMjJjZjAxNWQ0MTQ0OTM3ZmE0NDhhMWFlNjcxZmZlMzI0Zjk2NjVmOWE0YTQ4YzBjZjU1ZWNjMzJkZTU0JlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.ZF9BtGFtU3_Gnind6SYUNOwlWqjkGLP6LiSpAwvWN7s)

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

    ![Reservartion](https://private-user-images.githubusercontent.com/167058363/411248909-e1a7aefa-f3f3-4983-9234-499cbae56164.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MzkwMzAxMDEsIm5iZiI6MTczOTAyOTgwMSwicGF0aCI6Ii8xNjcwNTgzNjMvNDExMjQ4OTA5LWUxYTdhZWZhLWYzZjMtNDk4My05MjM0LTQ5OWNiYWU1NjE2NC5wbmc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjUwMjA4JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI1MDIwOFQxNTUwMDFaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT1iMWE5MTYxNWMzOGNkNGI4NzJiN2MwMGFjOGNiYzM4YzA5ZWZlMjFkYTU4ZjAxODgxYmM2YWM4NmM2ZTk4MTNhJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.SygYYJhcFVzqrXzkI1aOUv2oa-m9YafzEHdYaKvTfnk)

- **Search Movies:**  
  Search for movies by various parameters such as day or name, and retrieve detailed information about a movie and its available seats.

  ![Search Movies](https://private-user-images.githubusercontent.com/167058363/411248226-1db14425-11a6-4576-88fe-9bad0343aa0d.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MzkwMjk1NTQsIm5iZiI6MTczOTAyOTI1NCwicGF0aCI6Ii8xNjcwNTgzNjMvNDExMjQ4MjI2LTFkYjE0NDI1LTExYTYtNDU3Ni04OGZlLTliYWQwMzQzYWEwZC5wbmc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjUwMjA4JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI1MDIwOFQxNTQwNTRaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT04NDRhYjA3ZGY5YTRlYWM3ZmQ5NTI4ODE4ZjFiY2NhODcwN2VlM2E0OGY5NDYxOWUzMWVmMWY2YmU0MzE1MjM0JlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.8ZZNQoWEOoVIrO8VgMI1Fhu5eBb_SkZEb4lGpBdp35E)

- **View Tickets:**  
  If a seat has been booked, users can view their tickets for the movie they intend to watch, ensuring exclusive access to their reserved seat.

## What Resources Did I Use?

- **Authentication:**  
  I chose HTTP Basic Authentication with Basic Tokens (to experiment with an alternative to JWT).

  ![Authentication](https://private-user-images.githubusercontent.com/167058363/411248721-1528dd9a-2308-4995-9c71-d08e281b7cea.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MzkwMjk5NTQsIm5iZiI6MTczOTAyOTY1NCwicGF0aCI6Ii8xNjcwNTgzNjMvNDExMjQ4NzIxLTE1MjhkZDlhLTIzMDgtNDk5NS05YzcxLWQwOGUyODFiN2NlYS5wbmc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjUwMjA4JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI1MDIwOFQxNTQ3MzRaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT1kMWZhMWVkODEwNTg2NTdkZTRmMDNhODg3NTJhZWI3Y2I4ZDY4ZDQ0YjEzMGY3NGY1ZDQ5ZDlhYzliZDg3Zjk5JlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.z2lc9UC1cqnQj1sCUCze4EKIMALDyV9gjE0Y_IByijA)

- **Documentation:**  
  Swagger is used for API documentation.

  ![Swagger Documentation](https://private-user-images.githubusercontent.com/167058363/411247966-df18188d-576a-443f-805a-25d98a4e5c1f.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MzkwMjk0MTUsIm5iZiI6MTczOTAyOTExNSwicGF0aCI6Ii8xNjcwNTgzNjMvNDExMjQ3OTY2LWRmMTgxODhkLTU3NmEtNDQzZi04MDVhLTI1ZDk4YTRlNWMxZi5wbmc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjUwMjA4JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI1MDIwOFQxNTM4MzVaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT05YzczYTlhNmNjMmQ1YzgyNjc5NGVkN2RhY2FlNzBhN2E1ZDQ3NGJmZmY0NzY1YjhlZGJkZmE3OGM3ZDc0Y2VlJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.5ti2drd8msxmmv8OH9MoYxuRi8DbCOzQEj3Sgy5UKGU)

- **Security:**  
  Spring Security manages permissions and endpoint protection.

- **Testing:**  
  WebTestClient is utilized to ensure that the backend operates correctly.