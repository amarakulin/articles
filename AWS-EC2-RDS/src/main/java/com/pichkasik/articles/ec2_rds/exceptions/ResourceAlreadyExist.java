package com.pichkasik.articles.ec2_rds.exceptions;

public class ResourceAlreadyExist extends Exception {
    public ResourceAlreadyExist(String message) {
        super(message);
    }
}
