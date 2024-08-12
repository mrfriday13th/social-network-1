package com.ex.sn.sn.Service;

import com.ex.sn.sn.DTO.FileUpload.FileResDto;
import com.ex.sn.sn.Entity.FileUpload;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    public List<FileResDto> uploadFile(MultipartFile[] multipartFile);
    FileUpload findOneById(Long id);
    FileUpload findOneByUrlFile(String urlFile);
    FileUpload save(FileUpload fileUpload);
    void deleteById(Long id);
}
