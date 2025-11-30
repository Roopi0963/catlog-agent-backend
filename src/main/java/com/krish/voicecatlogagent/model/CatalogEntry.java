package com.krish.voicecatlogagent.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "catalog_entries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatalogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    private String brand;

    private double discount;

    @Column(nullable = false)
    private double price;

    private String category;

    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    private boolean productAvailable;

    private int stockQuantity;

    // --- Image Fields (As requested) ---
    private String imageName;

    private String imageType;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imageData;

    // Link to Vendor
    @Column(name = "vendor_id")
    private Long vendorId;
}