package com.poc.postgres.postgresdemo.service;

import com.poc.postgres.postgresdemo.model.File;
import com.poc.postgres.postgresdemo.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public void save(MultipartFile file) throws IOException {
        File File = new File();
        File.setName(StringUtils.cleanPath(file.getOriginalFilename()));
        File.setContentType(file.getContentType());
        File.setData(file.getBytes());
        File.setSize(file.getSize());

        fileRepository.save(File);
    }

    public Optional<File> getFile(String id) {
        return fileRepository.findById(id);
    }

    public List<File> getAllFiles() {
        return fileRepository.findAll();
    }
}
