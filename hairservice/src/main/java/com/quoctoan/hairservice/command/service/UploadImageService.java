package com.quoctoan.hairservice.command.service;

import com.quoctoan.hairservice.command.data.ServiceHairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadImageService {

    @Autowired
    private StorageService storageService;

    @Autowired
    private ServiceHairRepository serviceHairRepository;

    public String uploadImage(MultipartFile file, String namePath, String serviceHairId) {
        String imageUrl = storageService.uploadImages(file, namePath);
        serviceHairRepository.updateImage(imageUrl, serviceHairId);
        return imageUrl;
    }

}
