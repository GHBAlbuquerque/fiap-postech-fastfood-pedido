package com.fiap.fastfood.common.beans;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;

@Configuration
public class AWSDynamoBeanDeclaration {
    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
//        AWSSecurityTokenService stsClient =
//                AWSSecurityTokenServiceClientBuilder.standard()
//                        .withCredentials(new DefaultAWSCredentialsProviderChain())
//                        .withRegion(String.valueOf(Region.US_EAST_1))
//                        .build();

        return AmazonDynamoDBClient.builder()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion(String.valueOf(Region.US_EAST_1))
                .build();
    }


}