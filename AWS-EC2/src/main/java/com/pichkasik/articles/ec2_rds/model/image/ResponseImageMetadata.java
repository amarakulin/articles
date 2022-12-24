package com.pichkasik.articles.ec2_rds.model.image;

import java.util.Date;

public class ResponseImageMetadata {

    private final Date lastUpdate;
    private final String name;
    private final Long size;
    private final String extension;

    public ResponseImageMetadata(Date lastUpdate, String name, Long size, String extension) {
        this.lastUpdate = lastUpdate;
        this.name = name;
        this.size = size;
        this.extension = extension;
    }

    public static ResponseImageMetadata buildFromImageEntity(ImageEntity imageEntity) {
        return new ResponseImageMetadata(
                imageEntity.getLastUpdate(),
                imageEntity.getName(),
                imageEntity.getSize(),
                imageEntity.getExtension()
        );
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public String getName() {
        return name;
    }

    public Long getSize() {
        return size;
    }

    public String getExtension() {
        return extension;
    }

    @Override
    public String toString() {
        return "ImageMetadata{" +
                "lastUpdate=" + lastUpdate +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", extension='" + extension + '\'' +
                '}';
    }
}
