package com.pheasa.roomservice.service.impl;

import com.pheasa.roomservice.domain.Room;
import com.pheasa.roomservice.dto.PageDTO;
import com.pheasa.roomservice.dto.RoomDTO;
import com.pheasa.roomservice.dto.RoomFilterDTO;
import com.pheasa.roomservice.exception.RoomNotFoundException;
import com.pheasa.roomservice.mapper.RoomMapper;
import com.pheasa.roomservice.repository.RoomCustomRepository;
import com.pheasa.roomservice.repository.RoomRepository;
import com.pheasa.roomservice.service.RoomService;
import com.pheasa.roomservice.util.RoomCriteriaBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service

public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final RoomCustomRepository roomCustomRepository;

    @Override
    public Mono<RoomDTO> createRoom(RoomDTO roomDTO) {
        log.debug("Creating room to DB: {}", roomDTO);
        Room room = roomMapper.toRoom(roomDTO);
        return roomRepository.save(room).doOnSuccess(saved -> log.info("Room created successfully : {}", saved))
//                .map(r -> roomMapper.toRoomDTO(r))
                .map(roomMapper::toRoomDTO);
    }

    @Override
    public Mono<RoomDTO> getRoomById(String id) {
        log.debug("Retrieving room with ID: {}", id);
        return roomRepository.findById(id).switchIfEmpty(Mono.error(new RoomNotFoundException(id)))
                .doOnNext(room -> log.info("Room received: {}", room)).map(roomMapper::toRoomDTO);
    }

    @Override
    public Mono<RoomDTO> updateRoom(String id, RoomDTO roomDTO) {
        log.debug("updating room ID: {} with data: {}", id, roomDTO);
        return roomRepository.findById(id).switchIfEmpty(Mono.error(new RoomNotFoundException(id)))
                .flatMap(existing -> {
                    roomMapper.updateRoomFromDTO(roomDTO, existing);
                    Mono<Room> monoRoom = roomRepository.save(existing);
                    return monoRoom;
                }).map(roomMapper::toRoomDTO);
    }

    @Override
    public Mono<Void> deleteRoom(String id) {
        log.debug("Deleting room with ID: {}", id);
        return roomRepository.deleteById(id).switchIfEmpty(Mono.error(new RoomNotFoundException(id)))
                .doOnSuccess(deleted -> log.info("Room deleted successfully!! : {}", id));
    }

    @Override
    public Flux<RoomDTO> searchRoomByName(String name) {
        return roomRepository.findRoom(name).map(roomMapper::toRoomDTO);
    }

    @Override
    public Flux<RoomDTO> getRoomByFilter(RoomFilterDTO filterDTO) {
        Criteria criteria = RoomCriteriaBuilder.build(filterDTO);
        return roomCustomRepository.findByFilter(new Query(criteria))
                .map(roomMapper::toRoomDTO);
    }

    @Override
    public Mono<PageDTO<RoomDTO>> getRoomByFilterPagination(RoomFilterDTO filterDTO) {
        Criteria criteria = RoomCriteriaBuilder.build(filterDTO);
        Mono<Long> countMono = roomCustomRepository.coundByFilter(new Query(criteria));

        Query query = new Query(criteria)
                .skip((long) filterDTO.getPage() * filterDTO.getSize())
                .limit(filterDTO.getSize());
        query.with(RoomCriteriaBuilder.sort(filterDTO));

        Flux<RoomDTO> contentFlux = roomCustomRepository.findByFilter(query)
                .map(roomMapper::toRoomDTO);
        return Mono.zip(countMono, contentFlux.collectList()).map(tuple -> {
            long total = tuple.getT1();
            List<RoomDTO> content = tuple.getT2();
            int size = filterDTO.getSize() > 0 ? filterDTO.getSize() : 1;
            int totalPages = (int) Math.ceil((double) total / size);
            return new PageDTO<RoomDTO>(filterDTO.getPage(), size, total, totalPages, content);
        });
    }
}
