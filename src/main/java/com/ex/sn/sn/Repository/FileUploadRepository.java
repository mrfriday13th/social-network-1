package com.ex.sn.sn.Repository;

import com.ex.sn.sn.Entity.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {
    FileUpload findOneById(Long id);
    FileUpload findOneByUrlFile(String urlFile);
    FileUpload save(FileUpload fileUpload);
    void deleteById(Long id);
}
