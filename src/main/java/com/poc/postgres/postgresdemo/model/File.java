package com.poc.postgres.postgresdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "files")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class File {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

//    private String contentType;

    private Long size;

//    @Lob
//    private byte[] data;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chunk_id", referencedColumnName = "id")
    private Chunks chunk;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "contentType", column = @Column(name = "metadata_content_type")),
            @AttributeOverride( name = "category", column = @Column(name = "metadata_category")),
    })
    private Metadata metadata;
}
