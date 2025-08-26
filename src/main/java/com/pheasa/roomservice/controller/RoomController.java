package com.pheasa.roomservice.controller;

import com.pheasa.roomservice.dto.PageDTO;
import com.pheasa.roomservice.dto.RoomDTO;
import com.pheasa.roomservice.dto.RoomFilterDTO;
import com.pheasa.roomservice.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rooms")
public class RoomController {
	
	private final RoomService roomService;

    //room_servic
    @PostMapping
    //for add summary to url swagger
    @Operation(summary = "Create Room", parameters = @Parameter(in = ParameterIn.PATH, name = "createRoom"))
    public Mono<RoomDTO> createRoom(@Valid @RequestBody RoomDTO roomDTO) {
        return roomService.createRoom(roomDTO);
    }

    @GetMapping("/{roomId}")
    //for add summary to url swagger
    @Operation(summary = "Get room by ID", parameters = @Parameter(in = ParameterIn.PATH, name = "roomId"))
    public Mono<RoomDTO> getRoomById(@PathVariable String roomId) {
        return roomService.getRoomById(roomId);
    }

    @PutMapping("/{roomId}")
    //for add summary to url swagger
    @Operation(summary = "Update room with ID", parameters = @Parameter(in = ParameterIn.PATH, name = "updateRoom"))
    public Mono<RoomDTO> updateRoom(@PathVariable String roomId, @RequestBody RoomDTO roomDTO) {
        return roomService.updateRoom(roomId, roomDTO);
    }

    @DeleteMapping("/{roomId}")
    //for add summary to url swagger
    @Operation(summary = "Delete room by ID", parameters = @Parameter(in = ParameterIn.PATH, name = "deleteRoom"))
    public Mono<Void> deteleRoom(@PathVariable String roomId) {
        return roomService.deleteRoom(roomId);
    }

    //purpose study only
    @GetMapping("/search")
    public Flux<RoomDTO> findRoomByName(@RequestParam String name) {
        return roomService.searchRoomByName(name);
    }

    @GetMapping("/searchs")
    public Flux<RoomDTO> getRoomByFilter(RoomFilterDTO roomFilterDTO) {
        return roomService.getRoomByFilter(roomFilterDTO);
    }

    @GetMapping("/searchs/pagination")
    public Mono<PageDTO<RoomDTO>> getRoomByFilterPagination(RoomFilterDTO roomFilterDTO) {
        return roomService.getRoomByFilterPagination(roomFilterDTO);
    }

    @GetMapping("/searchs/pagination2")
    public Mono<ResponseEntity<PageDTO<RoomDTO>>> getRoomByFilterPaginationWithHeader(RoomFilterDTO roomFilterDTO) {
        return roomService.getRoomByFilterPagination(roomFilterDTO)
                .map(page -> ResponseEntity.ok()
                        .header("X-Total-Count", String.valueOf(page.getTotalElements()))
                        .body(page)
                );
    }
}
