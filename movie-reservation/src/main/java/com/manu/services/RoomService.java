package com.manu.services;

import com.manu.dtos.requests.NewRoomRequest;
import com.manu.dtos.responses.HttpCustomResponse;
import com.manu.entities.RoomEntity;
import com.manu.exceptions.ResourceNotFoundException;
import com.manu.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

  @Autowired private RoomRepository roomRepository;

  public HttpCustomResponse<Object> findAllRooms() {
    try {
      return new HttpCustomResponse<>(200, roomRepository.findAll(), null);
    } catch (Exception e) {
      System.out.println(e);
      return new HttpCustomResponse<>(500, null, "Internal Server Error");
    }
  }

  public HttpCustomResponse<Object> findById(Long id) {
    try {
      var room =
          roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Nothing"));
      return new HttpCustomResponse<>(200, room, null);
    } catch (Exception e) {
      if (e instanceof ResourceNotFoundException) {
        return new HttpCustomResponse<>(400, null, "The room you're looking for does not exist");
      }
      System.out.println(e);
      return new HttpCustomResponse<>(500, null, "Internal Server Error");
    }
  }

  public HttpCustomResponse<Object> createRoom(NewRoomRequest body) {
    try {
      if (body.getRows() * body.getColums() == 0) {
        return new HttpCustomResponse<>(400, null, "You cannot create a room with 0 seats");
      }
      if (roomRepository.findByName(body.getName()).isPresent()) {
        return new HttpCustomResponse<>(400, null, "This name is already taken");
      }
      return new HttpCustomResponse<>(
          201,
          roomRepository.save(new RoomEntity(body.getName(), body.getRows(), body.getColums(), 0)),
          null);
    } catch (Exception e) {
      System.out.println(e);
      return new HttpCustomResponse<>(500, null, "Internal Server Error");
    }
  }

  public HttpCustomResponse<Object> deleteRoom(Long id) {
    try {
      var room =
          roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Nothing"));
      roomRepository.delete(room);
      return new HttpCustomResponse<>(204, null, "Room deleted successfully");
    } catch (Exception e) {
      if (e instanceof ResourceNotFoundException) {
        return new HttpCustomResponse<>(
            400, null, "The room you're trying to delete does not exist");
      }
      System.out.println(e);
      return new HttpCustomResponse<>(500, null, "Internal Server Error");
    }
  }

  public HttpCustomResponse<Object> updateRoom(int unavailable, Long id) {
    try {
      var room =
          roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Nothing"));
      room.setUnavailable(unavailable);
      var updatedRoom = roomRepository.save(room);
      return new HttpCustomResponse<>(
          200, null, "The room: " + updatedRoom.getName() + " has been updated successfully");
    } catch (Exception e) {
      if (e instanceof ResourceNotFoundException) {
        return new HttpCustomResponse<>(
            400, null, "The room you're trying to update does not exist");
      }
      System.out.println(e);
      return new HttpCustomResponse<>(500, null, "Internal Server Error");
    }
  }
}
