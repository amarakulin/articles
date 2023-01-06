package com.pichkasik.articles.ec2_rds.service.impl;

import com.pichkasik.articles.ec2_rds.exceptions.NotFoundException;
import com.pichkasik.articles.ec2_rds.exceptions.ResourceAlreadyExist;
import com.pichkasik.articles.ec2_rds.model.image.ImageEntity;
import com.pichkasik.articles.ec2_rds.model.image.ResponseImageData;
import com.pichkasik.articles.ec2_rds.model.image.ResponseImageMetadata;
import com.pichkasik.articles.ec2_rds.repository.ImageRepository;
import com.pichkasik.articles.ec2_rds.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    private final Supplier<Date> getCurrentTime = () -> Date.from(Instant.now());
    private final Function<String, String> getExtension = (filename) -> filename.substring(filename.lastIndexOf('.'));

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public List<ResponseImageMetadata> getImagesMetadata() {
        return imageRepository.findAll().stream()
                .map(ResponseImageMetadata::buildFromImageEntity)
                .toList();
    }


    @Override
    public String uploadImage(MultipartFile image) throws IOException, ResourceAlreadyExist {
        validate(image.getOriginalFilename());
        ImageEntity imageEntity = new ImageEntity(
                getCurrentTime.get(),
                image.getOriginalFilename(),
                image.getSize(),
                getExtension.apply(image.getOriginalFilename()),
                image.getContentType(),
                image.getBytes()
        );
        ImageEntity res = imageRepository.save(imageEntity);
        return "Saved image with name: %s".formatted(res.getName());
    }

    private void validate(String filename) throws ResourceAlreadyExist {
        Optional<ImageEntity> entity = imageRepository.findByName(filename);
        if (entity.isPresent()) {
            throw new ResourceAlreadyExist("Image with name '%s' already exist".formatted(filename));
        }
    }

    @Override
    public ResponseImageData downloadImage(String name) throws NotFoundException {
        ImageEntity imageEntity = imageRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Can't find a image by name"));
        return new ResponseImageData(imageEntity.getType(), imageEntity.getData());
    }

    @Override
    public void deleteImage(String name) throws NotFoundException {
        ImageEntity imageEntity = imageRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Can't find a image by name"));
        imageRepository.delete(imageEntity);
    }
}
