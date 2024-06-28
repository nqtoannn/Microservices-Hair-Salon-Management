package com.ngocthong.productservice.command.controller;

import com.ngocthong.productservice.command.service.UploadProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "Authorization, Content-Type")
@RestController
@RequestMapping("/api/v1/")
public class UploadImageController {
    @Autowired
    private UploadProductImageService uploadProductImageService;

    @PostMapping("productItem/uploadImageProductItem")
    public String uploadImageProductItem(@RequestParam("namePath") String namePath, @RequestParam("file") MultipartFile file,
                                         @RequestParam("productItemId") String productItemId) {
        return uploadProductImageService.uploadImage(file, namePath, productItemId);
    }

}
