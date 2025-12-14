package com.krish.voicecatlogagent.service;

import com.krish.voicecatlogagent.dto.CatalogEntryDto;
import com.krish.voicecatlogagent.model.CatalogEntry;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface CatalogService {
    CatalogEntry createProduct(CatalogEntryDto dto, MultipartFile imageFile, String vendorEmail) throws IOException;
    List<CatalogEntry> getAllMyProducts(String vendorEmail);
    CatalogEntry getProductById(Long id, String vendorEmail);
    CatalogEntry updateProduct(Long id, CatalogEntryDto dto, MultipartFile imageFile, String vendorEmail) throws IOException;
    void deleteProduct(Long id, String vendorEmail);
    List<CatalogEntry> getPublicCatalog();

}