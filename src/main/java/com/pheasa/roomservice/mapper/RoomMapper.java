package com.pheasa.roomservice.mapper;

import com.pheasa.roomservice.domain.Room;
import com.pheasa.roomservice.dto.RoomDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Repository;

@Mapper(componentModel = "spring")
@Repository
public interface RoomMapper {

	Room toRoom(RoomDTO roomDTO);

	RoomDTO toRoomDTO(Room room);

	void updateRoomFromDTO(RoomDTO roomDTO, @MappingTarget Room room);

}
