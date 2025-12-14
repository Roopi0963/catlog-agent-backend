package com.krish.voicecatlogagent.service.impl;

import com.krish.voicecatlogagent.dto.CatalogEntryDto;
import com.krish.voicecatlogagent.model.CatalogEntry;
import com.krish.voicecatlogagent.model.Vendor;
import com.krish.voicecatlogagent.repository.CatalogEntryRepository;
import com.krish.voicecatlogagent.repository.VendorRepository;
import com.krish.voicecatlogagent.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final CatalogEntryRepository catalogRepository;
    private final VendorRepository vendorRepository;

    private Vendor getVendor(String email) {
        return vendorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
    }

    @Override
    public CatalogEntry createProduct(CatalogEntryDto dto, MultipartFile imageFile, String vendorEmail) throws IOException {
        Vendor vendor = getVendor(vendorEmail);

        CatalogEntry entry = CatalogEntry.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .brand(dto.getBrand())
                .price(dto.getPrice())
                .discount(dto.getDiscount())
                .category(dto.getCategory())
                .releaseDate(dto.getReleaseDate())
                .productAvailable(dto.isProductAvailable())
                .stockQuantity(dto.getStockQuantity())
                .vendorId(vendor.getId())
                .build();

        if (imageFile != null && !imageFile.isEmpty()) {
            entry.setImageName(imageFile.getOriginalFilename());
            entry.setImageType(imageFile.getContentType());
            entry.setImageData(imageFile.getBytes());
        }

        return catalogRepository.save(entry);
    }

    @Override
    public List<CatalogEntry> getAllMyProducts(String vendorEmail) {
        Vendor vendor = getVendor(vendorEmail);
        return catalogRepository.findByVendorId(vendor.getId());
    }
    @Override
    public List<CatalogEntry> getPublicCatalog() {
        return catalogRepository.findByProductAvailableTrue();
    }


    @Override
    public CatalogEntry getProductById(Long id, String vendorEmail) {
        Vendor vendor = getVendor(vendorEmail);
        return catalogRepository.findByIdAndVendorId(id, vendor.getId())
                .orElseThrow(() -> new RuntimeException("Product not found or access denied"));
    }

    @Override
    public CatalogEntry updateProduct(Long id, CatalogEntryDto dto, MultipartFile imageFile, String vendorEmail) throws IOException {
        // 1. Fetch existing product and verify ownership
        CatalogEntry existingEntry = getProductById(id, vendorEmail);

        // 2. Update fields
        existingEntry.setName(dto.getName());
        existingEntry.setDescription(dto.getDescription());
        existingEntry.setBrand(dto.getBrand());
        existingEntry.setPrice(dto.getPrice());
        existingEntry.setDiscount(dto.getDiscount());
        existingEntry.setCategory(dto.getCategory());
        existingEntry.setReleaseDate(dto.getReleaseDate());
        existingEntry.setProductAvailable(dto.isProductAvailable());
        existingEntry.setStockQuantity(dto.getStockQuantity());

        // 3. Update Image ONLY if a new one is provided
        if (imageFile != null && !imageFile.isEmpty()) {
            existingEntry.setImageName(imageFile.getOriginalFilename());
            existingEntry.setImageType(imageFile.getContentType());
            existingEntry.setImageData(imageFile.getBytes());
        }

        return catalogRepository.save(existingEntry);
    }

    @Override
    public void deleteProduct(Long id, String vendorEmail) {
        // 1. Verify ownership before deleting
        CatalogEntry entry = getProductById(id, vendorEmail);
        catalogRepository.delete(entry);
    }
}