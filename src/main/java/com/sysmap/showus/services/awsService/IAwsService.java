package com.sysmap.showus.services.awsService;

import org.springframework.web.multipart.MultipartFile;

public interface IAwsService {
    String upload(MultipartFile multipartFile, String fileName);
}
