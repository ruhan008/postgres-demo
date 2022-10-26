package com.poc.postgres.postgresdemo.service;

import com.poc.postgres.postgresdemo.model.Chunks;
import com.poc.postgres.postgresdemo.model.File;
import com.poc.postgres.postgresdemo.model.Metadata;
import com.poc.postgres.postgresdemo.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public void save(MultipartFile file) throws IOException {
        File File = new File();
        File.setName(StringUtils.cleanPath(file.getOriginalFilename()));
//        File.setContentType(file.getContentType());
        File.setChunk(Chunks.builder().data(file.getBytes()).build());
        File.setSize(file.getSize());
        File.setMetadata(Metadata.builder()
                .contentType(file.getContentType())
                .category("Demo")
                .build());

        fileRepository.save(File);
    }

    public void store (String fileName, InputStream template, MultipartFile file) throws IOException {
        File fileObject = generateFileObject(template, fileName, "contentTypeDefault", createMetadata(file));
        fileRepository.save(fileObject);
    }

    private File generateFileObject(InputStream template, String fileName, String contentTypeDefault,
                                    Metadata metadata) throws IOException {
        File file = new File();
        file.setName(fileName);
//        InputStream temp = template;
//        file.setSize((long) temp.readAllBytes().length);
        file.setSize((long) template.available());
        file.setChunk(Chunks.builder().data(template.readAllBytes()).build());
        file.setMetadata(metadata);
        return file;
    }

    private Metadata createMetadata(MultipartFile file) {
        return Metadata.builder()
                .contentType(file.getContentType())
                .category("Demo2")
                .build();
    }

    public Optional<File> getFile(String id) {
        return fileRepository.findById(id);
    }

    public List<File> getAllFiles() {
        return fileRepository.findAll();
    }
}
