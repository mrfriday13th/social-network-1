package com.ex.sn.sn.Service.Impl;

import com.ex.sn.sn.Entity.FileUpload;
import com.ex.sn.sn.Repository.FileUploadRepository;
import com.ex.sn.sn.Service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileUploadRepository fileUploadRepository;

    @Override
    public FileUpload findOneById(Long id){
        return fileUploadRepository.findOneById(id);
    }

    @Override
    public FileUpload findOneByUrlFileUpload(String urlFileUpload) {

        return fileUploadRepository.findOneByUrlFile(urlFileUpload);
    }

    @Override
    public FileUpload save(FileUpload fileUpload) {

        return fileUploadRepository.save(fileUpload);
    }

    @Override
    public void deleteById(Long id) {
        fileUploadRepository.deleteById(id);
    }
}
