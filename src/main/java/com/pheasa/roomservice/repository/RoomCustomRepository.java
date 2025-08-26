package com.pheasa.roomservice.repository;

import com.pheasa.roomservice.domain.Room;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoomCustomRepository {

    Flux<Room> findByFilter(Query query);

    Mono<Long> coundByFilter(Query query);
}
