package com.ex.sn.sn.Service;


import com.ex.sn.sn.DTO.InforUser.InforUserResDto;
import com.ex.sn.sn.DTO.User.UserPutReqDto;
import com.ex.sn.sn.DTO.User.UserResDto;
import com.ex.sn.sn.Entity.InforUser;
import com.ex.sn.sn.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    InforUserResDto updateInfo(UserPutReqDto reqDto, MultipartFile[] multipartFile);

    UserResDto getUserInformation();

    Page<UserResDto> searchUserByName(Integer pageNo, String content);

    void updateUserInfo(InforUser user);

    User findOneById(Long id);

    UserResDto findDetailUser(Long id);
}
