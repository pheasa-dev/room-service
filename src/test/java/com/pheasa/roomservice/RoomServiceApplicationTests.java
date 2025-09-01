package com.pheasa.roomservice;

import com.pheasa.roomservice.domain.Room;
import com.pheasa.roomservice.dto.RoomDTO;
import com.pheasa.roomservice.dto.RoomFilterDTO;
import com.pheasa.roomservice.mapper.RoomMapper;
import com.pheasa.roomservice.mapper.RoomMapperImpl;
import com.pheasa.roomservice.repository.RoomCustomRepository;
import com.pheasa.roomservice.repository.RoomRepository;
import com.pheasa.roomservice.service.RoomService;
import com.pheasa.roomservice.util.RoomCriteriaBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
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
//        when(roomRepository.save(room)).thenReturn(Mono.just(room));
//        when((Publisher<?>) roomMapper.toRoom(roomDTO)).thenReturn(room);
//        when((Publisher<?>) roomMapper.toRoomDTO(room)).thenReturn(roomDTO);


        //verify(roomMapper.toRoom(room), time(1));
        //then
//        StepVerifier.create(roomService.createRoom(roomDTO))
//                .expectNext(roomDTO)
//                .verifyComplete();
    }
    @Test
    void shoudReturnEmptyCrituria_whenNoFilterProvided(){
        //given
        RoomFilterDTO filter = new RoomFilterDTO();
        //when
        Criteria criteria = RoomCriteriaBuilder.build(filter);
        //then
        assertThat(criteria.getCriteriaObject().isEmpty());
    }
    @Test
    void shouldAddNameCriterai_whenNameProvided(){
        //given
        RoomFilterDTO filter = new RoomFilterDTO();
        filter.setName("Luxury Room");
        //when
        Criteria criteria = RoomCriteriaBuilder.build(filter);
        String json = criteria.getCriteriaObject().toJson();
        //then
        assertThat(json).contains("name","Luxury Room");
    }
}
