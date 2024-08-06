package com.ex.sn.sn.Mapper.FileUpload;

import com.ex.sn.sn.DTO.FileUpload.FileResDto;
import com.ex.sn.sn.Entity.FileUpload;
import com.ex.sn.sn.Mapper.MapperI;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileUploadResponseMapper extends MapperI<FileUpload, FileResDto> {
}
