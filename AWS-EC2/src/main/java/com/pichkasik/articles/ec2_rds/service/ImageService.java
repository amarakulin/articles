package com.pichkasik.articles.ec2_rds.service;

import com.pichkasik.articles.ec2_rds.exceptions.NotFoundException;
import com.pichkasik.articles.ec2_rds.exceptions.ResourceAlreadyExist;
import com.pichkasik.articles.ec2_rds.model.image.ResponseImageData;
import com.pichkasik.articles.ec2_rds.model.image.ResponseImageMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    List<ResponseImageMetadata> getAllMetadata();
    String upload(MultipartFile image) throws IOException, ResourceAlreadyExist;
    ResponseImageData downloadImage(String name) throws NotFoundException;
    void deleteImage(String name) throws NotFoundException;
}
