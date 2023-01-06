package com.pichkasik.articles.ec2_rds.model.image;

public class ResponseImageData {

    String contentType;
    byte[] data;

    public ResponseImageData(String contentType, byte[] data) {
        this.contentType = contentType;
        this.data = data;
    }

    public String getContentType() {
        return contentType;
    }

    public byte[] getData() {
        return data;
    }
}
