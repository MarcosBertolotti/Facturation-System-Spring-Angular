package com.springboot.apirest.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

public interface IUploadFileService {

    Resource load(String fileName) throws MalformedURLException;

    String copy(MultipartFile file) throws IOException;

    Boolean delete(String fileName);

    Path getPath(String fileName);
}
