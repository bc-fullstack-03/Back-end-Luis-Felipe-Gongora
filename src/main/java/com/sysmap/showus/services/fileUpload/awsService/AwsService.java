package com.sysmap.showus.services.fileUpload.awsService;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
@Service
public class AwsService implements IAwsService {
    @Autowired
    private AmazonS3 amazonS3;

    public String upload(MultipartFile multipartFile, String fileName){
        var fileUri = "";

        try {
            var fileConverted = convertMultiPartToFile(multipartFile);
            amazonS3.putObject(new PutObjectRequest("demo-bucket", fileName, fileConverted).withCannedAcl(CannedAccessControlList.PublicRead));
            fileUri = "http://s3.localstack.localstack.cloud:4566"+"/"+"demo-bucket"+"/"+fileName;
            fileConverted.delete();
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return fileUri;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException{
        var convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));

        var fos = new FileOutputStream(convFile);

        fos.write(file.getBytes());
        fos.close();

        return convFile;
    }
}
