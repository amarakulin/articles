package com.pichkasik.articles.ec2_rds.service.impl;

import com.pichkasik.articles.ec2_rds.service.ServerMetadata;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("local")
public class LocalhostMetadata implements ServerMetadata {

    private final static String LOCATION_RESPONSE_TEMPLATE = "You are running the server from localhost.";

    @Override
    public String getLocationServer() {
        return LOCATION_RESPONSE_TEMPLATE;
    }
}
