package com.pichkasik.articles.ec2_rds.controller;

import com.pichkasik.articles.ec2_rds.service.ServerMetadata;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/location")
public class LocationController {

    private final ServerMetadata serverMetadata;

    public LocationController(ServerMetadata serverMetadata) {
        this.serverMetadata = serverMetadata;
    }

    @GetMapping
    public String getLocation() {
        return serverMetadata.getLocationServer();
    }
}
