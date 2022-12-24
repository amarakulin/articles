package com.pichkasik.articles.ec2_rds.config;

import com.amazonaws.util.EC2MetadataUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("aws")
@Configuration
public class AwsConfig {

    @Bean
    public String availabilityZone() {
        return EC2MetadataUtils.getAvailabilityZone();
    }
}
