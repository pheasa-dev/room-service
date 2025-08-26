package com.pheasa.roomservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RoomFilterDTO {
	@Schema(description = "Filter the floor number", example = "3")
	private Integer floor;
	private String name;
	private String type;
	private Double price;
	private Double priceMax;
	private Double priceMin;
	private String priceOp;
	private Integer size = 10;
	private Integer page = 0;
}
