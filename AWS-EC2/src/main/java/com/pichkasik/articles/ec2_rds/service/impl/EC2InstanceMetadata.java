package com.pichkasik.articles.ec2_rds.service.impl;

import com.pichkasik.articles.ec2_rds.service.ServerMetadata;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("aws")
public class EC2InstanceMetadata implements ServerMetadata {

    private final static String LOCATION_RESPONSE_TEMPLATE = "The host is located in '%s' availability zone.";
    private final String availabilityZone;

    public EC2InstanceMetadata(String availabilityZone) {
        this.availabilityZone = availabilityZone;
    }

    @Override
    public String getLocationServer() {
        return LOCATION_RESPONSE_TEMPLATE.formatted(availabilityZone);
    }
}
