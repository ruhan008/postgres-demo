package com.poc.postgres.postgresdemo.controller;

import com.poc.postgres.postgresdemo.model.File;
import com.poc.postgres.postgresdemo.model.FileResponse;
import com.poc.postgres.postgresdemo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            /* Using Multipart file as input */
//            fileService.save(file);
            /* Using InputStream as input */
            fileService.store(file.getOriginalFilename(), file.getInputStream(), file);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("File uploaded successfully: %s", file.getOriginalFilename()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Could not upload the file: %s!", file.getOriginalFilename()));
        }
    }

    @GetMapping
    public List<FileResponse> list() {
        return fileService.getAllFiles()
                .stream()
                .map(this::mapToFileResponse)
                .collect(Collectors.toList());
    }

    private FileResponse mapToFileResponse(File File) {
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(File.getId())
                .toUriString();
        FileResponse fileResponse = new FileResponse();
        fileResponse.setId(File.getId());
        fileResponse.setName(File.getName());
//        fileResponse.setContentType(File.getContentType());
        fileResponse.setSize(File.getSize());
        fileResponse.setUrl(downloadURL);

        return fileResponse;
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Optional<File> FileOptional = fileService.getFile(id);

        if (!FileOptional.isPresent()) {
            return ResponseEntity.notFound()
                    .build();
        }

        File File = FileOptional.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + File.getName() + "\"")
                .contentType(MediaType.valueOf(File.getMetadata().getContentType()))
                .body(File.getChunk().getData());
    }
}
