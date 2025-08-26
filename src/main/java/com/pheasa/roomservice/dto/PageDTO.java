package com.pheasa.roomservice.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Paginated respone wrapper")
public class PageDTO<T> {
    @Schema(description = "Current page number (0-based)")
    private int page;
    @Schema(description = "Number of per page")
    private int size;
    @Schema(description = "Total number of records")
    private long totalElements;
    @Schema(description = "Total number of pages")
    private int totalPages;
    @Schema(description = "Current page data list")
    private List<T> content;
}
