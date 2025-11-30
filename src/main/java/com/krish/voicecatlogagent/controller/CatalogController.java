package com.krish.voicecatlogagent.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krish.voicecatlogagent.dto.CatalogEntryDto;
import com.krish.voicecatlogagent.model.CatalogEntry;
import com.krish.voicecatlogagent.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api/catalog")
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;
    private final ObjectMapper objectMapper;

    // --- CREATE ---
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(
            @RequestPart("product") String productJson,
            @RequestPart(value = "image", required = false) MultipartFile imageFile,
            @AuthenticationPrincipal UserDetails userDetails
    ) throws IOException {
        CatalogEntryDto dto = objectMapper.readValue(productJson, CatalogEntryDto.class);
        return ResponseEntity.ok(catalogService.createProduct(dto, imageFile, userDetails.getUsername()));
    }

    // --- READ ALL ---
    @GetMapping("/list")
    public ResponseEntity<?> getAllMyProducts(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(catalogService.getAllMyProducts(userDetails.getUsername()));
    }

    // --- READ ONE ---
    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(catalogService.getProductById(id, userDetails.getUsername()));
    }

    // --- UPDATE ---
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestPart("product") String productJson,
            @RequestPart(value = "image", required = false) MultipartFile imageFile,
            @AuthenticationPrincipal UserDetails userDetails
    ) throws IOException {
        CatalogEntryDto dto = objectMapper.readValue(productJson, CatalogEntryDto.class);
        return ResponseEntity.ok(catalogService.updateProduct(id, dto, imageFile, userDetails.getUsername()));
    }

    // --- DELETE ---
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        catalogService.deleteProduct(id, userDetails.getUsername());
        return ResponseEntity.ok("Product deleted successfully");
    }
}