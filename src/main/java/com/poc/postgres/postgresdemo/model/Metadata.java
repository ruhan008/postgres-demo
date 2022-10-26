package com.poc.postgres.postgresdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Metadata {
    private String contentType;
    private String category;
}
