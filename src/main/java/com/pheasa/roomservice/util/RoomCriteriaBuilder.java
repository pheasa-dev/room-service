package com.pheasa.roomservice.util;

import com.pheasa.roomservice.dto.RoomFilterDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.pheasa.roomservice.util.RoomConstants.*;

@Slf4j
public class RoomCriteriaBuilder {

    public static Criteria build(RoomFilterDTO filter) {

        List<Criteria> criterias = new ArrayList<>();
        if (Objects.nonNull(filter.getName())) {
            criterias.add(Criteria.where(FIELD_NAME).is(filter.getName()));
        }

        if (Objects.nonNull(filter.getFloor())) {
            // criteria.and(FIELD_FLOOR).is(filter.getFloor());
            criterias.add(Criteria.where(FIELD_NAME).is(filter.getName()));
        }

        if (Objects.nonNull(filter.getFloor())) {
            criterias.add(Criteria.where(FIELD_FLOOR).is(filter.getFloor()));
        }

        if (Objects.nonNull(filter.getPrice()) && Objects.nonNull(filter.getPriceOp())) {
            switch (filter.getPriceOp()) {
                case OP_LT -> criterias.add(Criteria.where(FIELD_PRICE).lt(filter.getPrice()));
                case OP_LTE -> criterias.add(Criteria.where(FIELD_PRICE).lte(filter.getPrice()));
                case OP_GT -> criterias.add(Criteria.where(FIELD_PRICE).gt(filter.getPrice()));
                case OP_GTE -> criterias.add(Criteria.where(FIELD_PRICE).gte(filter.getPrice()));
                case OP_EQ -> criterias.add(Criteria.where(FIELD_PRICE).is(filter.getPrice()));
                default -> log.warn("Invalid price operator: {} ", filter.getPriceOp());
            }
        } else if (Objects.nonNull(filter.getPriceMin()) && Objects.nonNull(filter.getPriceMax())) {
            criterias.add(Criteria.where(FIELD_PRICE).gte(filter.getPriceMax()).lte(filter.getPriceMax()));
        }
        return criterias.isEmpty() ? new Criteria() : new Criteria().andOperator(criterias.toArray(new Criteria[0]));
    }

    public static Sort sort(RoomFilterDTO filter) {

        Sort.Direction direction = "desc".equalsIgnoreCase(filter.getDirection()) ? Sort.Direction.DESC : Sort.Direction.ASC;
//        sort feild
        String sortField = Objects.nonNull(filter.getSortBy()) ? filter.getSortBy() : FIELD_NAME;
        if (!ALLOWED_SORT_FIELD.contains(sortField)) {
            throw new IllegalArgumentException("Invalid sort field: " + sortField);
        }

        if (!sortField.equals(FIELD_NAME)) {
            sortField = ATT + sortField;
        }
        return Sort.by(direction, sortField);
    }
}
