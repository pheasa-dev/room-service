package com.pheasa.roomservice;

import com.pheasa.roomservice.domain.Room;
import com.pheasa.roomservice.dto.RoomDTO;
import com.pheasa.roomservice.mapper.RoomMapper;
import com.pheasa.roomservice.mapper.RoomMapperImpl;
import com.pheasa.roomservice.repository.RoomCustomRepository;
import com.pheasa.roomservice.repository.RoomRepository;
import com.pheasa.roomservice.service.RoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static reactor.core.publisher.Mono.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RoomServiceApplicationTests {
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private RoomMapper roomMapper;
    @Mock
    private RoomCustomRepository roomCustomRepository;
    @InjectMocks
    private RoomMapperImpl roomMapperImpl;
    @Autowired
    private RoomService roomService;

    @Test
    void Create_success() {
        //given
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setName("Luxury");

        Room room = new Room();
        room.setName("Luxury");

        //when
        when(roomRepository.save(room)).thenReturn(Mono.just(room));
        when((Publisher<?>) roomMapper.toRoom(roomDTO)).thenReturn(room);

        when((Publisher<?>) roomMapper.toRoomDTO(room)).thenReturn(roomDTO);
        //verify(roomMapper.toRoom(room), time(1));
        //then
        StepVerifier.create(roomService.createRoom(roomDTO))
                .expectNext(roomDTO)
                .verifyComplete();
    }
}
