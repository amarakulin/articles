package com.pichkasik.articles.ec2_rds.repository;

import com.pichkasik.articles.ec2_rds.model.image.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    Optional<ImageEntity> findByName(String name);
}
