package com.ex.sn.sn.Service;

import com.ex.sn.sn.DTO.FileUpload.FileResDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    public List<FileResDto> uploadFile(MultipartFile[] multipartFile);
}
