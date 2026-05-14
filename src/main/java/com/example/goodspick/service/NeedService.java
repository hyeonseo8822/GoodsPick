package com.example.goodspick.service;

import com.example.goodspick.entity.Need;
import com.example.goodspick.repository.NeedRepository;
import com.example.goodspick.storage.FileSystemStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class NeedService {

    private static final Logger log = LoggerFactory.getLogger(NeedService.class);
    private final NeedRepository needRepository;
    private final FileSystemStorageService storageService;

    public NeedService(NeedRepository needRepository, FileSystemStorageService storageService) {
        this.needRepository = needRepository;
        this.storageService = storageService;
    }

    public Need registerNeed(Need need, MultipartFile image, String userId) {
        String imagePath = storageService.store(image);
        need.setImagePath(imagePath);
        need.setUserId(userId); // Set the userId on the Need entity
        return needRepository.save(need);
    }

    public List<Need> getAllNeeds() {
        return needRepository.findAll();
    }

    public List<Need> getNeedsByUserId(String userId) {
        return needRepository.findByUserId(userId);
    }

    public Need getNeedById(String id) {
        return needRepository.findById(id).orElse(null); // Return null if not found
    }

    public List<Need> getNeedsByGoodsNameContaining(String query) {
        return needRepository.findByGoodsNameContainingIgnoreCase(query);
    }

    public void deleteNeed(String id) {
        // Optional: Add logic here to delete the associated image file from storage
        // For now, we'll just delete the database record.
        needRepository.deleteById(id);
    }

    public Need updateNeed(String id, String goodsName, String goodsDescription, boolean inPersonTransaction, MultipartFile image) {
        log.info("Attempting to update Need item with id: {}", id);
        Need existingNeed = getNeedById(id);
        if (existingNeed == null) {
            log.error("Update failed: Need item with id {} not found.", id);
            return null;
        }

        log.debug("Original Need item: {}", existingNeed);

        existingNeed.setGoodsName(goodsName);
        existingNeed.setGoodsDescription(goodsDescription);
        existingNeed.setInPersonTransaction(inPersonTransaction);

        // Handle image update
        // If a new image is provided, store it and update the path
        if (image != null && !image.isEmpty()) {
            // Optional: Delete the old image file first
            // storageService.delete(existingNeed.getImagePath());
            String newImagePath = storageService.store(image);
            log.info("Updating image for Need item {}. New path: {}", id, newImagePath);
            existingNeed.setImagePath(newImagePath);
        }

        log.info("Need item details before save: {}", existingNeed); // Using INFO for visibility
        Need updatedNeed = needRepository.save(existingNeed);
        log.info("Successfully updated and saved Need item: {}", updatedNeed);
        
        return updatedNeed;
    }
    
}

