package com.pheasa.roomservice.repository;

import com.pheasa.roomservice.domain.Room;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;

public interface RoomRepository extends ReactiveMongoRepository<Room, String> {
	// Flux<Room> findByName(String name);
	Flux<Room> findByNameContainingIgnoreCase(String name);

//    {name: "room"}
	@Query("{'name': ?0}")
	Flux<Room> findRoom(String name);

}
