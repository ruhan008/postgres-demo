package com.poc.postgres.postgresdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Embedded;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
    private String id;
    private String name;
    private Long size;
    private String url;
    private String contentType;
    @Embedded
    private Metadata metadata;
}
