package com.ngocthong.productservice.command.service;

import com.netflix.discovery.converters.Auto;
import com.ngocthong.productservice.command.data.ProductItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadProductImageService {
    @Autowired
    private StorageService storageService;

    @Autowired
    private ProductItemRepository productItemRepository;

    public String uploadImage(MultipartFile file, String namePath, String productItemId) {
        String imageUrl = storageService.uploadImages(file, namePath);
        productItemRepository.updateImage(imageUrl, productItemId);
        return imageUrl;
    }

}
