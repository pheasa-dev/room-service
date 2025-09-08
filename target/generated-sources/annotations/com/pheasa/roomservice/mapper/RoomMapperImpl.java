package com.pheasa.roomservice.mapper;

import com.pheasa.roomservice.domain.Room;
import com.pheasa.roomservice.dto.RoomDTO;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-03T18:54:28+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class RoomMapperImpl implements RoomMapper {

    @Override
    public Room toRoom(RoomDTO roomDTO) {
        if ( roomDTO == null ) {
            return null;
        }

        Room room = new Room();

        room.setId( roomDTO.getId() );
        room.setName( roomDTO.getName() );
        Map<String, Object> map = roomDTO.getAttributes();
        if ( map != null ) {
            room.setAttributes( new LinkedHashMap<String, Object>( map ) );
        }

        return room;
    }

    @Override
    public RoomDTO toRoomDTO(Room room) {
        if ( room == null ) {
            return null;
        }

        RoomDTO roomDTO = new RoomDTO();

        roomDTO.setId( room.getId() );
        roomDTO.setName( room.getName() );
        Map<String, Object> map = room.getAttributes();
        if ( map != null ) {
            roomDTO.setAttributes( new LinkedHashMap<String, Object>( map ) );
        }

        return roomDTO;
    }

    @Override
    public void updateRoomFromDTO(RoomDTO roomDTO, Room room) {
        if ( roomDTO == null ) {
            return;
        }

        room.setId( roomDTO.getId() );
        room.setName( roomDTO.getName() );
        if ( room.getAttributes() != null ) {
            Map<String, Object> map = roomDTO.getAttributes();
            if ( map != null ) {
                room.getAttributes().clear();
                room.getAttributes().putAll( map );
            }
            else {
                room.setAttributes( null );
            }
        }
        else {
            Map<String, Object> map = roomDTO.getAttributes();
            if ( map != null ) {
                room.setAttributes( new LinkedHashMap<String, Object>( map ) );
            }
        }
    }
}
