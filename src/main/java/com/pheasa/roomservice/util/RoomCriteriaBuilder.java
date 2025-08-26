package com.pheasa.roomservice.util;


import com.pheasa.roomservice.dto.RoomFilterDTO;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import java.util.Objects;

public class RoomCriteriaBuilder {

    public static Query build(RoomFilterDTO filter) {
        Criteria criteria = new Criteria();
        if (Objects.nonNull(filter.getName())) {
            criteria.and("attributes.name").is(filter.getName());
        }

        if (Objects.nonNull(filter.getFloor())) {
            criteria.and("attributes.floor").is(filter.getFloor());
        }
        if (Objects.nonNull(filter.getPrice()) && Objects.nonNull(filter.getPriceOp())) {
            switch (filter.getPriceOp()) {
                case "lt" -> criteria.and("attributes.price").lt(filter.getPrice());
                case "lte" -> criteria.and("attributes.price").lte(filter.getPrice());
                case "gt" -> criteria.and("attributes.price").gt(filter.getPrice());
                case "gte" -> criteria.and("attributes.price").gte(filter.getPrice());
                case "eq" -> criteria.and("attributes.price").is(filter.getPrice());
            }
        } else if (Objects.nonNull(filter.getPriceMin()) && Objects.nonNull(filter.getPriceMax())) {
            criteria.and("attributes.price").gte(filter.getPriceMax()).lte(filter.getPriceMax());
        }
        Query query = new Query(criteria)
                .skip((long) filter.getPage() * filter.getSize())
                .limit(filter.getSize());
        return query;
    }
}
