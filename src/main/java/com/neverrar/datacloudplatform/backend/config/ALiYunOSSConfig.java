package com.neverrar.datacloudplatform.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class ALiYunOSSConfig  {
    @Value("${aliyun.oss.endpoint}")
    private  String endpoint;
    @Value("${aliyun.oss.accessKeyId}")
    private  String accessKeyId;
    @Value("${aliyun.oss.accessKeySecret}")
    private  String accessKeySecret;
    @Value("${aliyun.oss.folder}")
    private  String folder;
    @Value("${aliyun.oss.bucketName}")
    private  String bucketName;
    @Value("${aliyun.oss.webUrl}")
    private  String webUrl;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
}
