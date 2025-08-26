package com.pheasa.roomservice.repository;

import com.pheasa.roomservice.domain.Room;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class RoomCustomRepositoryImpl implements RoomCustomRepository {

	private final ReactiveMongoTemplate mongoTemplate;

	@Override
	public Flux<Room> findByFilter(Query query) {
		return mongoTemplate.find(query, Room.class);
	}

	@Override
	public Mono<Long> coundByFilter(Query query) {
		return mongoTemplate.count(query, Room.class);
	}
}
