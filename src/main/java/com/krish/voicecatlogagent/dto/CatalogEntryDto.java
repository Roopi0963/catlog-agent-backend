package com.krish.voicecatlogagent.dto;

import lombok.Data;
import java.util.Date;

@Data
public class CatalogEntryDto {
    private String name;
    private String description;
    private String brand;
    private double price;
    private double discount;
    private String category;
    private Date releaseDate;
    private boolean productAvailable;
    private int stockQuantity;
    // Image is handled via MultipartFile, not this DTO
}