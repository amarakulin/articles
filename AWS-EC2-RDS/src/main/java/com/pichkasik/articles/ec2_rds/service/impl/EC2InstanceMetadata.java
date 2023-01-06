package com.pichkasik.articles.ec2_rds.service.impl;

import com.amazonaws.util.EC2MetadataUtils;
import com.pichkasik.articles.ec2_rds.service.ServerMetadata;
import org.springframework.stereotype.Service;

@Service
public class EC2InstanceMetadata implements ServerMetadata {

    private final static String LOCATION_RESPONSE_TEMPLATE = "The host is located in '%s' availability zone.";

    @Override
    public String getLocationServer() {
        String availabilityZone = EC2MetadataUtils.getAvailabilityZone();
        return LOCATION_RESPONSE_TEMPLATE.formatted(availabilityZone);
    }
}
