package com.pheasa.roomservice.service;

import com.pheasa.roomservice.dto.PageDTO;
import com.pheasa.roomservice.dto.RoomDTO;
import com.pheasa.roomservice.dto.RoomFilterDTO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Component
public interface RoomService {
    Mono<RoomDTO> createRoom(RoomDTO roomDTO);

    Mono<RoomDTO> getRoomById(String id);

    Mono<RoomDTO> updateRoom(String id, RoomDTO roomDTO);

    Mono<Void> deleteRoom(String id);
    //study purpose only
    Flux<RoomDTO> searchRoomByName(String room);

    Flux<RoomDTO> getRoomByFilter(RoomFilterDTO filterDTO);

    Mono<PageDTO<RoomDTO>> getRoomByFilterPagination(RoomFilterDTO filterDTO);
}
