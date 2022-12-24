package com.pichkasik.articles.ec2_rds.model.image;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "image")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    Date lastUpdate;
    @Column
    String name;
    @Column
    Long size;
    @Column
    String extension;
    @Column
    String type;
    @Column
    byte[] data;

    public ImageEntity(Date lastUpdate, String name, Long size, String extension, String type, byte[] data) {
        this.lastUpdate = lastUpdate;
        this.name = name;
        this.size = size;
        this.extension = extension;
        this.type = type;
        this.data = data;
    }

    public ImageEntity() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setData(byte[] data) {
        this.data = data;
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

    public String getType() {
        return type;
    }

    public byte[] getData() {
        return data;
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
