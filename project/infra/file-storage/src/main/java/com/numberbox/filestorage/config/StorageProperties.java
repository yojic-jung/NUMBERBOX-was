package com.numberbox.filestorage.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cloud.aws")
public record StorageProperties(Credentials credentials, Bucket bucket, Region region) {
    public record Credentials(String accessKey, String secretKey) {
    }

    public record Bucket(String name, String url) {
    }

    public record Region(String name) {
    }
}