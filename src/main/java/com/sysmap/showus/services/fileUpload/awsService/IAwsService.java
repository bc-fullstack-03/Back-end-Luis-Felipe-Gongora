package com.sysmap.showus.services.fileUpload.awsService;

import org.springframework.web.multipart.MultipartFile;

public interface IAwsService {
    String upload(MultipartFile multipartFile, String fileName);
}
