package com.pheasa.roomservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Map;

@Data
public class RoomDTO {
	private String id;
	@NotBlank(message = "Room name is required")
	@Size(max = 100, message = "Room name must be at most 100 character")
	private String name;
	private Map<String, Object> attributes;
}
