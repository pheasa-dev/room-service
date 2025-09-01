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
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;
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
    void shoudReturnEmptyCrituria_whenNoFilterProvided() {
        //given
        RoomFilterDTO filter = new RoomFilterDTO();
        //when
        Criteria criteria = RoomCriteriaBuilder.build(filter);
        //then
        assertThat(criteria.getCriteriaObject().isEmpty());
    }

    @Test
    void shouldAddNameCriterai_whenNameProvided() {
        //given
        RoomFilterDTO filter = new RoomFilterDTO();
        filter.setName("Luxury Room");
        //when
        Criteria criteria = RoomCriteriaBuilder.build(filter);
        String json = criteria.getCriteriaObject().toJson();
        //then
        assertThat(json).contains("name", "Luxury Room");
    }

    @Test
    void shouldAddFloorCriterai_whenFloorProvided() {
        //given
        RoomFilterDTO filter = new RoomFilterDTO();
        filter.setFloor(2);
        //when
        Criteria criteria = RoomCriteriaBuilder.build(filter);
        String json = criteria.getCriteriaObject().toJson();
        //then
        assertThat(json).contains("floor", "2");
    }

    @Test
    void shouldAddPriceCriterai_withOperationLT() {
        //given
        RoomFilterDTO filter = new RoomFilterDTO();
        filter.setPrice(60d);
        filter.setPriceOp("lt");
        //when
        Criteria criteria = RoomCriteriaBuilder.build(filter);
        String json = criteria.getCriteriaObject().toJson();
        //then
        assertThat(json).contains("price").contains("$lt");
    }

    @Test
    void shouldAddPriceCriterai_withOperationLTE() {
        //given
        RoomFilterDTO filter = new RoomFilterDTO();
        filter.setPrice(60d);
        filter.setPriceOp("lte");
        //when
        Criteria criteria = RoomCriteriaBuilder.build(filter);
        String json = criteria.getCriteriaObject().toJson();
        //then
        assertThat(json).contains("price").contains("$lte");
    }

    @Test
    void shouldAddPriceCriterai_withOperationGT() {
        //given
        RoomFilterDTO filter = new RoomFilterDTO();
        filter.setPrice(60d);
        filter.setPriceOp("gt");
        //when
        Criteria criteria = RoomCriteriaBuilder.build(filter);
        String json = criteria.getCriteriaObject().toJson();
        //then
        assertThat(json).contains("price").contains("gt");
    }

    @Test
    void shouldAddPriceCriterai_withOperationGTE() {
        //given
        RoomFilterDTO filter = new RoomFilterDTO();
        filter.setPrice(60d);
        filter.setPriceOp("gte");
        //when
        Criteria criteria = RoomCriteriaBuilder.build(filter);
        String json = criteria.getCriteriaObject().toJson();
        //then
        assertThat(json).contains("price").contains("$gte");
    }

    @Test
    void shouldAddPriceCriterai_withOperationEQ() {
        //given
        RoomFilterDTO filter = new RoomFilterDTO();
        filter.setPrice(60d);
        filter.setPriceOp("eq");
        //when
        Criteria criteria = RoomCriteriaBuilder.build(filter);
        String json = criteria.getCriteriaObject().toJson();
        //then
        assertThat(json).contains("price").contains("60");
    }

    @Test
    void shouldAddPricMin_PricMax() {
        //given
        RoomFilterDTO filter = new RoomFilterDTO();
        filter.setPriceMin(60d);
        filter.setPriceMax(80d);
        //when
        Criteria criteria = RoomCriteriaBuilder.build(filter);
        String json = criteria.getCriteriaObject().toJson();
        //then
        assertThat(json).contains("price").contains("$gte").contains("$lte");
    }

//    @Test
//    void sort_withValidFieldASC() {
//        //given
//        RoomFilterDTO filter = new RoomFilterDTO();
//        filter.setDirection("asc");
//        filter.setSortBy("price");
//        //when
//        Sort sort = RoomCriteriaBuilder.sort(filter);
//        //then
//        assertThat(sort.getOrderFor("attributes.price")).isNotNull();
//        assertThat(sort.getOrderFor("attributes.price").getDirection())
//                .isEqualTo(Sort.Direction.ASC);
//    }

    @Test
    void sort_withInvalideField_throwException() {
        //given
        RoomFilterDTO filter = new RoomFilterDTO();
        filter.setDirection("asc");
        filter.setSortBy("type"); // type is invalid field
        //when
        //Sort sort = RoomCriteriaBuilder.sort(filter);
        assertThatThrownBy(() -> RoomCriteriaBuilder.sort(filter))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid sort field");
    }

    @Test
    void sort_withDefaulValue() {
        //given
        RoomFilterDTO filter = new RoomFilterDTO(); //no sortByOrDirection
        Sort sort = RoomCriteriaBuilder.sort(filter);
        //when
        assertThat(sort.getOrderFor("name")).isNotNull();
        assertThat(sort.getOrderFor("name").getDirection())
                .isEqualTo(Sort.Direction.ASC);
    }

    @Test
    void sort_withDirectionDESC() {
        //given
        RoomFilterDTO filter = new RoomFilterDTO(); //no sortByOrDirection
        filter.setDirection("desc");
        Sort sort = RoomCriteriaBuilder.sort(filter);
        //when
        assertThat(sort.getOrderFor("name")).isNotNull();
        assertThat(sort.getOrderFor("name").getDirection())
                .isEqualTo(Sort.Direction.DESC);
    }
}
