package com.quoctoan.hairservice.command.controller;

import com.quoctoan.hairservice.command.service.UploadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "Authorization, Content-Type")
@RestController
@RequestMapping("/api/v1/hairservice")
public class UploadImageController {

    @Autowired
    private UploadImageService uploadImageService;

    @PostMapping("/uploadImageServiceHair")
    public String uploadImageServiceHair(@RequestParam("namePath") String namePath, @RequestParam("file") MultipartFile file,
                                         @RequestParam("serviceHairId") String serviceHairId) {
        return uploadImageService.uploadImage(file, namePath, serviceHairId);
    }

}
