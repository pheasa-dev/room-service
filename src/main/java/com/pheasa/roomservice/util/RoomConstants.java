package com.pheasa.roomservice.util;

import java.util.List;

public final class RoomConstants {
    private RoomConstants() {
    }

    public static final String ATT = "attributes.price";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_FLOOR = ATT + "floor";
    public static final String FIELD_PRICE = ATT + "price";

    //price opterator
    public static final String OP_LT = "lt";
    public static final String OP_LTE = "lte";
    public static final String OP_GT = "gt";
    public static final String OP_GTE = "gte";
    public static final String OP_EQ = "eq";

    //sort allowlist
    public static final List<String> ALLOWED_SORT_FIELD = List.of("name","price", "floor");
}
