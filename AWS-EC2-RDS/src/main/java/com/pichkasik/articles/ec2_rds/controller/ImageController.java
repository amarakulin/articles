package com.pichkasik.articles.ec2_rds.controller;

import com.pichkasik.articles.ec2_rds.exceptions.NotFoundException;
import com.pichkasik.articles.ec2_rds.exceptions.ResourceAlreadyExist;
import com.pichkasik.articles.ec2_rds.model.image.ResponseImageData;
import com.pichkasik.articles.ec2_rds.model.image.ResponseImageMetadata;
import com.pichkasik.articles.ec2_rds.service.ImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/all")
    public List<ResponseImageMetadata> getAllImagesMetadata() {
        return imageService.getImagesMetadata();
    }

    @PostMapping(consumes = "multipart/form-data")
    public String uploadImage(@RequestParam MultipartFile image) throws IOException, ResourceAlreadyExist {
        String result = imageService.uploadImage(image);
        return "Upload: %s".formatted(result);
    }

    @GetMapping
    public ResponseEntity<byte[]> downloadImage(@RequestParam String name) throws NotFoundException {
        ResponseImageData responseImage = imageService.downloadImage(name);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(responseImage.getContentType()))
                .body(responseImage.getData());
    }

    @DeleteMapping
    public String deleteImage(@RequestParam String name) throws NotFoundException {
        imageService.deleteImage(name);
        return "Delete: %s".formatted(name);
    }
}
