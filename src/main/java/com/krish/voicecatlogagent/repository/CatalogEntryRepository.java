package com.krish.voicecatlogagent.repository;
import com.krish.voicecatlogagent.model.CatalogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CatalogEntryRepository extends JpaRepository<CatalogEntry, Long> {
    List<CatalogEntry> findByVendorId(Long vendorId);
    Optional<CatalogEntry> findByIdAndVendorId(Long id, Long vendorId);
}