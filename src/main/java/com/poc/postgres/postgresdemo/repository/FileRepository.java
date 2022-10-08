package com.poc.postgres.postgresdemo.repository;

import com.poc.postgres.postgresdemo.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, String> {
}
