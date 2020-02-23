package com.springboot.apirest.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileService implements IUploadFileService{

    private final Logger log = LoggerFactory.getLogger(UploadFileService.class);

    private final static String DIRECTORY_UPLOAD = "uploads";

    @Override
    public Resource load(String fileName) throws MalformedURLException {

        Path filePath = this.getPath(fileName);

        Resource resource = new UrlResource(filePath.toUri());

        if(!resource.exists() && !resource.isReadable()) {

            filePath = Paths.get("src/main/resources/static/images").resolve("not-user.png").toAbsolutePath();

            resource = new UrlResource(filePath.toUri());

            log.error("Error couldn't load image: " + fileName);
        }
        return resource;
    }

    @Override
    public String copy(MultipartFile file) throws IOException {

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename().replace(" ", "-");

        Path filePath = this.getPath(fileName);
        log.info(filePath.toString());

        Files.copy(file.getInputStream(), filePath);    // mueve/copia el archivo que subimos al servidor a la ruta escogida

        return fileName;
    }

    @Override
    public Boolean delete(String fileName) {

        if(fileName != null && fileName.length() > 0){

            Path previousFilePath = Paths.get("uploads").resolve(fileName).toAbsolutePath();
            File previousFile = previousFilePath.toFile();

            if(previousFile.exists() && previousFile.canRead()){
                previousFile.delete();
                return true;
            }
        }
        return false;
    }

    @Override
    public Path getPath(String fileName) {

        return Paths.get(DIRECTORY_UPLOAD).resolve(fileName).toAbsolutePath();
    }
}
